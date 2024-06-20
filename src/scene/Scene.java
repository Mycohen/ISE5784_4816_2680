package scene;

import geometries.Geometries;
import primitives.*;
import lighting.AmbientLight;

/**
 * Represents a 3D scene containing geometries, background color, ambient light,
 * and other settings.
 */
public class Scene {

    /** The name of the scene. */
    public String Name;

    /** The background color of the scene. */
    public Color background;

    /** The ambient light in the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The collection of geometries in the scene. */
    public Geometries geometries = new Geometries();

    /**
     * Constructs a new Scene with the given name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        Name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background the background color
     * @return the Scene object itself (for method chaining)
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light
     * @return the Scene object itself (for method chaining)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries the collection of geometries
     * @return the Scene object itself (for method chaining)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
