package unittests;

import geometries.Intersectable;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shani and Merav
 */

class PlaneTest {
    /**
     * Test method for {@link.geometries.Plane#getNormal(geometries.Plane)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        Plane p = new Plane(new Point3D(1,1,1), new Vector(2,0,0));
        assertEquals(p.getNormal(new Point3D(1,1,1)), new Vector(1,0,0), "Plane.getNormal() result is wrong");
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point3D(0,0,0), new Vector(1,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        Point3D p = new Point3D(0, 1, 0);
        List<Intersectable.GeoPoint>result = plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 1, 0)));
        assertEquals( 1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong Intersection point");


        // TC02: Ray does not intersect the plane
        result = plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(-1, 1, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03: the ray included in the plane
        result = plane.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");

        // TC04: the ray is not included in the plane
        result = plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");

        // **** Group: Ray is orthogonal to the plane
        // TC05: – the ray starts before the plane
        p = new Point3D(0, 0, 0);
        result = plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals( 1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong Intersection point");

        // TC06: – the ray starts after the plane
        result = plane.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");

        // TC07: – the ray starts at the plane
        result = plane.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");

        // **** Special cases
        // TC08: – Ray starts at the plane
        result = plane.findIntersections(new Ray(new Point3D(0, 1, 1),
                new Vector(1, 1, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");

        // TC09: – Ray starts at the reference point of the plane
        result = plane.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(1, 1, 0)));
        assertEquals( 0, result.size(), "Wrong number of points");
    }
}