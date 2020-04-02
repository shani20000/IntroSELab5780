package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
        * Class Cylinder is the class representing a Cylinder
        */
public class Cylinder extends Tube implements Geometry {
    private double _height;

    /**
     * parameter constructor
     * @param _radius
     * @param _ray
     * @param _height
     */
    public Cylinder(double _radius, Ray _ray, double _height) {
        super(_radius, _ray);
        this._height = _height;
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
     * @param point point 3D
     * @return the normal to the cylinder at the point p
     */
    @Override
    public Vector getNormal(Point3D point)
    {
        Plane plane = new Plane(_ray.getPoint(), _ray.getVector());
        Vector v1 = _ray.getPoint().subtract(point);
        if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
        {
            return (_ray.getVector().scale(-1)).normalize();
        }
        Point3D p1 = _ray.getPoint().add(_ray.getVector().normalized().scale(_height));
        v1 = p1.subtract(point);
        if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
        {
            return (_ray.getVector()).normalize();
        }
        Vector v = new Vector(point);
        Point3D p = new Point3D(point);
        double t = v.dotProduct(new Vector(p));
        Point3D o = new Point3D(v.scale(t).getPoint());
        Vector n = (p.subtract(o)).normalize();
        return n;    }
}
