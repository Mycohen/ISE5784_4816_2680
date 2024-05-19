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
     * Constructor initializing the sphere with a specified center point.
     *
     * @param myCenter the center point of the sphere
     */
    public Sphere(double radius, Point myCenter) {
        super(radius);
        this.center = myCenter;
    }


    /**
     * Gets the normal vector to the sphere at a given point.
     * Currently, returns null. Needs to be implemented.
     *
     * @param p the point on the surface of the sphere
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
