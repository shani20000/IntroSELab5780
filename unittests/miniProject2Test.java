package unittests;
import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;


public class miniProject2Test {

    /**
     * Produce a picture with super sampling and soft shadows improvements
     */
    @Test
    public void mp2MultiThreading() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        Material materialDefault = new Material(0.5,0.5,30);
        primitives.Color cubeColor = new Color(250, 0, 220);
        scene.addGeometries(new Sphere(new Color(255,0,0), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(Color.BLACK, materialDefault,
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new Color(59, 189, 57), materialDefault,
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                //create cube
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(60, -30, 30), new Point3D(80, -30, -10),
                        new Point3D(80, 10, -10), new Point3D(60, 10, 30))
        );

        scene.addLights(new SpotLight(new Color(java.awt.Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new Color(java.awt.Color.YELLOW),
                        new Point3D(-50, -95, 0), 1, 0.00005, 0.00005)
                // new DirectionalLight(new Color(java.awt.Color.WHITE),
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
    @Test
    public void mp2TestWithAdaptiveSuperSamplingAndMultiThreading() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        Material materialDefault = new Material(0.5,0.5,30);
        Color cubeColor = new Color(250, 0, 220);
        scene.addGeometries(new Sphere(new Color(255,0,0), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(Color.BLACK, materialDefault,
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new Color(59, 189, 57), materialDefault,
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                //create cube
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(60, -30, 30), new Point3D(80, -30, -10),
                        new Point3D(80, 10, -10), new Point3D(60, 10, 30))
        );

        scene.addLights(new SpotLight(new Color(java.awt.Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new Color(java.awt.Color.YELLOW),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
                // new DirectionalLight(new Color(java.awt.Color.WHITE),
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
    @Test
    public void mp2MoveCamera() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -1000, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        Material materialDefault = new Material(0.5,0.5,30);
        Color cubeColor = new Color(250, 0, 220);
        scene.addGeometries(new Sphere(new Color(255,0,0), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(Color.BLACK, materialDefault,
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new Color(59, 189, 57), materialDefault,
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                //create cube
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(60, -30, 30), new Point3D(80, -30, -10),
                        new Point3D(80, 10, -10), new Point3D(60, 10, 30))
        );

        scene.addLights(new SpotLight(new Color(java.awt.Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new Color(java.awt.Color.YELLOW),
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

    /**
     *Produce a picture
     */
    @Test
    public void mp2fun() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -1000, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)));
        //scene.setCamera(new Camera(new Point3D(0, -2000, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(primitives.Color.BLACK, 0));

        Material materialDefault = new Material(0.5,0.5,30);
        Material pyramidMaterial = new Material(0, 0.8, 0, 1, 0);
        Color cubeColor = new Color(250, 0, 220);
        Color pyramidColor = new Color(java.awt.Color.WHITE);
        scene.addGeometries(new Sphere(new Color(255,0,0), new Material(0.1, 0.8, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(Color.BLACK, materialDefault,
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new Color(59, 189, 57), materialDefault,
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Plane(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                //create cube
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(60, -30, 30), new Point3D(80, -30, -10),
                        new Point3D(80, 10, -10), new Point3D(60, 10, 30)),
                
                //create pyramid
                new Polygon(pyramidColor, pyramidMaterial,
                        new Point3D(-30, -40, -180), new Point3D(-10, -40, -220),
                        new Point3D(30, -40, -200), new Point3D(10, -40, -160)),
                new Triangle(pyramidColor, pyramidMaterial,
                        new Point3D(-30, -40, -180), new Point3D(-10, -40, -220),
                        new Point3D(0, 10, -190)),
                new Triangle(pyramidColor, pyramidMaterial,
                        new Point3D(-10, -40, -220), new Point3D(30, -40, -200),
                        new Point3D(0, 10, -190)),
                new Triangle(pyramidColor, pyramidMaterial,
                        new Point3D(30, -40, -200), new Point3D(10, -40, -160),
                        new Point3D(0, 10, -190)),
                new Triangle(pyramidColor, pyramidMaterial,
                        new Point3D(10, -40, -160), new Point3D(-30, -40, -180),
                        new Point3D(0, 10, -190))
        );

        scene.addLights(new SpotLight(new Color(java.awt.Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new Color(java.awt.Color.YELLOW),
                        new Point3D(-50, -95, 0), 1, 0.00005, 0.00005)

        );

        ImageWriter imageWriter = new ImageWriter("mp2 fun Up", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);
        //render.set_softShadowRadius(15);
        //render.set_superSampleDensity(1);
        render.setMultithreading(4);
        render.set_adaptiveSuperSamplingLevel(4);
        render.renderImage();
        render.writeToImage();
    }
}
