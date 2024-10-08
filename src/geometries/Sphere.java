package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class representing a sphere in three-dimensional space.
 * A sphere is defined by its center point and a radius.
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructor initializing the sphere with a specified center point and radius.
     *
     *
     * @param radius the radius of the sphere
     * @param myCenter the center point of the sphere
     */
    public Sphere(double radius, Point myCenter) {
        super(radius);
        this.center = myCenter;
        // Calculate and set bounding box
        Point min = new Point(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
        Point max = new Point(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        this.boundingBox = new BoundingBox(min, max);
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

    /**
     * Finds the intersection points of a given ray with the sphere.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        if (boundingBox != null && !boundingBox.hasIntersection(ray)) {
            return null;  // No intersection with the bounding box, so no need to check further
        }
        if (this.center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this, ray.getHead().add(ray.getDirection().scale(this.radius))));
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
        if(t1 > maxDistance || t2 > maxDistance) return null;


        if (alignZero(t1) > 0 && alignZero(t2) > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
        } else if (alignZero(t1) > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        } else if (alignZero(t2) > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        } else {
            return null;
        }
    }
}
