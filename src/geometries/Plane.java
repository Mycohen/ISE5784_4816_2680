package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Plane class represents a plane in a 3D Cartesian coordinate system.
 * A plane is defined by a point and a normal vector.
 *
 * <p>This class provides methods to create a plane from three non-collinear points or from a point and a normal vector.
 * It also provides methods to retrieve the normal vector to the plane.</p>
 *
 * @see Geometry
 * @see Point
 * @see Vector
 */
public class Plane implements Geometry {
    /** A point on the plane */
    private final Point q;

    /** The normal vector to the plane */
    private final Vector normal;

    /**
     * Constructor to create a Plane given three points in space.
     * The three points must not be collinear.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @param p3 the third point
     * @throws IllegalArgumentException if the three points are collinear
     */
    public Plane(Point p1, Point p2, Point p3) {

        // Calculate two vectors lying on the plane
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        // Check that the vectors are not collinear
        if (v1.crossProduct(v2).length() == 0) {
            throw new IllegalArgumentException("The three points are collinear");
        }
        q = p1;
        // Calculate the normal vector using the cross product of the two vectors
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructor to create a Plane given a point and a normal vector.
     *
     * @param p the point on the plane
     * @param vNormal the normal vector to the plane
     */
    public Plane(Point p, Vector vNormal) {
        this.q = p;
        this.normal = vNormal.normalize();
    }

    /**
     * Gets the normalized normal vector to the plane.
     *
     * @return the normalized normal vector
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * Gets the normalized normal vector to the plane at a given point.
     * Since the plane is flat, this is the same as the general normal vector.
     *
     * @param p the point on the plane
     * @return the normalized normal vector at the given point
     */
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {

        if(ray.getHead().equals(q))
            return null;

        // Compute the numerator of the intersection formula
        Vector pq = this.q.subtract(ray.getHead());
        double numerator = this.normal.dotProduct(pq);

        // Compute the denominator of the intersection formula
        double denominator = this.normal.dotProduct(ray.getDirection());

        // If the denominator is zero, the ray is parallel to the plane (no intersection)
        if (denominator == 0) {
            return null;
        }

        // Calculate the parameter t for the ray equation
        double t = alignZero(numerator / denominator) ;

        // If t is negative, the intersection point is behind the ray's origin (no valid intersection)
        if (t <= 0) {
            return null;
        }

        // Calculate the intersection point using the ray's equation
        Point intersectionPoint = ray.getHead().add(ray.getDirection().scale(t));

        // Return the intersection point as a list
        return List.of(intersectionPoint);
    }
}
