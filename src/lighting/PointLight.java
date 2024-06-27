package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource {

    protected Point position;

    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        double denominator = kC + kL * d + kQ * d * d;
        return intensity.scale(1 / denominator);
    }

    @Override
    public Vector getL(Point p) {
        return position.subtract(p).normalize();
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
