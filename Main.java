

//Shani Babayev --- id: 315125492 --- mail: abhv100@gmail.com
//Merav Cohen --- id: 208242453 --- mail: merav5677@gmail.com

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Assert;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.List;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.*;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

    /**
     * Main program to tests initial functionality of the 1st stage
     * 
     * @param args irrelevant here
     */
    public static void main(String[] args) {



        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new SpotLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130), new Vector(-2, 2, 1),
                1, 0.0001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();







        try { // test zero vector
            new Vector(0, 0, 0);
            out.println("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}


        Camera cam = new Camera(Point3D.zeroPoint, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<Point3D> results;
        Sphere sphere;
        int Nx = 3;
        int Ny = 3;
        int counter;

        //TC02
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        counter = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
               // results = sphere.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
              //  if (results != null)
               //     counter += results.size();
            }
        }
        assertEquals(18, counter, "Wrong number of points");

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        Assert.assertEquals("Bad ray", new Ray(Point3D.zeroPoint, new Vector(-2, -2, 10)),
                cam.constructRayThroughPixel(3, 3, 0, 0, 10, 6, 6));

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // test length..
        if (!isZero(v1.lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");

        // test Dot-Product
        if (!isZero(v1.dotProduct(v3)))
            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");

        // test Cross-Product
        try { // test zero vector
            v1.crossProduct(v2);
            out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
        Vector vr = v1.crossProduct(v3);
        if (!isZero(vr.length() - v1.length() * v3.length()))
            out.println("ERROR: crossProduct() wrong result length");
        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
            out.println("ERROR: crossProduct() result is not orthogonal to its operands");

        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        if (vCopy != vCopyNormalize)
            out.println("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
            out.println("ERROR: normalize() result is not a unit vector");
        Vector u = v.normalized();
        if (u == v)
            out.println("ERROR: normalizated() function does not create a new vector");

        // Test operations with points and vectors
        Point3D p1 = new Point3D(1, 2, 3); 
        if (!Point3D.zeroPoint.equals(p1.add(new Vector(-1, -2, -3))))
            out.println("ERROR: Point + Vector does not work correctly");
        if (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)))
             out.println("ERROR: Point - Point does not work correctly");

        out.println("If there were no any other outputs - all tests succeeded!");
    }
}
