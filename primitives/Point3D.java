package primitives;
import java.awt.*;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Point3D is the class representing a 3D point
 */
public class Point3D {

    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    /**
     * static zero point
     */
    public static Point3D zeroPoint = new Point3D(0, 0, 0);

    /**
     * parameter constructor - gets 3 coordinates
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * parameter constructor - gets 3 double numbers
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * copy constructor
     * @param p - other point
     */
    public Point3D(Point3D p) {
        this.x = p.getX();
        this.y = p.getY();
        this.z = p.getZ();
    }

    /**
     * getter
     * @return x value
     */
    public Coordinate getX() {
        return x;
    }

    /**
     * getter
     * @return y value
     */
    public Coordinate getY() {
        return y;
    }

    /**
     * getter
     * @return z value
     */
    public Coordinate getZ() {
        return z;
    }

    //functions


    /**
     * subtract point p from this point
     * @param p - other point
     * @return a vector of the subtraction
     */
    public Vector subtract(Point3D p) {
        return new Vector(new Point3D(this.x.get() - p.getX().get(), this.y.get() - p.getY().get(), this.z.get() - p.getZ().get()));
    }

    /**
     * add a vector to this point
     * @param v - a vector
     * @return new point 3D
     */
    public Point3D add(Vector v)
    {
        return new Point3D(this.x.get()+ v.getPoint().getX().get(),this.y.get()+ v.getPoint().getY().get(),this.z.get()+ v.getPoint().getZ().get());
    }

    /**
     * @param p - other point
     * @return the squared distance between the two points
     */
    public  double distanceSquared(Point3D p)
    {

        return ((this.x.get() - p.getX().get())*(this.x.get() - p.getX().get())+
                (this.y.get() - p.getY().get())*(this.y.get() - p.getY().get())+
                (this.z.get() - p.getZ().get())*(this.z.get() - p.getZ().get()));
    }

    /**
     * @param p - other point
     * @return the distance between the two points
     */
    public  double distance(Point3D p)
    {
        return (Math.sqrt(distanceSquared(p)));
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
        Point3D point3D = (Point3D) o;
        return Objects.equals(x, point3D.x) &&
                Objects.equals(y, point3D.y) &&
                Objects.equals(z, point3D.z);
    }

    /**
     * to string
     * @return a string of the point data
     */
    @Override
    public String toString() {
        return "(" +
                 x +
                ", " + y +
                ", " + z +
                ')';
    }
}