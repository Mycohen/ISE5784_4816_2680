package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * Class representing a sphere in three-dimensional space.
 * A sphere is defined by its center point and a radius.
 */
public class Sphere implements Geometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructor initializing the sphere with a specified center point.
     *
     * @param myCenter the center point of the sphere
     */
    public Sphere(Point myCenter) {
        this.center = myCenter;
    }

    /**
     * This method is not implemented and returns null.
     * Consider removing or implementing this method.
     *
     * @return null
     */
    public Vector getNormal() {
        return null;
    }

    /**
     * Gets the normal vector to the sphere at a given point.
     * Currently returns null. Needs to be implemented.
     *
     * @param p the point on the surface of the sphere
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
