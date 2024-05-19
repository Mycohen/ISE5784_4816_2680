package geometries;

/**
 * Class representing radial geometry in three-dimensional space.
 * This class can be extended by geometries that are defined by a radius, such as spheres and tubes.
 */
public abstract class RadialGeometry implements Geometry{ // implements Geometry

    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructor initializing the radial geometry with a specified radius.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
