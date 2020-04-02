package unittests;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Cylinder class
 * @author Shani and Merav
 */
 class TubeTest {
    // ============ Equivalence Partitions Tests ==============
    /**
     * Test method for {@link.geometries.Tube#getNormal(geometries.Tube)}.
     */

    @Test
    void getNormal() {
        Ray r = new Ray(new Point3D(1,0,0), new Vector(0,1,0));
        Tube t = new Tube(1, r);
        assertEquals("Tube.getNormal() result is wrong", new Vector(-1, 0, 0), t.getNormal(new Point3D(2, 0, 0)));
    }
}