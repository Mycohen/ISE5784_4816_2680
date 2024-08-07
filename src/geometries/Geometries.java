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

    /**
     * Adds one or more geometrical objects to the collection.
     *
     * @param geometries The geometrical objects to add to the collection.
     */
    public void add(Intersectable... geometries) {
        // Adds all the given geometries to the internal list
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * Finds all intersections of the given ray with the geometrical objects
     * in the collection within a specified maximum distance.
     *
     * @param ray The ray to intersect with the geometrical objects.
     * @param maxDistance The maximum distance within which to find intersections.
     * @return A list of intersection points, or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null; // List to store all intersections
        for (Intersectable geo : geometries) {
            // Find intersections with the current geometry
            List<GeoPoint> geoIntersections = geo.findGeoIntersections(ray, maxDistance);
            if (geoIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                // Add the intersections to the list
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }

    /**
     * Constructs a Bounding Volume Hierarchy (BVH) for the geometrical objects
     * in the collection to optimize intersection tests.
     *
     * <p>This method replaces the current list of geometries with a new list
     * where the objects are organized into a BVH structure.</p>
     */
    public void makeBVH() {
        // Build the BVH from the current geometries
        List<Intersectable> intersectables = BoundingBox.buildBVH(geometries);
        geometries.clear(); // Clear the current geometries
        geometries.addAll(intersectables); // Add the BVH-optimized geometries
    }
}
