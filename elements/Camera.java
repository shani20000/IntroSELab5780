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
        Point3D pij = findAPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        Vector vij = pij.subtract(p0);
        return new Ray(p0, vij.normalize());
    }

    /**
     * This function finds the point of the pixel on the view plane
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public Point3D findAPixel(int nX, int nY,
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
        return pij;
    }
}

