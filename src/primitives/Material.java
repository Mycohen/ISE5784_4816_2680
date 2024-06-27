package primitives;

public class Material {
    public Double3 kd = Double3.ZERO;
    public Double3 ks = Double3.ZERO;
    public int nShininess = 0;
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;
    }
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
