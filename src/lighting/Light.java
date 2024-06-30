package lighting;

import primitives.Color;

/**
 * Represents a generic light source.
 * Provides methods to get the intensity of the light.
 *
 * @author Moshe Yaakov Cohen
 * @author Eliaou Kopinski
 */
public class Light {

    /** The intensity of the light */
    protected Color intensity;

    /**
     * Constructs a light with the specified intensity.
     *
     * @param intensity The intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return The intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
