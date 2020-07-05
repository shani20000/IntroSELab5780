package unittests;

import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.*;
import primitives.*;
import primitives.Point3D;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTest {
    @Test
    /**
     * test for integration of constructing ray and finding ray intersection with a sphere
     */
    public void testConstructRayAndSphereIntersection() {

        Camera cam1 = new Camera(Point3D.zeroPoint, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Camera cam2 = new Camera(new Point3D(0,0,-0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Sphere sphere;
        int Nx = 3;
        int Ny = 3;
        int counter;

        //TC01 - 2 intersection points with sphere
        sphere = new Sphere(1, new Point3D(0, 0, 3));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = sphere.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(2, counter, "Wrong number of points");

        //TC02 - 18 intersection points with sphere
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = sphere.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(18, counter, "Wrong number of points");

        //TC03 - 10 intersection points with sphere
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = sphere.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(10, counter, "Wrong number of points");

        //TC04 - 9 intersection points with sphere
        sphere = new Sphere(4, new Point3D(0, 0, 0.5));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = sphere.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(9, counter, "Wrong number of points");

        //TC05 - no intersection points with sphere - sphere behind camera
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = sphere.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(0, counter, "Wrong number of points");

    }

    @Test
    /**
     * test for integration of constructing ray and finding ray intersection with a plane
     */
    public void testConstructRayAndPlaneIntersection() {

        Camera cam = new Camera(Point3D.zeroPoint, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Plane plane;
        int Nx = 3;
        int Ny = 3;
        int counter;

        //TC01 - 9 intersection points with plane - plane is paralleled to the view plane
        plane = new Plane(new Point3D(0,0,2), new Vector(0,0,1));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = plane.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(9, counter, "Wrong number of points");

        //TC02 - 9 intersection points with plane
        plane = new Plane(new Point3D(0,0,4), new Point3D(0.5,1,4.2), new Point3D(-0.5,1,3.8));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = plane.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(9, counter, "Wrong number of points");

        //TC03 - 6 intersection points with plane
        plane = new Plane(new Point3D(0,0,4), new Point3D(-0.2,1.5,5), new Point3D(-0.5,1,3.8));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = plane.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(6, counter, "Wrong number of points");

    }

    @Test
    /**
     * test for integration of constructing ray and finding ray intersection with a triangle
     */
    public void testConstructRayAndTriangleIntersection() {

        Camera cam = new Camera(Point3D.zeroPoint, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Triangle triangle;
        int Nx = 3;
        int Ny = 3;
        int counter;

        //TC01 - 1 intersection points with triangle
        triangle = new Triangle(new Point3D(0,-1,2), new Point3D(1,1,2), new Point3D(-1,1,2));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = triangle.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(1, counter, "Wrong number of points");

        //TC02 - 2 intersection points with triangle
        triangle = new Triangle(new Point3D(0,-20,2), new Point3D(1,1,2), new Point3D(-1,1,2));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                results = triangle.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    counter += results.size();
            }
        }
        assertEquals(2, counter, "Wrong number of points");

    }
}
