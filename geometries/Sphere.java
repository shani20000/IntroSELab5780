package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Class Sphere is the class representing a Sphere
 */
public class Sphere extends RadialGeometry implements Geometry {

private Point3D _center;

    /**
     * parameter constructor
     * @param _radius
     * @param _center
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = _center;
    }

    /**
     * getter
     * @return
     */
    public Point3D get_center() {
        return _center;
    }

    /**
     * @param p a 3D point
     * @return the normal to the sphere at the point p
     */
    public Vector getNormal(Point3D p)
    {
        Vector v = (p.subtract(_center)).normalize();
        return v;
    }

    /**
     * @return string of the sphere data
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "Center=" + _center + " Radius=" + super.toString()+
                '}';
    }
}
