package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
/**
 * Interface Geometry is Interface for all the Geometries
 */
public abstract class Geometry implements Intersectable {
    protected Color emission;
    protected Material material;

    /**
     * constructor
     * @param emission
     */

    public Geometry(Color emission) {
        this.emission = emission;
        material = new Material(0,0,0);
    }

    /**
     * constructor
     * @param emission
     * @param material
     */
    public Geometry(Color emission, Material material) {
        this.emission = emission;
        this.material = material;
    }

    /**
     * copy constructor
     * @param g geometry to copy
     */
    public Geometry(Geometry g) {
        this.emission = g.emission;
        this.material = g.material;
    }

    /**
     * default constructor
     */
    public Geometry() {
        emission= Color.BLACK;
        material = new Material(0,0,0);
    }


    /**
     * getter
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * getter
     * @return the emission light
     */
    public Color getEmission() {
        return emission;
    }

    /**
     *
     * @param p a 3D point
     * @return the normal to the geometry at the point p
     */

    public abstract Vector getNormal(Point3D p);

}
