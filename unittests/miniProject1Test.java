package unittests;
import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.awt.*;

public class miniProject1Test {
    @Test
    /**
     * Produce a picture of without any improvements
     */
    @org.junit.Test
    public void mp1Test() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
               new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                       new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
                //new DirectionalLight(new primitives.Color(Color.WHITE),
                //       new Vector(-1,1,-1))
                      );

        ImageWriter imageWriter = new ImageWriter("mp1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        //render.set_softShadowDensity(3);
        //render.set_superSampleDensity(0.33);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Produce a picture with super sampling improvement
     */
    @org.junit.Test
    public void mp1TestWithSuperSampling() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
                //new DirectionalLight(new primitives.Color(Color.WHITE),
                //        new Vector(-1,1,-1))
    );

        ImageWriter imageWriter = new ImageWriter("mp1WithSuperSampling", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_superSampleDensity(1);
        render.renderImage();
        render.writeToImage();
}

    @Test
    /**
     * Produce a picture with soft shadows improvement
     */
    @org.junit.Test
    public void mp1TestWithSoftShadows() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
               // new DirectionalLight(new primitives.Color(Color.WHITE),
               //         new Vector(-1,1,-1))
                        );

        ImageWriter imageWriter = new ImageWriter("mp1WithSoftShadows", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowRadius(5);
        //render.set_superSampleDensity(0.5);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Produce a picture with super sampling and soft shadows improvements
     */
    @org.junit.Test
    public void mp1TestWithSuperSamplingAndSoftShadows() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
               // new DirectionalLight(new primitives.Color(Color.WHITE),
                //        new Vector(-1,1,-1))
                        );

        ImageWriter imageWriter = new ImageWriter("mp1WithSuperSamplingAndSoftShadows", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowRadius(3);
        render.set_superSampleDensity(0.5);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Produce a picture of a sphere and triangle with point light with soft shadows
     */
    @org.junit.Test
    public void softShadow() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)),
                new Triangle(new primitives.Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new primitives.Color(400, 240, 0),
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("softShadow", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowRadius(3);
        render.set_superSampleDensity(0.33);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow with super sampling
     */
    @org.junit.Test
    public void transparencySuperSamplingTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(primitives.Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(primitives.Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new primitives.Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new primitives.Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency super sampling", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);
        render.set_superSampleDensity(0.33);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow with super sampling
     */
    @org.junit.Test
    public void transparencySoftShadowsSuperSamplingTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(primitives.Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(primitives.Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new primitives.Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new primitives.Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency Soft shadows SuperSampling", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowRadius(3);
        render.set_superSampleDensity(0.35);
        render.renderImage();
        render.writeToImage();
    }
}
