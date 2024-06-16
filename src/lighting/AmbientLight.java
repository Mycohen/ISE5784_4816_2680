package lighting;
import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    private final Color intensity;

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    public AmbientLight(Color intensity, Double3 scale) {
        this.intensity = intensity.scale(scale);
    }
    public AmbientLight(Color intensity, double scale) {
        this.intensity = intensity.scale(scale);
    }
    public Color getIntensity() {
        return intensity;
    }





}
