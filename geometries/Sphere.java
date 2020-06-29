package geometries;

import primitives.*;
import primitives.Vector;

import java.math.*;

import java.util.*;

/**
 * Class Sphere is the class representing a Sphere
 */
public class Sphere extends RadialGeometry {

private Point3D _center;

    /**
     * constructor
     * @param emission
     * @param material
     * @param _radius
     * @param _center
     */
    public Sphere(Color emission, Material material, double _radius, Point3D _center) {
        super(emission, material, _radius);
        this._center = _center;
    }

    /**
     * constructor
     * @param emission
     * @param _radius
     * @param _center
     */
    public Sphere(Color emission, double _radius, Point3D _center) {
        this(emission, new Material(0,0,0), _radius, _center);
    }

    /**
     * parameter constructor
     * @param _radius
     * @param _center
     */
    public Sphere(double _radius, Point3D _center) {
        this(Color.BLACK, new Material(0,0,0), _radius, _center);
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

    /**
     *
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(ray.getPoint().equals(_center))
        {
            List<GeoPoint> intersections = new ArrayList();
            intersections.add(new GeoPoint(this,ray.getPoint().add(ray.getVector().scale(get_radius()))));
            return intersections;
        }
        Vector v= _center.subtract(ray.getPoint());
        double tm = ray.getVector().dotProduct(v);
        double d = Math.sqrt(v.lengthSquared()-tm*tm);
        if(d>=get_radius())
            return null;
        double th = Math.sqrt(Math.pow(get_radius(), 2) - Math.pow(d, 2));
        double t1 = tm + th;
        double t2 = tm - th;

        if(t1<=0 && t2 <=0)
            return null;
        List<GeoPoint> intersections = new ArrayList();
        if (t1>0)
            intersections.add(new GeoPoint(this,ray.getIntersectionPoint(t1)));
        if (t2>0 && t2!=t1)
            intersections.add(new GeoPoint(this,ray.getIntersectionPoint(t2)));
        return intersections;
    }
}
