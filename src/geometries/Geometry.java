package geometries;

import primitives.Material;
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
     * <p>
     * Default emission color is black, meaning the geometry does not emit any light by default.
     * </p>
     */
    protected Color emission = Color.BLACK;

    /**
     * The material properties of the geometry.
     * <p>
     * This includes properties like reflectivity, transparency, and shininess,
     * which affect how the geometry interacts with light.
     * </p>
     */
    private Material material = new Material();

    /**
     * Gets the material properties of the geometry.
     *
     * @return the material properties of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material properties of the geometry.
     *
     * @param material the material properties to set
     * @return the geometry itself (for method chaining)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Calculates the normal vector to the geometry at a given point.
     * <p>
     * The normal vector is perpendicular to the surface of the geometry at the given point.
     * Implementing classes must provide the specific logic to calculate this vector
     * based on the shape's geometry.
     * </p>
     *
     * @param p the point on the geometry where the normal is to be calculated
     * @return the normal vector to the geometry at the specified point
     */
    public abstract Vector getNormal(Point p);

    /**
     * Gets the emission color of the geometry.
     * <p>
     * The emission color represents the color that the geometry appears to emit,
     * contributing to the overall lighting of the scene.
     * </p>
     *
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     * <p>
     * This method allows you to set a color that the geometry will appear to emit.
     * This is often used for objects that are self-illuminating in a scene.
     * </p>
     *
     * @param emission the emission color to set
     * @return the geometry itself (for method chaining)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
