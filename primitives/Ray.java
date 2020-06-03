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

    public Point3D getIntersectionPoint(double t)
    {
        return(point.add(vector.scale(t)));
    }

    /**
     *This function constructs rays from a point
     * @param mainRay
     * @param point
     * @param delta
     * @param num
     * @return
     */
    public static List<Ray> constructRayBeam(Ray mainRay, Point3D point, double delta, int num) {
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

}
