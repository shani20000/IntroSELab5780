package unittests;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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
}