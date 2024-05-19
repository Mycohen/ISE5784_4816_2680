package geometries;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Cylinder class represents a cylinder in a 3D Cartesian coordinate system.
 * A cylinder is defined by its height and extends a tube.
 */
public class Cylinder extends Tube {
    // The height of the cylinder
    private final double height;

    /**
     * Constructor to create a Cylinder with a specified height.
     *
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * Gets the normal vector to the cylinder at a given point.
     * Note: This method currently returns null and needs to be implemented.
     *
     * @param p the point on the cylinder
     * @return the normal vector at the given point (currently null)
     */
    @Override
    public Vector getNormal(Point p) {
        // Placeholder implementation
        return null;
    }



}
