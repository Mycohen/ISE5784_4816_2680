package renderer;
import primitives.*;
import geometries.*;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase{
    public SimpleRayTracer(scene.Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null)
            return scene.background;
        else return calcColor(ray.findClosestPoint(intersections));

    }

    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();

    }
}
