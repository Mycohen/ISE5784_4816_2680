package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    public DirectionalLight(Vector direction, Color intensity) {
        super(intensity);
        this.direction = direction;

    }


    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }


}
