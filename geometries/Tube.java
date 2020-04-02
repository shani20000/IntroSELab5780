package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
/**
 * Class Tube is the class representing a Tube
 */
public class Tube extends RadialGeometry implements Geometry{

    public Ray _ray;

    /**
     * parameter constructor
     * @param _radius
     * @param _ray
     */
    public Tube(double _radius, Ray _ray) {
        super(_radius);
        this._ray = _ray; }

    /**
     * getter
     * @return _ray
     */
    public Ray get_ray() {
        return _ray;
    }

    /**
     * @param point a 3D point
     * @return the normal to the tube at the point p
     */
    public Vector getNormal(Point3D point)
    {
        Vector v = new Vector(point);
        Point3D p = new Point3D(point);
        double t = v.dotProduct(new Vector(p));
        Point3D o = new Point3D(v.scale(t).getPoint());
        Vector n = (p.subtract(o)).normalize();
        return n;
    }

    /**
     * @return string of the tube data
     */
    @Override
    public String toString() {
        return "Tube{" +
                "_ray=" + _ray + " Radius=" + super.toString() +
                '}';
    }
}
