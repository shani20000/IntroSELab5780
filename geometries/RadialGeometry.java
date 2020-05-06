package geometries;

import primitives.Color;
import primitives.Material;

/**
 * Class RadialGeometry is aan abstract class for Radial Geometries
 */
public abstract class RadialGeometry extends Geometry {

    private double _radius;

    /**
     * constructor
     * @param emission
     * @param material
     */
    public RadialGeometry(Color emission, Material material, double _radius) {
        super(emission, material);
        this._radius = _radius;
    }

    /**
     * constructor
     * @param emission
     * @param _radius
     */
    public RadialGeometry(Color emission, double _radius) {
        this(emission, new Material(0,0,0), _radius);
    }

    /**
     * parameter constructor
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this(Color.BLACK, new Material(0,0,0), _radius);
    }


    /**
     * copy constructor
     * @param _radialGeometry to copy
     */
    public RadialGeometry(RadialGeometry _radialGeometry) {

        this._radius = _radialGeometry.get_radius();
        this.emission = _radialGeometry.getEmission();
        this.material = _radialGeometry.getMaterial();
    }

    /**
     * getter
     * @return _radius
     */
    public double get_radius() {
        return _radius;
    }

    /**
     * @return string of the Radial geometry data
     */
    @Override
    public String toString() {
        return "" + _radius ;
    }
}
