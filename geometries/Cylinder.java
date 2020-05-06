package geometries;

import primitives.*;

import java.util.List;

/**
        * Class Cylinder is the class representing a Cylinder
        */
public class Cylinder extends Tube {
    private double _height;

    /**
     * constructor
     * @param emission
     * @param material
     * @param _radius
     * @param _ray
     * @param _height
     */
    public Cylinder(Color emission, Material material, double _radius, Ray _ray, double _height) {
        super(emission, material, _radius, _ray);
        this._height = _height;
    }

    /**
     * constructor
     * @param emission
     * @param _radius
     * @param _ray
     * @param _height
     */
    public Cylinder(Color emission, double _radius, Ray _ray, double _height) {
        this(emission, new Material(0,0,0), _radius, _ray, _height);
    }

    /**
     * parameter constructor
     * @param _radius
     * @param _ray
     * @param _height
     */
    public Cylinder(double _radius, Ray _ray, double _height) {
        this(Color.BLACK, new Material(0,0,0), _radius, _ray, _height);
    }

    /**
     * getter
     * @return _height
     */
    public double get_height() { //getter
        return _height;
    }

    /**
     * @return string of the Cylinder data
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height + " Radius=" + super.toString()+
                '}';
    }

    /**
     *
     * @param p point 3D
     * @return the normal to the cylinder at the point p
     */
    @Override
    public Vector getNormal(Point3D p)
    {
        Plane plane = new Plane(_ray.getPoint(), _ray.getVector());
        Vector v1 = _ray.getPoint().subtract(p);
        if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
            return (_ray.getVector().scale(-1)).normalize();
        Point3D p1 = _ray.getPoint().add(_ray.getVector().normalized().scale(_height));
        v1 = p1.subtract(p);
        if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
            return (_ray.getVector()).normalize();
        Vector v = p.subtract((_ray.getPoint()));
        double t = _ray.getVector().dotProduct(v);
        Point3D o = _ray.getPoint().add(_ray.getVector().scale(t));
        Vector n = (p.subtract(o)).normalize();
        return n;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}
