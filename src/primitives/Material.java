package primitives;

/**
 * Represents the material properties of an object, including diffuse and specular coefficients,
 * reflection and transmission coefficients, and shininess for specular highlights.
 */
public class Material {

    /** Diffuse reflection coefficient indicating the intensity of the diffuse light */
    public Double3 kD = Double3.ZERO;

    /** Specular reflection coefficient indicating the intensity of the specular light */
    public Double3 kS = Double3.ZERO;

    /** Shininess exponent for specular highlights */
    public int nShininess = 0;

    /** Reflection coefficient indicating the intensity of the reflected light */
    public Double3 kR = Double3.ZERO;

    /** Transmission coefficient indicating the intensity of the refracted light */
    public Double3 kT = Double3.ZERO;

    /**
     * Sets the reflection coefficient (kR) of the material.
     *
     * @param kr the reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKR(Double3 kr) {
        this.kR = kr;
        return this;
    }

    /**
     * Sets the transmission coefficient (kT) of the material.
     *
     * @param kt the transmission coefficient to set
     * @return the updated Material object
     */
    public Material setKT(Double3 kt) {
        this.kT = kt;
        return this;
    }

    /**
     * Sets the transmission coefficient (kT) of the material using a scalar value.
     *
     * @param kt the scalar value for the transmission coefficient to set
     * @return the updated Material object
     */
    public Material setKT(double kt) {
        this.kT = new Double3(kt);
        return this;
    }

    /**
     * Sets the reflection coefficient (kR) of the material using a scalar value.
     *
     * @param kr the scalar value for the reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKR(double kr) {
        this.kR = new Double3(kr);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material using a scalar value.
     *
     * @param kd the scalar value for the diffuse reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKD(double kd) {
        this.kD = new Double3(kd);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material using a scalar value.
     *
     * @param ks the scalar value for the specular reflection coefficient to set
     * @return the updated Material object
     */
    public Material setKS(double ks) {
        this.kS = new Double3(ks);
        return this;
    }

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
