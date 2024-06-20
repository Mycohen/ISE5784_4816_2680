package renderer;

import primitives.*;
import geometries.*;

import java.util.List;

/**
 * A simple implementation of a ray tracer.
 * This class extends RayTracerBase and provides a basic ray tracing mechanism.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene the scene to be rendered by this ray tracer
     */
    public SimpleRayTracer(scene.Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and returns the color at the intersection point.
     * If there are no intersections, the background color of the scene is returned.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point or the background color if no intersections are found
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        } else {
            return calcColor(ray.findClosestPoint(intersections));
        }
    }

    /**
     * Calculates the color at the given point.
     * This implementation returns the ambient light intensity of the scene.
     *
     * @param point the point at which to calculate the color
     * @return the ambient light intensity of the scene
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
