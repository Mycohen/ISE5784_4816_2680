package geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    final private List<Intersectable> geometries = new LinkedList<>();
    public Geometries () {};
    public Geometries(Intersectable... geometries) {
        add(geometries);

    }
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            this.geometries.add(geo);
        }
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geo : geometries) {
            List<Point> temp = geo.findIntersections(ray);
            if (temp != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(temp);
            }
        }
        return intersections;
    }

}
