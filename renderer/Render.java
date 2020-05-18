package renderer;

import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.Collections;
import java.util.List;
import java.awt.color.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * a constant for moving rays beginning size for shading, transparency and reflection rays.
     */
    /**
     * constructor
     *
     * @param _imageWriter
     * @param _scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
    }

    /**
     * Fill the buffer according to the geometries that are in the scene.
     * This function creates the buffer of pixels
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        double distance = _scene.getDistance();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();
        Ray ray;

        // for each point (i,j) in the view plane
        // i is pixel row number and j is pixel in the row number
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else {
                    GeoPoint clo = getClosestPoint(intersectionPoints);
                }
                GeoPoint closestPoint = getClosestPoint(intersectionPoints);
               // if (closestPoint != null)
                _imageWriter.writePixel(j, i, closestPoint == null ? _scene.getBackground().getColor() : calcColor(closestPoint, ray).getColor());
               // _imageWriter.writePixel(j, i, calcColor(closestPoint, ray).getColor());

            }
        }
    }

    /**
     * Calculate the color intensity in the point
     * @param gp
     * @param ray
     * @return the color intensity
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(findClosestIntersection(ray), ray, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene.getAmbientLight().get_intensity());
    }

    /**
     *
     * Calculate the color intensity in the point
     *recursive function
     * @param geoPoint the point to calculate the color
     * @param inRay
     * @param level
     * @param k
     * @return the color intensity
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        Color resultColor;
        //if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        //Color ambientLight = _scene.getAmbientLight().get_intensity();
        Color emissionLight = geoPoint.geometry.getEmission();
        //resultColor = ambientLight;
        resultColor = emissionLight;
        List<LightSource> lights = _scene.get_lights();
        Material material = geoPoint.geometry.getMaterial();
        Vector v = geoPoint.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point).normalize();
        double nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (lights != null) {
            for (LightSource lightSource : lights) {
                Vector l = lightSource.getL(geoPoint.point).normalize();
                if (n.dotProduct(l) * n.dotProduct(v) > 0) {
                    if (unshaded(lightSource, l, n, geoPoint)) {
                        Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                        Color diffuse = calcDiffusive(kd, l, n, lightIntensity);
                        Color specular = calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                        resultColor = resultColor.add(diffuse, specular);
                    }
                }
            }
        }
        if (level == 1) return Color.BLACK;
        double kr = geoPoint.geometry.getMaterial().get_kR();
        double kKr = k * kr;
        if (kKr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(inRay.getVector(), n, geoPoint);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                resultColor = resultColor.add(calcColor(reflectedPoint, reflectedRay, level - 1, kKr).scale(kr));
        }
        double kt = geoPoint.geometry.getMaterial().get_kT();
        double kKt = k * kt;
        if (kKt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(inRay.getVector(), n, geoPoint);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                resultColor = resultColor.add(calcColor(refractedPoint, refractedRay, level - 1, kKt).scale(kt));
        }
        return resultColor;
    }

    /**
     * this function calculates the Specular light
     *
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return the Specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        return lightIntensity.scale(ks * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
    }

    /**
     * this function calculates the Diffusive light
     *
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return the diffuse light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * Find the closest point to p0 (the camera)
     *
     * @param intersectionPoints the list of the intersection points
     * @return the closest point to p0
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double minDistance = Double.MAX_VALUE;
        Point3D p0 = this._scene.getCamera().getP0();
        if (intersectionPoints == null)
            return null;
        for (GeoPoint geoPoint : intersectionPoints) {
            double distance = p0.distance(geoPoint.point);
            if (distance < minDistance) {
                minDistance = distance;
                result = geoPoint;
            }
        }
        return result;
    }

    /**
     * Print the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, java.awt.Color color) {
        double rows = this._imageWriter.getNx();
        double columns = _imageWriter.getNy();
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, color);
                }
            }
        }
    }

    /**
     * write to the image
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * this function checks if a geometry is shaded
     *
     * @param l  the vector from the light source to the point
     * @param n  the normal of the geometry at the point
     * @param gp the point
     * @return 1 if the geometry is unshaded or 0 else
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        //Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        //Point3D point = gp.point.add(delta);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return true;
   //     if (light instanceof DirectionalLight) //if the light is directional there is no need for the next check
   //         return false;
        double distance = light.getDistance(gp.point);
        for (GeoPoint interPoint : intersections) {
        //    if (gp.geometry.getMaterial().get_kT() == 0)
        //        return false;
            if (alignZero(interPoint.point.distance(gp.point) - distance) <= 0
                    && interPoint.geometry.getMaterial().get_kT() == 0)
                //if (interPoint.point.distance(lightRay.getPoint()) < distance)
                return false;
        }
        return true;
    }

    /**
     * calculate a reflected ray
     *
     * @param l  direction vector (from the light source)
     * @param n  the normal to the geometry at the point
     * @param gp the intersection
     * @return a reflected ray
     */
    public Ray constructReflectedRay(Vector l, Vector n, GeoPoint gp) {
        Ray r = new Ray(gp.point, l.subtract(n.scale(l.dotProduct(n) * 2)).normalize(), n);
        return r;
    }

    /**
     * calculate a refracted ray
     *
     * @param l  direction vector (from the light source)
     * @param n the normal
     * @param gp the intersection
     * @return a refracted ray
     */
    public Ray constructRefractedRay(Vector l, Vector n, GeoPoint gp) {
        Ray r = new Ray(gp.point, l, n);
        return r;
    }

    /**
     * this function finds the closest intersection point with the ray
     *
     * @param ray to find intersection
     * @return the closest intersection point with the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = new ArrayList();
        intersections = _scene.getGeometries().findIntersections(ray);
        if(intersections == null)
            return null;
        double min = Double.MAX_VALUE;
        double distance;
        GeoPoint closestPoint = null;
        for (GeoPoint gp : intersections) {
            distance = ray.getPoint().distance(gp.point);
            if (distance < min) {
                min = distance;
                closestPoint = gp;
            }
        }
        return closestPoint;
    }
}



