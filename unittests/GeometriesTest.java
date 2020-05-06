package unittests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntsersections() {

        Geometries g = new Geometries();
        List<Intersectable.GeoPoint> result;

        // ============ Equivalence Partitions Tests ==============

        //TC01: intersection with some geometries
        result = g.findIntersections(new Ray(new Point3D(6, 7, -0.5),
                new Vector(-5, -5, 0)));
        assertEquals(3, result.size(), "Wrong number of points");


        // =============== Boundary Values Tests ==================
        // TC02: empty collection
        result = g.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(2, -2, -1)));
        assertEquals(null, result, "Wrong number of points");

        //TC03: no intersections
        g.add(new Polygon(new Point3D(0,0,0), new Point3D(4,0,0), new Point3D(4,4,0), new Point3D(0,4,0)),
                new Plane(new Point3D(1,1,1), new Vector(2,0,0)),
                new Sphere(2,new Point3D(1,1,1)),
                new Triangle(new Point3D(0,0,0), new Point3D(4,0,0), new Point3D(0,4,0)));
        result = g.findIntersections(new Ray(new Point3D(-3, -3, -3),
                new Vector(-2, -2, -1)));
        assertEquals(null, result, "Wrong number of points");

        //TC04: intersection with one geometry only
        result = g.findIntersections(new Ray(new Point3D(6, 6, 6),
                new Vector(-1, 1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");

        //TC05: intersection with all the geometries
        result = g.findIntersections(new Ray(new Point3D(0.5, 1.2, -0.5),
                new Vector(0.5, 0.8, 2.5)));
        assertEquals(4, result.size(), "Wrong number of points");

    }
}