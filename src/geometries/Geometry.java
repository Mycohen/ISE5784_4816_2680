package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Color;

/**
 * Abstract class representing geometric shapes in a 3D space.
 * <p>
 * This abstract class provides a method to calculate the normal vector to the shape at a given point.
 * Implementing classes should provide the logic to calculate the normal vector specific to the shape.
 * </p>
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 * @see Vector
 * @see Point
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry.
     * Default emission color is black.
     *
     */
    protected Color emission = Color.BLACK;

    /**
     * Calculates the normal vector to the geometry at a given point.
     *
     * @param p the point on the geometry where the normal is to be calculated
     * @return the normal vector to the geometry at the specified point
     */
    public abstract Vector getNormal(Point p);

    /**
     * Gets the emission color of the geometry.
     *
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission the emission color to set
     * @return the geometry itself (for method chaining)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
