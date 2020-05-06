package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
/**
 * Class Triangle is the class representing a Triangle
 */
public class Triangle extends Polygon   {
    /**
     * constructor
     * @param emission
     * @param material
     * @param _point1
     * @param _point2
     * @param _point3
     */
    public Triangle(Color emission, Material material, Point3D _point1, Point3D _point2, Point3D _point3) {
        super(emission, material, new Point3D[]{_point1, _point2, _point3});
    }

    /**
     * constructor
     * @param emission
     * @param _point1
     * @param _point2
     * @param _point3
     */
    public Triangle(Color emission, Point3D _point1, Point3D _point2, Point3D _point3) {
        super(emission, new Point3D[]{_point1, _point2, _point3});
    }

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

