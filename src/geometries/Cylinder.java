package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The {@code Cylinder} class represents a cylinder in a 3D Cartesian coordinate system.
 * A cylinder is defined by its height and extends the {@code Tube} class.
 *
 * <p>A cylinder has a circular base and top with the same radius, and it extends vertically
 * to a certain height.</p>
 *
 * @see Tube
 * @see Ray
 * @see Point
 * @see Vector
 */
public class Cylinder extends Tube {
    /** The height of the cylinder */
    private final double height;

    /**
     * Constructs a {@code Cylinder} with a specified radius, axis ray, and height.
     *
     * @param radius the radius of the cylinder
     * @param axis the axis ray of the cylinder
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if the height is less than or equal to 0
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    /**
     * Gets the normal vector to the cylinder at a given point.
     *
     * <p>The normal vector is calculated based on the position of the point relative
     * to the cylindrical surface and the top or bottom bases.</p>
     *
     * @param p the point on the cylinder
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point is exactly on the axis of the cylinder
     */
    @Override
    public Vector getNormal(Point p) {
        Point p0 = axis.getHead();
        Vector dir = axis.getDirection();

        // Vector from p0 to the point p
        Vector p0ToP = p.subtract(p0);

        // Project p0ToP onto the direction vector dir
        double t = dir.dotProduct(p0ToP);

        // Check if the point is on the bottom base
        if (t <= 0) {
            return dir.scale(-1); // Normal is in the opposite direction of the axis
        }

        // Check if the point is on the top base
        if (t >= this.height) {
            return dir; // Normal is in the direction of the axis
        }

        // Point is on the curved surface
        Point o = p0.add(dir.scale(t)); // Projection of p onto the axis
        Vector normal = p.subtract(o);

        if (normal.length() == 0) {
            // Point is exactly on the axis (should not happen for valid cylinder surface points)
            throw new IllegalArgumentException("Point cannot be on the axis of the cylinder");
        }

        return normal.normalize(); // Normal vector is radial
    }
}
