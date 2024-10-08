package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Class representing a ray in three-dimensional space.
 *
 * This class defines a ray with a starting point and a direction vector.
 * It provides methods for ray manipulation and intersection calculations.
 *
 * @authors Moshe Yaakov Cohen, Eliaou Kopinski
 */
public class Ray {

    /** The starting point of the ray. */
    private final Point head;

    /** The direction vector of the ray (normalized). */
    private final Vector direction;

    /** Small delta value for numerical stability in calculations. */
    private static final double DELTA = 0.1;

    /**
     * Constructor initializing the ray with a starting point and direction vector.
     *
     * @param p0 the starting point of the ray
     * @param dir the direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        // Setting the head to the specified starting point
        head = p0;
        // Normalizing and setting the direction vector
        direction = dir.normalize();
    }

    /**
     * Constructor initializing the ray with a starting point, direction vector, and normal vector for numerical stability.
     *
     * @param p0 the starting point of the ray
     * @param dir the direction vector of the ray
     * @param normal the normal vector for numerical stability adjustment
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        direction = dir.normalize();
        double nv = normal.dotProduct(this.direction);
        Vector deltaV = normal.scale(nv < 0 ? -DELTA : DELTA);
        head = p0.add(deltaV);
    }

    /**
     * Calculates a point along the ray at a given parameter value t.
     *
     * @param t the parameter value
     * @return the point along the ray at parameter t
     */
    public Point getPoint(double t) {
        if (Util.isZero(t))
            return this.head;
        return this.head.add(this.direction.scale(t));
    }

    /**
     * Gets the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDirection() {
        return this.direction;
    }

    /**
     * Gets the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getHead() {
        return this.head;
    }

    /**
     * Finds the closest point to the head of the ray from a list of points.
     *
     * @param points the list of points to search in
     * @return the closest point to the head of the ray, or null if the list is empty or null
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint to the head of the ray from a list of GeoPoints.
     *
     * @param geoPoints the list of GeoPoints to search in
     * @return the closest GeoPoint to the head of the ray, or null if the list is empty or null
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        GeoPoint closest = null;

        if (geoPoints == null) {
            return null;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint geoPo : geoPoints) {
            double distance = this.head.distance(geoPo.point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = geoPo;
            }
        }
        return closest;
    }

    /**
     * Override equals method to compare rays' starting points and direction vectors.
     *
     * @param o the object to compare with
     * @return true if the rays are equal, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    /**
     * Override hashCode method for consistency with equals method.
     *
     * @return the hash code of the ray
     */
    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    /**
     * Override toString method to represent the ray as a string.
     *
     * @return the string representation of the ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
