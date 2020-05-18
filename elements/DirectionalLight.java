package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;

    /**
     * constructor
     * @param _intensity
     * @param _direction
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalize();
    }

    /**
     * @return the intensity of this light
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.get_intensity();
    }

    /**
     * @param p
     * @return the direction of the light at the point p
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * @param point
     * @return infinity distance
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
