package primitives;

public class Material {
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return this;
    }
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setKs(double ks) {
        this.kS = new Double3(ks);
        return this;
    }
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
