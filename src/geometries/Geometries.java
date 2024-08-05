package geometries;

import primitives.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of geometrical objects that can be intersected by a ray.
 * Provides methods to add geometrical objects and find intersections with a ray.
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 */
public class Geometries extends Intersectable {
    // A list to store the geometrical objects
    final private List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor that initializes an empty collection of geometries.
     */
    public Geometries() {}

    /**
     * Constructor that initializes the collection with the given geometrical objects.
     *
     * @param geometries The geometrical objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }


//    public void add(Intersectable... geometries) {
//        for (Intersectable geo : geometries) {
//            this.geometries.add(geo);
//        }
//    }
    public void add(Intersectable... geometries) {
    this.geometries.addAll(List.of(geometries));
}

    /**
     * Finds all intersections of the given ray with the geometrical objects
     * in the collection.
     *
     * @param ray The ray to intersect with the geometrical objects.
     * @return A list of intersection points, or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geo : geometries) {
            List<GeoPoint> geoIntersections = geo.findGeoIntersections(ray, maxDistance);
            if (geoIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geoIntersections);
            }

        }
        return intersections;
    }
    public void makeBVH() {
        List<Intersectable> intersectables = BoundingBox.buildBVH(geometries);
        geometries.clear();
        geometries.addAll(intersectables);
    }
}
