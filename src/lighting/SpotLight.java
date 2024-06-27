package lighting;

import primitives.*;

import static java.lang.Math.max;

public class SpotLight extends PointLight {

    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        double dirDotL = direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(max(0, dirDotL));
    }
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
