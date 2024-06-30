package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a scene, which illuminates all objects uniformly
 * from a specified direction.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a directional light with a given direction and intensity.
     *
     * @param direction the direction from which the light is coming
     * @param intensity the intensity of the light
     */
    public DirectionalLight(Vector direction, Color intensity) {
        super(intensity);
        this.direction = direction.normalize(); // Normalize the direction vector
    }

    /**
     * Retrieves the intensity of the directional light at a specific point.
     *
     * @param p the point at which the intensity is evaluated (not used in this implementation)
     * @return the intensity of the directional light
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    /**
     * Retrieves the direction vector of the light at a specific point.
     *
     * @param p the point from which the direction is evaluated (not used in this implementation)
     * @return the direction vector of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize(); // Ensure the direction vector is normalized
    }
}
