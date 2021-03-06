package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * Class Ray is the class representing a Ray
 */
public class Ray {

    private Point3D point;
    private Vector vector;
    private static final double DELTA = 0.1;

    private static Random rnd = new Random();
    /**
     * parameter constructor with delta
     * @param head
     * @param direction
     * @param normal
     */
    public Ray(Point3D head, Vector direction, Vector normal)
    {
        int sign = 1;
        if(direction.dotProduct(normal)<0)
            sign = -1;
        point = head.add(normal.scale(sign*DELTA));
        vector = direction.normalize();
    }

    /**
     * parameter constructor
     * @param point
     * @param vector
     */
    public Ray(Point3D point, Vector vector) {
        if(vector.length()!=1)
            vector.normalize();
        this.point = point;
        this.vector = vector.normalize();
    }

    /**
     * copy constructor
     * @param r - other ray
     */
    public Ray (Ray r)
    {
        this.point=r.getPoint();
        this.vector=r.getVector();
    }

    /**
     * getter
     * @return the point value
     */
    public Point3D getPoint() {
        return point;
    }

    /**
     * getter
     * @return the vector value
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * to string
     * @return a string of the ray data
     */
    @Override
    public String toString() {
        return "" + point
                + vector
                ;
    }

    /**
     * equals function
     * @param o - the object to compare
     * @return a boolean value - the objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(point, ray.point) &&
                Objects.equals(vector, ray.vector);
    }

    /**
     *
     * @param t
     * @return
     */
    public Point3D getIntersectionPoint(double t)
    {
        return(point.add(vector.scale(t)));
    }


    /**
     * This function constructs beam rays from a point
     * @param mainRay
     * @param point
     * @param radius
     * @param num
     * @param vUp
     * @param vRight
     * @return a list of rays
     */
     public static List<Ray> constructRayBeam(Ray mainRay, Point3D point, double radius, int num, Vector vUp, Vector vRight) {
     if (isZero(mainRay.getPoint().distance(point))) {
     throw new IllegalArgumentException("Distance can't be 0");
     }
     //we raffle num*2 random number and then distinct them to avoid dual rays later.
     //the chance that we get too few numbers (lower than num) is low, and we prefer doing that instead of creating unnecessary rays
     double[] randomNumbers = rnd.doubles(num * 2, radius * (-1), radius).distinct().toArray();
     Point3D rayPoint = mainRay.getPoint();
     LinkedList<Ray> rays = new LinkedList<>();
     rays.add(mainRay);
     //we take the num  first numbers of the array
     for(int i=0; i < Math.min(num, randomNumbers.length-1); i++) {
         Point3D pXY = point;
         //we add to the point the random radius
         pXY = pXY.add(vUp.scale(randomNumbers[i]).add(vRight.scale(randomNumbers[i + 1])));
         //we create the ray
         rays.add(new Ray(rayPoint, pXY.subtract(rayPoint).normalize()));
     }
     return rays;
     }

    /**
     *This function creates rays for adaptive superSampling
     * @param mainRay
     * @param point
     * @param rUp
     * @param rRight
     * @param vUp
     * @param vRight
     * @return list of 4 rays: top left, top right, bottom left, bottom right
     */
    public static List<Ray> construct4Rays(Ray mainRay, Point3D point, double rUp, double rRight, Vector vUp, Vector vRight) {
        if (isZero(mainRay.getPoint().distance(point))) {
            throw new IllegalArgumentException("Distance can't be 0");
        }
        Point3D rayPoint = mainRay.getPoint();
        LinkedList<Ray> rays = new LinkedList<>();
        Point3D tL = point; //top left
        tL = tL.add(vUp.scale(rUp).add(vRight.scale(rRight*(-1d))));
        rays.add(new Ray(rayPoint, tL.subtract(rayPoint).normalize()));
        Point3D tR = point; //top right
        tR = tR.add(vUp.scale(rUp).add(vRight.scale(rRight)));
        rays.add(new Ray(rayPoint, tR.subtract(rayPoint).normalize()));
        Point3D bL = point; //bottom left
        bL = bL.add(vUp.scale(rUp*(-1d)).add(vRight.scale(rRight*(-1d))));
        rays.add(new Ray(rayPoint, bL.subtract(rayPoint).normalize()));
        Point3D bR = point; //bottom right
        bR = bR.add(vUp.scale(rUp*(-1d)).add(vRight.scale(rRight)));
        rays.add(new Ray(rayPoint, bR.subtract(rayPoint).normalize()));
        return rays;
    }
}

