package geometries;

import primitives.*;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static primitives.Util.*;

/**
 * Class Plane is the class representing a Plane
 */
public class Plane extends Geometry {
    private Point3D _point;
    private Vector _normal;

    /**
     * constructor
     * @param emission
     * @param material
     * @param _point
     * @param _normal
     */
    public Plane(Color emission, Material material, Point3D _point, Vector _normal) {
        super(emission, material);
        this._point = _point;
        this._normal = _normal.normalize();
    }

    /**
     * constructor
     * @param emission
     * @param _point
     * @param _normal
     */
    public Plane(Color emission, Point3D _point, Vector _normal) {
        this(emission, new Material(0,0,0), _point, _normal);
    }


    /**
     * parameter constructors
     * @param _point
     * @param _normal
     */
    public Plane(Point3D _point, Vector _normal) {
        this(Color.BLACK, new Material(0,0,0), _point, _normal);
    }

    /**
     * parameter constructors
     * @param _point1
     * @param _point2
     * @param _point3
     * calculate the normal to the plane
     */
    public Plane(Point3D _point1, Point3D _point2, Point3D _point3)
    {
        //Vector v1 = _point2.subtract(_point1);
        //Vector v2 = _point3.subtract(_point1);
        this(Color.BLACK, new Material(0,0,0), _point1, _point2.subtract(_point1).crossProduct(_point3.subtract(_point1)));
    }


    /**
     * parameter constructors
     * @param emission
     * @param material
     * @param _point1
     * @param _point2
     * @param _point3
     * calculate the normal to the plane
     */
    public Plane(Color emission, Material material, Point3D _point1, Point3D _point2, Point3D _point3)
    {
        this(emission, material, _point1, _point2.subtract(_point1).crossProduct(_point3.subtract(_point1)));
    }

    /**
     * parameter constructors
     * @param emission
     * @param _point1
     * @param _point2
     * @param _point3
     * calculate the normal to the plane
     */
    public Plane(Color emission, Point3D _point1, Point3D _point2, Point3D _point3)
    {
        this(emission, new Material(0,0,0), _point1, _point2.subtract(_point1).crossProduct(_point3.subtract(_point1)));
    }

    /**
     * getter
     * @return _normal
     */
    public Vector get_normal()
    {
        return _normal;
    }

    /**
     * getter
     * @return _point
     */
    public Point3D get_point() {
        return _point;
    }

    /**
     * @param p a 3D point
     * @return the normal to the plane at the point p
     */
    @Override
    public Vector getNormal(Point3D p) {

        return _normal ;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(_point.equals(ray.getPoint()))
            return null;
        double t = alignZero(_normal.dotProduct(_point.subtract(ray.getPoint()))/_normal.dotProduct(ray.getVector()));
        if(t<=0)
            return null;
        List<GeoPoint> intersections = new ArrayList();
        intersections.add(new GeoPoint(this,ray.getIntersectionPoint(t)));
        return intersections;
    }
}
