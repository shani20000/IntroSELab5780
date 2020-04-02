package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.security.PublicKey;
/**
 * Class Plane is the class representing a Plane
 */
public class Plane implements Geometry {
    private Point3D _point;
    private Vector _normal;

    /**
     * parameter constructors
     * @param _point
     * @param _normal
     */
    public Plane(Point3D _point, Vector _normal) {
        this._point = _point;
        this._normal = _normal.normalize();
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
        this._point= _point1;
        Vector v1 = _point2.subtract(_point1);
        Vector v2 = _point3.subtract(_point1);
        _normal = (v1.crossProduct(v2)).normalize();
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
}
