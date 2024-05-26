package geometries;

import primitives.Point;
import primitives.Vector;

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
     * @param x the first point
     * @param y the second point
     * @param z the third point
     * @throws IllegalArgumentException if the three points are collinear
     */
    public Plane(Point x, Point y, Point z) {
        q = x;
        // Calculate two vectors lying on the plane
        Vector v1 = x.subtract(y);
        Vector v2 = x.subtract(z);

        // Check that the vectors are not collinear
        if (v1.crossProduct(v2).length() == 0) {
            throw new IllegalArgumentException("The three points are collinear");
        }
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
}
