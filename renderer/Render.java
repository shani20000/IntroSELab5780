package renderer;

import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.*;
import scene.Scene;

import java.util.List;

import java.util.ArrayList;

import static primitives.Util.alignZero;

public class Render {
    private final ImageWriter _imageWriter;
    private final Scene _scene;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private double _superSampleDensity = 0d;
    private double _softShadowRadius = 0d;
    private int numOfSuperSamplingRays = 50;
    private int numOfSoftShadowRays = 50;
    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage


    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     * @author Dan
     *
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }


/**
 * A constant for moving rays beginning size for shading, transparency and reflection rays.
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
     * setter
     * @param _superSampleDensity
     */
    public void set_superSampleDensity(double _superSampleDensity) {
        if (_superSampleDensity >= 0d)
            this._superSampleDensity = _superSampleDensity;
    }

    /**
     * setter
     * @param _softShadowRadius
     */
    public void set_softShadowRadius(double _softShadowRadius) {
        this._softShadowRadius = _softShadowRadius;
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint() { _print = true; return this; }

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
        if(_superSampleDensity==0d) {
            // for each point (i,j) in the view plane
            // i is pixel row number and j is pixel in the row number
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                    List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                    if (intersectionPoints == null)
                        _imageWriter.writePixel(j, i, background);
                    else {
                        GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                        _imageWriter.writePixel(j, i, closestPoint == null ? _scene.getBackground().getColor() : calcColor(closestPoint, ray).getColor());
                    }
                }
            }
        }
        else //this is the super sampling
        {
            superSampling(camera, geometries, background, distance, nX, nY, width, height);
        }
    }

    /**
     * this function do the super sampling
     * @param camera
     * @param geometries
     * @param background
     * @param distance
     * @param nX
     * @param nY
     * @param width
     * @param height
     */
    private void superSampling(Camera camera, Intersectable geometries, java.awt.Color background, double distance, int nX, int nY, int width, int height) {
        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        double radius = ((_imageWriter.getWidth() / _imageWriter.getNx() + _imageWriter.getHeight() / _imageWriter.getNy()) / 2d) * _superSampleDensity;
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    if (intersectionPoints == null)
                        _imageWriter.writePixel(pixel.col, pixel.row, background);
                    else {
                        Point3D pij = camera.findAPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                        List<Ray> rays = Ray.constructRayBeam(ray, pij, radius, numOfSuperSamplingRays, camera.getvUp(), camera.getvRight());
                        Color avgColor = Color.BLACK;
                        for (Ray r : rays) {
                            closestPoint = findClosestIntersection(r);
                            if (closestPoint == null)
                                avgColor = avgColor.add(new Color(background));
                            else
                                avgColor = avgColor.add(calcColor(closestPoint, r));
                        }
                        avgColor = avgColor.scale(1d / rays.size());
                        _imageWriter.writePixel(pixel.col, pixel.row, avgColor.getColor());
                    }
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");

        /*
        Ray ray;
        double radius =((_imageWriter.getWidth()/_imageWriter.getNx() +_imageWriter.getHeight()/_imageWriter.getNy())/2d)*_superSampleDensity;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else {
                    Point3D pij = camera.findAPixel(nX, nY, j, i, distance, width, height);
                    List<Ray> rays = Ray.constructRayBeam(ray, pij, radius, numOfSuperSamplingRays, camera.getvUp(), camera.getvRight());
                    Color avgColor = Color.BLACK;
                    for (Ray r : rays) {
                        closestPoint = findClosestIntersection(r);
                        if (closestPoint == null)
                            avgColor = avgColor.add(new Color(background));
                        else
                            avgColor = avgColor.add(calcColor(closestPoint, r));
                    }
                    avgColor = avgColor.scale(1d / rays.size());
                    _imageWriter.writePixel(j, i, avgColor.getColor());
                }
            }
        }
    }
    */
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
        Color emissionLight = geoPoint.geometry.getEmission();
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
                    double ktr = calcShadow(lightSource, l, n, geoPoint, _softShadowRadius);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
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
                resultColor = resultColor.add(calcColor(reflectedPoint, reflectedRay, level - 1, kKr).scale(kKr));
        }
        double kt = geoPoint.geometry.getMaterial().get_kT();
        double kKt = k * kt;
        if (kKt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(inRay.getVector(), n, geoPoint);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                resultColor = resultColor.add(calcColor(refractedPoint, refractedRay, level - 1, kKt).scale(kKt));
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
     * @param light the light source
     * @param l  the vector from the light source to the point
     * @param n  the normal of the geometry at the point
     * @param gp the point
     * @return 1 if the geometry is unshaded or 0 else
     */
    /**
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
     */


    /**
     * this function checks if a geometry is shaded
     * @param light the light source
     * @param l  the vector from the light source to the point
     * @param n  the normal of the geometry at the point
     * @param gp the point
     * @return the shadow level (1 if the geometry is unshaded).
     */
    private double calcShadow(LightSource light, Vector l, Vector n, GeoPoint gp, double _softShadowDensity) {
        double radius =(_imageWriter.getWidth()/_imageWriter.getNx() +_imageWriter.getHeight()/_imageWriter.getNy())/2d;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        if (_softShadowDensity == 0d || light.getClass() == DirectionalLight.class){ //if there is no soft shadows or the light source is directional) {
            List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
            if (intersections == null)
                return 1.0;
            double distance = light.getDistance(gp.point);
            double ktr = 1.0;
            for (GeoPoint interPoint : intersections) {
                if (alignZero(interPoint.point.distance(gp.point) - distance) <= 0) {
                    ktr *= interPoint.geometry.getMaterial().get_kT();
                    if (ktr < MIN_CALC_COLOR_K)
                        return 0.0;
                }
            }
            return ktr;
        }
        else {
            return softShadow((PointLight) light, gp, _softShadowDensity, lightRay);
        }
    }

    private double softShadow(PointLight light, GeoPoint gp, double _softShadowDensity, Ray lightRay) {
        List<Ray> rays;
        rays = Ray.constructRayBeam(lightRay, light.get_position(), _softShadowDensity, numOfSoftShadowRays,
                _scene.getCamera().getvUp(),_scene.getCamera().getvRight());
        double sum = 0;
        for (Ray r : rays) {
            List<GeoPoint> intersections = _scene.getGeometries().findIntersections(r);
            if (intersections == null)
                sum += 1.0;
            else {
                double distance;
                Plane lightPlane = new Plane(light.get_position(), lightRay.getVector());
                double temp = 1.0;
                for (GeoPoint interPoint : intersections) {
                    Point3D lightPlaneIntersection = lightPlane.findIntersections(r).get(0).point;
                    distance = lightPlaneIntersection.distance(gp.point);
                    if (alignZero(interPoint.point.distance(gp.point) - distance) <= 0) {
                        temp *= interPoint.geometry.getMaterial().get_kT();
                        if (temp < MIN_CALC_COLOR_K) {
                            temp = 0.0;
                            break;
                        }
                    }
                }
                sum += temp;
            }
        }
        double ktr = sum / rays.size();
        return ktr;
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



