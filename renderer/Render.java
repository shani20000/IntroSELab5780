package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;
import java.awt.color.*;

import java.util.List;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;

    /**
     *
     */
    private static final double DELTA = 0.1;

    /**
     * constructor
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
                if (closestPoint != null)
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());

            }
        }
    }

    /**
     * Calculate the color intensity in the point
     *
     * @param geoPoint the point to calculate the color
     * @return the color intensity
     */
    private Color calcColor(GeoPoint geoPoint) {
        Color resultColor;
        Color ambientLight = _scene.getAmbientLight().get_intensity();
        Color emissionLight = geoPoint.geometry.getEmission();
        resultColor = ambientLight;
        resultColor = resultColor.add(emissionLight);
        List<LightSource> lights = _scene.get_lights();
        Material material = geoPoint.geometry.getMaterial();
        Vector v = geoPoint.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point).normalize();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (lights != null) {
            for (LightSource lightSource : lights) {
                Vector l = lightSource.getL(geoPoint.point).normalize();
                if (n.dotProduct(l)*n.dotProduct(v) > 0) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    Color diffuse = calcDiffusive(kd, l, n, lightIntensity);
                    Color specular = calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    resultColor = resultColor.add(diffuse,specular);
                }
            }
        }
        return resultColor;
    }

    /**
     * this function calculates the Specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return the Specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        return lightIntensity.scale(ks * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
    }

    /**
     * this function calculates the Diffusive light
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
}


