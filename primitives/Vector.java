package primitives;

import java.util.Objects;
/**
 * Class Vector is the class representing a Vector
 */
public class Vector {

    private Point3D point;

    /**
     * parameter constructor - gets a point
     * @param point
     */
    public Vector(Point3D point) {
        if(point.equals(Point3D.zeroPoint))
            throw new IllegalArgumentException("Cannot create zero vector");
        this.point = point;
    }

    /**
     * parameter constructor - gets 3 coordinates
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
       Point3D pointTemp =new Point3D(x,y,z);
        if(pointTemp.equals(Point3D.zeroPoint))
            throw new IllegalArgumentException("Cannot create zero vector");
        point = pointTemp;
    }

    /**
     * parameter constructors - gets 3 double values
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        Point3D pointTemp =new Point3D(x,y,z);
        if(pointTemp.equals(Point3D.zeroPoint))
            throw new IllegalArgumentException("Cannot create zero vector");
        point = pointTemp;
    }

    /**
     * copy constructor
     * @param v - other vector
     */
    public Vector(Vector v) {
        point =new Point3D(v.getPoint());
    }

    /**
     * getter
     * @return the point value
     */
    public Point3D getPoint() {
        return point;
    }

    //functions
    public  boolean isZero(Vector v)
    {
        if (v.getPoint().equals(Point3D.zeroPoint))
            return true ;
        return false;
    }

    /**
     * subtract vector v from this vector
     * @param v - the vector to subtract
     * @return a new vector of the result
     */
    public Vector subtract(Vector v)
    {
        return this.point.subtract((v.getPoint()));
    }

    /**
     * add vector v to this vector
     * @param v - the vector to add
     * @return a new vector of the result
     */
    public Vector add(Vector v)
    {
        return new Vector(point.add(v));
    }

    /**
     * scale the vector and the number
     * @param num - the number to scale
     * @return a new vector of the result
     */
    public Vector scale(double num)
    {
        return new Vector(this.getPoint().getX().get()*num,
                this.getPoint().getY().get()*num,
                this.getPoint().getZ().get()*num); // subtract from???
    }

    /**
     * dot product the vector and another vector
     * @param v - the other vector
     * @return a number represents the result
     */
    public double dotProduct( Vector v)
    {
        return (
                this.getPoint().getX().get()* v.getPoint().getX().get() +
                this.getPoint().getY().get()* v.getPoint().getY().get() +
                this.getPoint().getZ().get()* v.getPoint().getZ().get());
    }

    /**
     * cross product the vector and another vector
     * @param v - the other vector
     * @return a new vector of the result
     */
    public Vector crossProduct( Vector v)
    {
        return new Vector(
                this.getPoint().getY().get()* v.getPoint().getZ().get()-
                        this.getPoint().getZ().get()* v.getPoint().getY().get(),
                this.getPoint().getZ().get()* v.getPoint().getX().get()-
                        this.getPoint().getX().get()* v.getPoint().getZ().get(),
                this.getPoint().getX().get()* v.getPoint().getY().get()-
                this.getPoint().getY().get()* v.getPoint().getX().get());
    }

    /**
     * @return the squared length of the vector
     */
    public double lengthSquared()
    {
        return (this.point.distanceSquared(Point3D.zeroPoint));
    }

    /**
     * @return the length of the vector
     */
    public double length()
    {
        return (Math.sqrt(lengthSquared()));
    }

    /**
     * normalise the vector
     * @return the updated vector
     */
    public Vector normalize()
    {
        this.point= scale((double)1/length()).getPoint();
        return  this;
    }

    /**
     * @return a new vector - normalized vector
     */
    public Vector normalized()
    {// we didn't use the normalize because it changes the vector it self
        return new Vector(scale((double)1/length()).getPoint());
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
        Vector vector = (Vector) o;
        return Objects.equals(point, vector.point);
    }

    /**
     * to string
     * @return a string of the vector data
     */
    @Override
    public String toString() {
        return ""+ point ;

    }
}
