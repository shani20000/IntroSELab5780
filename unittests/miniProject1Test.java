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
                new Sphere(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(Color.GREEN), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
               new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                       new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
              // new Polygon(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.6, 0),
               //         new Point3D(-20, 10, 0), new Point3D(0, -30, 0),
               //         new Point3D(40, -10, 0), new Point3D(20, 30, 0)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(new Color(172, 165, 9, 153)),
                        new Point3D(0, -500, 300), 1,0.00001, 0.000001),
                new DirectionalLight(new primitives.Color(Color.WHITE),
                       new Vector(-1,1,-1)));

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
                new Sphere(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(Color.GREEN), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                // new Polygon(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.6, 0),
                //         new Point3D(-20, 10, 0), new Point3D(0, -30, 0),
                //         new Point3D(40, -10, 0), new Point3D(20, 30, 0)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(new Color(172, 165, 9, 153)),
                        new Point3D(0, -500, 300), 1,0.00001, 0.000001),
                new DirectionalLight(new primitives.Color(Color.WHITE),
                        new Vector(-1,1,-1)));

        ImageWriter imageWriter = new ImageWriter("mp1WithSuperSampling", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        //render.set_softShadowDensity(3);
        render.set_superSampleDensity(0.25);
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
                new Sphere(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(Color.GREEN), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                // new Polygon(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.6, 0),
                //         new Point3D(-20, 10, 0), new Point3D(0, -30, 0),
                //         new Point3D(40, -10, 0), new Point3D(20, 30, 0)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(new Color(172, 165, 9, 153)),
                        new Point3D(0, -500, 300), 1,0.00001, 0.000001),
                new DirectionalLight(new primitives.Color(Color.WHITE),
                        new Vector(-1,1,-1)));

        ImageWriter imageWriter = new ImageWriter("mp1WithSoftShadows", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowDensity(3);
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
                new Sphere(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 100)),
                new Sphere(new primitives.Color(Color.GREEN), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 140)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 150)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10),  new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(97, 61, 38)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(new Color(172, 165, 9, 153)),
                        new Point3D(0, -500, 300), 1,0.00001, 0.000001)
               // new DirectionalLight(new primitives.Color(Color.WHITE),
                //        new Vector(-1,1,-1))
                        );

        ImageWriter imageWriter = new ImageWriter("mp1WithSuperSamplingAndSoftShadows", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.set_softShadowDensity(3);
        render.set_superSampleDensity(0.5);
        render.renderImage();
        render.writeToImage();
    }
}
