package lighting;

import primitives.*;

/**
 * Represents a spot light source in a scene, which emits light in a specific direction from a point.
 */
public class SpotLight extends PointLight {

    /** The direction in which the spot light is pointing */
    private Vector direction;

    /** The narrow beam of the spot light */
    private double narrowBeam = 1;

    /**
     * Constructs a spot light with a given intensity, position, and direction.
     *
     * @param intensity the intensity of the spot light
     * @param position the position of the spot light in 3D space
     * @param direction the direction in which the spot light is pointing
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize(); // Normalize the direction vector
    }

    /**
     * Sets the constant attenuation factor (kC) of the spot light.
     *
     * @param kC the constant attenuation factor to set
     * @return the updated SpotLight object
     */
    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor (kL) of the spot light.
     *
     * @param kL the linear attenuation factor to set
     * @return the updated SpotLight object
     */
    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor (kQ) of the spot light.
     *
     * @param kQ the quadratic attenuation factor to set
     * @return the updated SpotLight object
     */
    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    /**
     * Retrieves the intensity of the spot light at a specific point, considering the light's direction.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the spot light at the given point, factoring in directionality
     */
    @Override
    public Color getIntensity(Point p) {
        double dirDotL = direction.dotProduct(getL(p)); // Dot product of direction and light direction to point
        return super.getIntensity(p).scale(Math.max(0, dirDotL)); // Scale intensity by maximum of 0 and dot product
    }

    /**
     * Retrieves the direction vector from the spot light towards a specific point.
     *
     * @param p the point towards which the direction is evaluated
     * @return the direction vector towards the given point
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p); // Delegate to the PointLight implementation
    }
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
