package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;
import java.util.Arrays;


/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * 
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = Arrays.asList(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.get_normal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }


    /**
     * constructor
     * @param emission
     * @param vertices
     */
    public Polygon(Color emission, Point3D... vertices) {
        this(emission,new Material(0,0,0),vertices);
    }

    /**
     * constructor
     * @param vertices
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK,new Material(0,0,0),vertices);
    }


    @Override
    public Vector getNormal(Point3D point) {
        return _plane.get_normal();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Plane plane = new Plane(emission, material, this._vertices.get(0), this._vertices.get(1), this._vertices.get(2));
        List<GeoPoint> intersections = plane.findIntersections(ray);
        if(intersections == null)
            return null;
        if(this._vertices.get(0).equals(ray.getPoint()) || this._vertices.get(0).equals(intersections.get(0)))
            return null;
        Vector v = this._vertices.get(0).subtract(ray.getPoint());
        Vector v1, v2, n;
        v1=v;
        if(this._vertices.get(1).equals(ray.getPoint()) || this._vertices.get(1).equals(intersections.get(0)))
            return null;
        v2 = this._vertices.get(1).subtract(ray.getPoint());
        n = v1.crossProduct(v2).normalize();
        if(isZero(ray.getVector().dotProduct(n)))
            return null;
        double sign = ray.getVector().dotProduct(n);
        v1=v2;

        for (Point3D vertex: _vertices.subList(2, _vertices.size()))
        {
            if(vertex.equals(ray.getPoint()) || vertex.equals(intersections.get(0)))
                return null;
            v2 = vertex.subtract(ray.getPoint());
            n = v1.crossProduct(v2).normalize();
            if(ray.getVector().dotProduct(n) * sign < 0 || isZero(ray.getVector().dotProduct(n)))
                return null;
            v1=v2;
        }
        n = v1.crossProduct(v).normalize();
        if(ray.getVector().dotProduct(n) * sign < 0 || isZero(ray.getVector().dotProduct(n)))
            return null;
        return intersections;
    }
}
