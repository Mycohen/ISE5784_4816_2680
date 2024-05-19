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
     * Constructor initializing the tube with a specified axis ray.
     *
     * @param axis the axis ray of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }


    /**
     * Gets the normal vector to the tube at a given point.
     * Currently returns null. Needs to be implemented.
     *
     * @param p the point on the surface of the tube
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
