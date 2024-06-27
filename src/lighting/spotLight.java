package lighting;

import primitives.*;

import static java.lang.Math.max;

public class spotLight extends PointLight {

    private Vector direction;

    public spotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    public spotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    public spotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    public spotLight setKq(double kQ) {
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
