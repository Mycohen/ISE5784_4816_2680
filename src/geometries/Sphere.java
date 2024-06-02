package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class representing a sphere in three-dimensional space.
 * A sphere is defined by its center point and a radius.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructor initializing the sphere with a specified center point and radius.
     *
     * @param radius the radius of the sphere
     * @param myCenter the center point of the sphere
     */
    public Sphere(double radius, Point myCenter) {
        super(radius);
        this.center = myCenter;
    }

    /**
     * Gets the normal vector to the sphere at a given point.
     *
     * @param p the point on the surface of the sphere
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point is at the center of the sphere
     */
    @Override
    public Vector getNormal(Point p) {
        // Calculate the vector from the center of the sphere to the point p
        Vector normalVector = p.subtract(center);

        // Check if the point is the center of the sphere (should not happen for valid sphere surface points)
        if (normalVector.length() == 0) {
            throw new IllegalArgumentException("Point cannot be the center of the sphere");
        }

        // Normalize the vector to get the normal vector
        return normalVector.normalize();
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        if(this.center.equals(ray.getHead())) {
            return List.of(ray.getHead().add(ray.getDirection().scale(this.radius)));
        }
        Vector U = this.center.subtract(ray.getHead());
        double tm = ray.getDirection().dotProduct(U);
        double dSquared = U.lengthSquared() - tm * tm;
        double radiusSquared = this.radius * this.radius;

        // If dSquared is greater than or equal to radiusSquared, there are no intersections
        if (dSquared >= radiusSquared) {
            return null;
        }

        double th = Math.sqrt(radiusSquared - dSquared);
        double t1 = tm - th;
        double t2 = tm + th;

        if (alignZero(t1) > 0 && alignZero(t2) > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)),
                    ray.getHead().add(ray.getDirection().scale(t2)));
        } else if (alignZero(t1 )> 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)));
        } else if (alignZero(t2) > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t2)));
        } else {
            return null;


        }
    }


}
