package scene;

import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;
    List<LightSource> _lights;

    /**
     * constructor
     * @param _name the name of the scene
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    /**
     * getter
     * @return _lights list
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    /**
     * getter
     * @return the name of the scene
     */
    public String getName() {
        return _name;
    }

    /**
     * getter
     * @return the background color
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * getter
     * @return the Ambient Light
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * getter
     * @return the list of Geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * getter
     * @return the Camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * getter
     * @return the distance from the view plane
     */
    public double  getDistance() {
        return _distance;
    }

    /**
     * setter
     * @param _background
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * setter
     * @param _ambientLight
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * setter
     * @param _camera
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * setter
     * @param _distance
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * add the given geometries to the list
     * @param geometries
     */
    public void addGeometries(Intersectable ... geometries) {
        _geometries.add(geometries);
    }

    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
    }
}
