package primitives;

/**
 * Represents the material properties of an object, including diffuse and specular coefficients,
 * and shininess for specular highlights.
 */
public class Material {

    /** Diffuse reflection coefficient */
    public Double3 kD = Double3.ZERO;

    /** Specular reflection coefficient */
    public Double3 kS = Double3.ZERO;

    /** Shininess exponent for specular highlights */
    public int nShininess = 0;

    /**
     * Sets the diffuse reflection coefficient (kD) of the material.
     *
     * @param kD the diffuse reflection coefficient to set
     * @return the updated Material object
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material using a scalar value.
     *
     * @param kd the scalar value for the diffuse reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material.
     *
     * @param kS the specular reflection coefficient to set
     * @return the updated Material object
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material using a scalar value.
     *
     * @param ks the scalar value for the specular reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKs(double ks) {
        this.kS = new Double3(ks);
        return this;
    }

    /**
     * Sets the shininess exponent (nShininess) of the material.
     *
     * @param nShininess the shininess exponent to set
     * @return the updated Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
