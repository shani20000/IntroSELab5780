package geometries;
import primitives.Point3D;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable {
    List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new ArrayList();
        //Array list - Dynamically re-sizing
        //Constant-time positional access
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList();
        this.geometries.addAll(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = new ArrayList();
        for (Intersectable g: geometries)
        {
            List<GeoPoint> result = g.findIntersections(ray);
            if(result !=null)
                intersections.addAll(result);
        }
        if(intersections.isEmpty())
            return null;
        return intersections;
    }
}
