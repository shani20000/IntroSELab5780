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

public class miniProject2Test {

    /**
     * Produce a picture with super sampling and soft shadows improvements
     */
    @org.junit.Test
    public void mp2MultiThreading() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(60, -30, 30),
                        new Point3D(80, -30, -10), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30))

    );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1, 0.00005, 0.00005)
                // new DirectionalLight(new primitives.Color(Color.WHITE),
                //        new Vector(-1,1,-1))
        );

        ImageWriter imageWriter = new ImageWriter("mp2WithMultiThreading", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        render.setMultithreading(4);
        render.setDebugPrint();
        render.set_softShadowRadius(10);
        render.set_superSampleDensity(0.5);
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with super sampling and soft shadows improvements
     */
    @org.junit.Test
    public void mp2TestWithAdaptiveSuperSamplingAndMultiThreading() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(60, -30, 30),
                        new Point3D(80, -30, -10), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30))
        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
                // new DirectionalLight(new primitives.Color(Color.WHITE),
                //        new Vector(-1,1,-1))
        );

        ImageWriter imageWriter = new ImageWriter("mp2 AdaptiveSuperSamplingAndMultiThreading", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        //render.set_softShadowRadius(10);
        //render.set_superSampleDensity(1);
        render.setMultithreading(4);
        render.set_adaptiveSuperSamplingLevel(4);
        render.renderImage();
        render.writeToImage();
    }

    /**
     *Produce a picture from different angle of the camera
     */
    @org.junit.Test
    public void mp2MoveCamera() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -1000, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color(Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.RED), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color(new Color(0, 0, 0)), new Material(0.5, 0.5, 30, 0, 0),
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new primitives.Color(new Color(59, 189, 57)), new Material(0.5, 0.5, 30),
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new primitives.Color(Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new primitives.Color(Color.BLACK), new Material(0.5, 0.5, 30, 0, 0),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(new primitives.Color(new Color(250, 0, 220)), new Material(0.5, 0.5, 30),
                        new Point3D(60, -30, 30),
                new Point3D(80, -30, -10), new Point3D(80, 10, -10),
                new Point3D(60, 10, 30))

        );

        scene.addLights(new SpotLight(new primitives.Color(Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.YELLOW),
                        new Point3D(-50, -95, 0), 1, 0.00005, 0.00005)

        );

        ImageWriter imageWriter = new ImageWriter("mp2 move camera 7", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        //render.set_softShadowRadius(15);
        //render.set_superSampleDensity(1);
        render.setMultithreading(4);
        render.set_adaptiveSuperSamplingLevel(3);
        render.renderImage();
        render.writeToImage();
    }
}
