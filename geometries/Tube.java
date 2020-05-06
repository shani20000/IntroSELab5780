package geometries;

import primitives.*;

import java.util.List;

/**
 * Class Tube is the class representing a Tube
 */
public class Tube extends RadialGeometry {

    public Ray _ray;

    /**
     * constructor
     *
     * @param emission
     * @param material
     * @param _radius
     * @param _ray
     */
    public Tube(Color emission, Material material, double _radius, Ray _ray) {
        super(emission, material, _radius);
        this._ray = _ray;
    }

    /**
     * constructor
     * @param emission
     * @param _radius
     * @param _ray
     */
    public Tube(Color emission, double _radius, Ray _ray) {
        this(emission, new Material(0,0,0), _radius, _ray);
    }

    /**
     * parameter constructor
     * @param _radius
     * @param _ray
     */
    public Tube(double _radius, Ray _ray) {
        this(Color.BLACK, new Material(0,0,0), _radius, _ray);
    }

    /**
     * copy constructor
     * @param _radialGeometry to copy
     */
    public Tube(RadialGeometry _radialGeometry, Ray _ray) {
        super(_radialGeometry);
        this._ray = _ray;
    }

    /**
     * getter
     * @return _ray
     */
    public Ray get_ray() {
        return _ray;
    }

    /**
     * @param p a 3D point
     * @return the normal to the tube at the point p
     */
    public Vector getNormal(Point3D p)
    {
        Vector v = p.subtract((_ray.getPoint()));
        double t = _ray.getVector().dotProduct(v);
        Point3D o = _ray.getPoint().add(_ray.getVector().scale(t));
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

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}
