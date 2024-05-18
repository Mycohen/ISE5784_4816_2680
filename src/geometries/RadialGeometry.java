package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class representing radial geometry in three-dimensional space.
 * This class can be extended by geometries that are defined by a radius, such as spheres and tubes.
 */
public class RadialGeometry { // implements Geometry

    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructor initializing the radial geometry with a specified radius.
     *
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
