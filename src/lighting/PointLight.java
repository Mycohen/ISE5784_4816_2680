package lighting;

import primitives.*;

/**
 * Represents a point light source in a scene, which emits light equally in all directions from a specific point.
 */
public class PointLight extends Light implements LightSource {

    /** The position of the point light source */
    protected Point position;

    /** Constant attenuation factor */
    private double kC = 1;

    /** Linear attenuation factor */
    private double kL = 0;

    /** Quadratic attenuation factor */
    private double kQ = 0;

    /**
     * Constructs a point light with a given intensity and position.
     *
     * @param intensity the intensity of the point light
     * @param position the position of the point light in 3D space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Retrieves the intensity of the point light at a specific point.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the point light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        double denominator = kC + kL * d + kQ * (d * d);
        return intensity.scale(1 / denominator);
    }

    /**
     * Retrieves the direction vector from the point light source towards a specific point.
     *
     * @param p the point towards which the direction is evaluated
     * @return the direction vector towards the given point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * Sets the constant attenuation factor (kC) of the point light.
     *
     * @param kC the constant attenuation factor to set
     * @return the updated PointLight object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor (kL) of the point light.
     *
     * @param kL the linear attenuation factor to set
     * @return the updated PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor (kQ) of the point light.
     *
     * @param kQ the quadratic attenuation factor to set
     * @return the updated PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Sets the position of the point light.
     *
     * @param position the new position of the point light
     * @return the updated PointLight object
     */
    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }
}
