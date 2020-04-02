package geometries;

import primitives.Point3D;
/**
 * Class Triangle is the class representing a Triangle
 */
public class Triangle extends Polygon implements Geometry    {

    /**
     * parameter constructor
     * @param _point1
     * @param _point2
     * @param _point3
     */
    public Triangle(Point3D _point1, Point3D _point2, Point3D _point3)
    {
        super(new Point3D[]{_point1, _point2, _point3});
    }

    /**
     * @return string of the triangle data
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "_vertices=" + _vertices +
                ", _plane=" + _plane +
                '}';
    }

}

