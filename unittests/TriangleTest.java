package unittests;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
/**
 * Unit tests for geometries.Triangle class
 * @author Shani and Merav
 */

class TriangleTest {
    /**
     * Test method for {@link.geometries.Triangle#getNormal(geometries.Triangle)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Triangle  t = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3), t.getNormal(new Point3D(0, 0, 1)));
    }
}