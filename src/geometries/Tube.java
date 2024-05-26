package geometries;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a tube in three-dimensional space.
 * A tube is defined by its axis (a ray) and a radius.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis ray of the tube.
     */
    protected final Ray axis;

    /**
     * Constructor initializing the tube with a specified axis ray and radius.
     *
     * @param radius the radius of the tube
     * @param axis the axis ray of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Gets the normal vector to the tube at a given point.
     *
     * @param p the point on the surface of the tube
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point is on the axis of the tube
     */
    @Override
    public Vector getNormal(Point p) {
        // Vector from the head of the axis to the point p
        Vector p0ToP = p.subtract(axis.getHead());

        // Project p0ToP onto the direction vector of the axis
        double t = axis.getDirection().dotProduct(p0ToP);

        // Calculate the projection point on the axis
        Point o = axis.getHead().add(axis.getDirection().scale(t));

        // Calculate the normal vector
        Vector n = p.subtract(o);

        // Check if the normal length is zero (point is on the axis)
        if (n.length() == 0) {
            throw new IllegalArgumentException("Point cannot be on the axis of the tube");
        }

        // Return the normalized normal vector
        return n.normalize();
    }
}
