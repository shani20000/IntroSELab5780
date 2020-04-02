package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Interface Geometry is Interface for all the Geometries
 */
public interface Geometry {
    /**
     *
     * @param p a 3D point
     * @return the normal to the geometry at the point p
     */
    public Vector getNormal(Point3D p);
}
