package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents the ambient light in a scene, which illuminates all objects equally
 * regardless of their position or orientation.
 */
public class AmbientLight {

    /** The intensity of the ambient light. */
    private final Color intensity;

    /** A constant representing no ambient light. */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructs an ambient light with a given intensity and scale factor.
     *
     * @param intensity the base intensity of the ambient light
     * @param scale the scale factor for the intensity
     */
    public AmbientLight(Color intensity, Double3 scale) {
        this.intensity = intensity.scale(scale);
    }

    /**
     * Constructs an ambient light with a given intensity and scale factor.
     *
     * @param intensity the base intensity of the ambient light
     * @param scale the scale factor for the intensity
     */
    public AmbientLight(Color intensity, double scale) {
        this.intensity = intensity.scale(scale);
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
