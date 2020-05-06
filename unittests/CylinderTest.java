package unittests;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Ray r = new Ray(new Point3D(1,0,0), new Vector(0,1,0));
        Cylinder c = new Cylinder(1, r,5);
        assertEquals("Cylinder.getNormal() result is wrong 1", new Vector(1, 0, 0), c.getNormal(new Point3D(2, 1, 0)));
        assertEquals("Cylinder.getNormal() result is wrong 2", new Vector(0, -1, 0), c.getNormal(new Point3D(1.5, 0, 0)));
        assertEquals("Cylinder.getNormal() result is wrong 3", new Vector(0, 1, 0), c.getNormal(new Point3D(1.5, 5, 0)));


        // ============ Boundary Tests ==============
        assertEquals("Cylinder.getNormal() result is wrong 4", new Vector(0, -1, 0), c.getNormal(new Point3D(2, 0, 0)));
        assertEquals("Cylinder.getNormal() result is wrong 5", new Vector(0, 1, 0), c.getNormal(new Point3D(2, 5, 0)));
    }
}