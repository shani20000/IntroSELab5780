package unittests;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 * @author Shani and Merav
 */
 class SphereTest {
    // ============ Equivalence Partitions Tests ==============
    /**
     * Test method for {@link.geometries.Sphere#getNormal(geometries.Sphere)}.
     */
    @Test
    void getNormal() {
        Sphere s=new Sphere(2,new Point3D(1,1,1));
        Point3D p= new Point3D(3,1,1);
        assertEquals(s.getNormal(p),new Vector(1,0,0),"Sphere.getNormal() result is wrong");
    }
}