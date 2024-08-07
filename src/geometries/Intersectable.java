package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Abstract class representing an intersectable geometry in 3D space.
 * <p>
 * This class provides methods to find intersections of a ray with the geometry and to manage bounding boxes
 * for efficient intersection tests. Implementing classes should define the specific logic to find intersections.
 * </p>
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 * @see Point
 * @see Ray
 * @see List
 */
public abstract class Intersectable {

    /**
     * The bounding box that encloses the geometry.
     * <p>
     * Bounding boxes are used to quickly determine whether a ray may intersect the geometry,
     * thus optimizing intersection calculations.
     * </p>
     */
    protected BoundingBox boundingBox;

    /**
     * Finds all intersection points between a ray and the geometry.
     *
     * @param ray The ray to test for intersections.
     * @return A list of intersection points, or null if no intersections are found.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds all intersection points between a ray and the geometry,
     * including the geometry object associated with each intersection.
     *
     * @param ray The ray to test for intersections.
     * @return A list of {@code GeoPoint} objects representing the intersections,
     * or null if no intersections are found.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds all intersection points between a ray and the geometry within a specified distance.
     *
     * @param ray The ray to test for intersections.
     * @param maxDistance The maximum distance from the ray's origin to consider for intersections.
     * @return A list of {@code GeoPoint} objects representing the intersections within the specified distance,
     * or null if no intersections are found.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Gets the bounding box that encloses the geometry.
     *
     * @return The bounding box of the geometry.
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Helper method that finds all intersection points between a ray and the geometry within a specified distance.
     * Implementing classes should define this method to perform the actual intersection calculations.
     *
     * @param ray The ray to test for intersections.
     * @param maxDistance The maximum distance from the ray's origin to consider for intersections.
     * @return A list of {@code GeoPoint} objects representing the intersections within the specified distance,
     * or null if no intersections are found.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Static inner class representing a geometric point and the associated geometry.
     * <p>
     * This class is used to store the intersection points between a ray and the geometry,
     * along with the geometry object itself.
     * </p>
     */
    public static class GeoPoint {
        public Geometry geometry; // The geometry object associated with this intersection point.
        public Point point; // The intersection point.

        /**
         * Constructs a {@code GeoPoint} with the specified geometry and intersection point.
         *
         * @param geometry The geometry associated with this intersection.
         * @param point The intersection point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Determines whether this {@code GeoPoint} is equal to another object.
         * <p>
         * Two {@code GeoPoint} objects are considered equal if they have the same geometry and point.
         * </p>
         *
         * @param o The object to compare with this {@code GeoPoint}.
         * @return {@code true} if the objects are equal, {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        /**
         * Returns a string representation of the {@code GeoPoint}.
         *
         * @return A string representation of the {@code GeoPoint}.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
