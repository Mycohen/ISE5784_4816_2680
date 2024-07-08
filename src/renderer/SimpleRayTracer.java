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
    private static final double DELTA = 0.1;

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
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Calculates the color of the intersection point by considering the ambient light and local effects.
     *
     * @param intersection the intersection point
     * @param ray          the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the color of the intersection point by considering the local effects only.
     *
     * @param gp  the intersection point
     * @param ray the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return null;
        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(gp, l, n,nl, lightSource)){
                //sign(nl) == sign(nv);

                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
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
     * @param n        the normal vector at the intersection point
     * @param l        the light direction vector
     * @param nl       the dot product of the normal and the light direction
     * @param v        the view direction vector
     * @return the specular component as a Double3
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = -alignZero(v.dotProduct(r));
        double max = Math.max(0, vr);
        return material.kS.scale(Math.pow(max, material.nShininess));
    }
    /**
     * Checks if the point is shaded by other geometries.
     *
     * @param l the light direction vector
     * @param n the normal vector at the intersection point
     * @return true if the point is shaded, false otherwise
     */
   private boolean unshaded (GeoPoint gp, Vector l, Vector n,double nl, LightSource light) {
       Vector lightDirection = l.scale(-1); // from point to light source
       Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
       Point point = gp.point.add(epsVector);
       Ray lightRay = new Ray(point, lightDirection);
       List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
       if (intersections == null) return true;
       double lightDistance = light.getDistance(gp.point);
       for (GeoPoint geoPoint : intersections) {
           if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0)
               return false;
       }
         return true;

   }

}
