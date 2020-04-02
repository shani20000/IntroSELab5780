package geometries;
/**
 * Class RadialGeometry is aan abstract class for Radial Geometries
 */
public abstract class RadialGeometry {

    private double _radius;

    /**
     * parameter constructor
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * copy constructor
     * @param _radialGeometry to copy
     */
    public RadialGeometry(RadialGeometry _radialGeometry) {
        this._radius = _radialGeometry.get_radius();
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
