package elements;
import primitives.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * This class intended for Camera
 */
public class Camera {
    Point3D p0;
    Vector vUp;
    Vector vTo;
    Vector vRight;

    /**
     * parameter constructor
     *
     * @param p0  - the camera center point
     * @param vUp The vector to up
     * @param vTo The vector towards the scene
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException("vectors are not orthogonal");
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * getter
     *
     * @return po - center point of the camera
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter
     *
     * @return vUp The vector to up
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * getter
     *
     * @return vTo The vector towards the scene
     */
    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return a ray from the camera through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("Distance can't be 0");
        }
        Point3D pc = p0.add(vTo.scale(screenDistance));
        double rY = screenHeight / nY;
        double rX = screenWidth / nX;
        double xJ = (j - ((nX - 1) / 2d)) * rX;
        double yI = (i - ((nY - 1) / 2d)) * rY;
        Point3D pij = pc;
        if (!isZero(xJ))
            pij = pij.add(vRight.scale(xJ));
        if (!isZero(yI))
            pij = pij.add(vUp.scale(-yI));
        Vector vij = pij.subtract(p0);
        return new Ray(p0, vij.normalize());
    }

    /**
     *
     * @param mainRay
     * @param point
     * @param delta
     * @param num
     * @return
     */
    /**
    public List<Ray> constructSuperSamplingRaysThroughPixel(Ray mainRay, Point3D point, double delta, int num) {
        if (isZero(mainRay.getPoint().distance(point))) {
            throw new IllegalArgumentException("Distance can't be 0");
        }
        Random rnd = new Random();
        double[] randomNumbers = rnd.doubles(num * 2, delta * (-1), delta).distinct().toArray();
        Vector direction = mainRay.getVector();
        Point3D rayPoint = mainRay.getPoint();
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        LinkedList<Ray> rays = new LinkedList<>();


        for(int i=0; i < Math.min(num, randomNumbers.length-1); i++) {
            Point3D pXY = point;
            pXY = pXY.add(v1.scale(randomNumbers[i]).add(v2.scale(randomNumbers[i+1])));
            rays.add(new Ray(rayPoint, pXY.subtract(rayPoint).normalize()));
        }
        return rays;
    }
     */
}

