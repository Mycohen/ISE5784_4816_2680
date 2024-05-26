package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * Class representing a sphere in three-dimensional space.
 * A sphere is defined by its center point and a radius.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructor initializing the sphere with a specified center point and radius.
     *
     * @param radius the radius of the sphere
     * @param myCenter the center point of the sphere
     */
    public Sphere(double radius, Point myCenter) {
        super(radius);
        this.center = myCenter;
    }

    /**
     * Gets the normal vector to the sphere at a given point.
     *
     * @param p the point on the surface of the sphere
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point is at the center of the sphere
     */
    @Override
    public Vector getNormal(Point p) {
        // Calculate the vector from the center of the sphere to the point p
        Vector normalVector = p.subtract(center);

        // Check if the point is the center of the sphere (should not happen for valid sphere surface points)
        if (normalVector.length() == 0) {
            throw new IllegalArgumentException("Point cannot be the center of the sphere");
        }

        // Normalize the vector to get the normal vector
        return normalVector.normalize();
    }
}
