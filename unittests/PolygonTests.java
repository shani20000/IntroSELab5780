package unittests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 */
public class PolygonTests {

    /**
     * test method for polygon
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to trinagle");
    }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Polygon polygon = new Polygon(new Point3D(0,0,0), new Point3D(4,0,0), new Point3D(0,4,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the polygon
        Point3D p = new Point3D(2, 1, 0);
        List<Intersectable.GeoPoint> result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(0, -1, -1)));
        assertEquals( 1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong Intersection point");

        // **** Group: Ray does not intersect the polygon
        // TC02: Ray against vertex
        result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(4, -3, -1)));
        assertEquals( null, result, "Wrong number of points");

        // TC03: Ray against edge
        result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(1, 1, -1)));
        assertEquals(null, result, "Wrong number of points");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray begins before the plane (all tests 0 points)

        // TC04: the ray on vertex
        result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(2, -2, -1)));
        assertEquals(null, result, "Wrong number of points");

        // TC05: the ray on edge
        result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(0, -2, -1)));
        assertEquals( null, result, "Wrong number of points");

        // TC06: the ray on edge's continuation
        result = polygon.findIntersections(new Ray(new Point3D(2, 2, 1),
                new Vector(4, -2, -1)));
        assertEquals( null, result, "Wrong number of points");
    }

}
