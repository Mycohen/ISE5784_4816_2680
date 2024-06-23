package renderer;

import scene.Scene;
import primitives.Color;
import primitives.Ray;


/**
 * Abstract base class for a ray tracer.
 * This class defines the common interface and holds the scene that will be rendered.
 */
public abstract class RayTracerBase {

    /** The scene to be rendered by the ray tracer */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene the scene to be rendered by this ray tracer
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color at the intersection point.
     * This method must be implemented by subclasses to provide the specific ray tracing logic.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
