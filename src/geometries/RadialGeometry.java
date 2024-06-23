package geometries;

/**
 * Class representing radial geometry in three-dimensional space.
 * This class can be extended by geometries that are defined by a radius, such as spheres and tubes.
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructor initializing the radial geometry with a specified radius.
     *
     * @param radius the radius of the radial geometry
     * @throws IllegalArgumentException if the radius is less than or equal to zero
     */
    public RadialGeometry(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
        this.radius = radius;
    }
}
