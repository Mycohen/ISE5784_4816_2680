package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source in a scene.
 * Defines methods to retrieve the intensity of the light and the direction towards a point.
 */
public interface LightSource {

    /**
     * Retrieves the intensity of the light at a specific point.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction vector from the light source towards a specific point.
     *
     * @param p the point towards which the direction is evaluated
     * @return the direction vector towards the given point
     */
    public Vector getL(Point p);
    public double getDistance(Point p);

}
