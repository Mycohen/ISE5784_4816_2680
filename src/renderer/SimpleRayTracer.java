package renderer;

import lighting.LightSource;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A simple implementation of a ray tracer.
 * This class extends RayTracerBase and provides a basic ray tracing mechanism.
 */
public class SimpleRayTracer extends RayTracerBase {

    /** A small constant value used to move the starting point of a secondary ray slightly away from the surface to avoid self-intersection. */
    private static final double DELTA = 0.1;

    /** The maximum recursion level for calculating global effects such as reflections and refractions. */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /** The minimum value of the attenuation coefficient for global effects below which the effect is ignored. */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /** The initial attenuation coefficient for global effects. */
    private static final Double3 INITIAL_K = Double3.ONE;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of the intersection point by considering the ambient light and local effects.
     *
     * @param geoPoint the intersection point
     * @param ray      the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at the intersection point with the given recursion level and attenuation coefficient.
     *
     * @param geoPoint the intersection point
     * @param ray      the ray that intersects the geometry
     * @param level    the current recursion level
     * @param k        the current attenuation coefficient
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color localEffects = calcLocalEffects(geoPoint, ray, k);
        return level == 1 ? localEffects : localEffects.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the global effects (reflections and refractions) at the intersection point.
     *
     * @param geoPoint the intersection point
     * @param ray      the ray that intersects the geometry
     * @param level    the current recursion level
     * @param k        the current attenuation coefficient
     * @return the color contribution from global effects
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        Vector direction = ray.getDirection();
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        return calcGlobalEffect(constructRefractedRay(geoPoint, direction, normal), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint, direction, normal), material.kR, level, k));
    }

    /**
     * Calculates the color contribution from a single global effect (reflection or refraction).
     *
     * @param ray      the secondary ray (reflection or refraction)
     * @param kx       the attenuation coefficient for the effect
     * @param level    the current recursion level
     * @param k        the current attenuation coefficient
     * @return the color contribution from the global effect
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK; // Return no contribution if the combined coefficient is too small
        }

        GeoPoint geoPoint = findClosestIntersection(ray);
        return geoPoint == null ? scene.background // If no intersection found, return background color
                : calcColor(geoPoint, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Constructs a refracted ray from the given intersection point.
     *
     * @param geoPoint the intersection point
     * @param direction the direction of the incoming ray
     * @param normal    the normal vector at the intersection point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector direction, Vector normal) {
        return new Ray(geoPoint.point, direction, normal);
    }

    /**
     * Constructs a reflected ray from the given intersection point.
     *
     * @param geoPoint the intersection point
     * @param direction the direction of the incoming ray
     * @param normal    the normal vector at the intersection point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector direction, Vector normal) {
        double nv = normal.dotProduct(direction);
        if (nv == 0) {
            return null;
        }

        Vector reflectedDirection = direction.subtract(normal.scale(2 * nv));
        return new Ray(geoPoint.point, reflectedDirection, normal);
    }

    /**
     * Finds the closest intersection point between a ray and the geometries in the scene.
     *
     * @param ray the ray to be traced
     * @return the closest intersection point, or null if no intersections are found
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Calculates the color of the intersection point by considering the local effects only.
     *
     * @param geoPoint the intersection point
     * @param ray      the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        Vector viewDirection = ray.getDirection();
        double nv = alignZero(normal.dotProduct(viewDirection));
        Color color = geoPoint.geometry.getEmission().add(scene.ambientLight.getIntensity());
        if (nv == 0)
            return color;
        Material material = geoPoint.geometry.getMaterial();
        for (LightSource light : scene.lights) {
            Vector l = light.getL(geoPoint.point);
            double nl = alignZero(normal.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(geoPoint, light, l, normal);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = light.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl).add(calcSpecular(material, normal, l, nl, viewDirection))));
                }
            }
        }

        return color;
    }

    /**
     * Calculates the diffusive component of the lighting model.
     *
     * @param material the material of the intersected geometry
     * @param nl       the dot product of the normal and the light direction
     * @return the diffusive component as a Double3
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular component of the lighting model.
     *
     * @param material the material of the intersected geometry
     * @param normal   the normal vector at the intersection point
     * @param lightDirection the light direction vector
     * @param nl       the dot product of the normal and the light direction
     * @param viewDirection the view direction vector
     * @return the specular component as a Double3
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightDirection, double nl, Vector viewDirection) {
        Vector reflectionDirection = lightDirection.subtract(normal.scale(2 * nl));
        double viewReflectionDotProduct = -alignZero(viewDirection.dotProduct(reflectionDirection));
        double max = Math.max(0, viewReflectionDotProduct);
        return material.kS.scale(Math.pow(max, material.nShininess));
    }

    /**
     * Calculates the transparency of an object by tracing a ray from the intersection point towards the light source.
     *
     * @param geoPoint the intersection point
     * @param ls       the light source
     * @param l        the light direction vector
     * @param n        the normal vector at the intersection point
     * @return the transparency coefficient as a Double3
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection, n);
        // Check if the light source is visible from the intersection point
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        // If no intersections found, the light source is visible
        if (intersections == null)
            return Double3.ONE;
        double lightDistance = ls.getDistance(geoPoint.point);
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            // Check if the intersection point is closer to the light source than the geometry
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
                ktr = ktr.product(gp.geometry.getMaterial().kT);
            // If the transparency coefficient is below the threshold, return it
            if (ktr.equals(Double3.ZERO))
                break;
        }
        return ktr;
    }
}
