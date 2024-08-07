package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The {@code Cylinder} class represents a cylinder in a 3D Cartesian coordinate system.
 * A cylinder is defined by its height and extends the {@code Tube} class.
 *
 * <p>A cylinder has a circular base and top with the same radius, and it extends vertically
 * to a certain height.</p>
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 *
 * @see Tube
 * @see Ray
 * @see Point
 * @see Vector
 */
public class Cylinder extends Tube {
    /** The height of the cylinder */
    private final double height;

    /**
     * Constructs a {@code Cylinder} with a specified radius, axis ray, and height.
     *
     * @param radius the radius of the cylinder
     * @param axis the axis ray of the cylinder
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if the height is less than or equal to 0
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        if (alignZero(height) <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    /**
     * Gets the normal vector to the cylinder at a given point.
     *
     * <p>The normal vector is calculated based on the position of the point relative
     * to the cylindrical surface and the top or bottom bases.</p>
     *
     * @param p the point on the cylinder
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point is exactly on the axis of the cylinder
     */
    @Override
    public Vector getNormal(Point p) {
        Point p0 = axis.getHead(); // Starting point of the cylinder's axis
        Vector dir = axis.getDirection(); // Direction of the cylinder's axis

        // Vector from p0 to the point p
        Vector p0ToP = p.subtract(p0);

        // Project p0ToP onto the direction vector dir
        double t = alignZero(dir.dotProduct(p0ToP));

        // Check if the point is on the bottom base
        if (t <= 0) {
            return dir.scale(-1); // Normal is in the opposite direction of the axis
        }

        // Check if the point is on the top base
        if (t >= this.height) {
            return dir; // Normal is in the direction of the axis
        }

        // Point is on the curved surface
        Point o = p0.add(dir.scale(t)); // Projection of p onto the axis
        Vector normal = p.subtract(o);

        if (isZero(normal.length())) {
            // Point is exactly on the axis (should not happen for valid cylinder surface points)
            throw new IllegalArgumentException("Point cannot be on the axis of the cylinder");
        }

        return normal.normalize(); // Normal vector is radial
    }

    /**
     * Finds the intersections of the given ray with the cylinder within a maximum distance.
     *
     * <p>This method solves a quadratic equation to find intersections with the cylindrical surface,
     * and checks for intersections with the top and bottom bases of the cylinder.</p>
     *
     * @param ray the ray to intersect with the cylinder
     * @param maxDistance the maximum allowable distance for intersections
     * @return a list of intersection points (if any), or null if no intersections are found
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector dir = ray.getDirection(); // Direction of the ray
        Vector v = axis.getDirection(); // Direction of the cylinder's axis
        Point p0 = ray.getHead(); // Origin point of the ray
        Point axisP0 = axis.getHead(); // Origin point of the cylinder's axis

        // Calculate coefficients for the quadratic equation
        double a = dir.lengthSquared() - Math.pow(dir.dotProduct(v), 2);
        double b = 2 * (dir.dotProduct(p0.subtract(axisP0)) - (dir.dotProduct(v)) * (p0.subtract(axisP0).dotProduct(v)));
        double c = p0.subtract(axisP0).lengthSquared() -
                Math.pow(p0.subtract(axisP0).dotProduct(v), 2) -
                radius * radius;

        // Check if ray is parallel to the cylinder axis
        if (isZero(a)) {
            if (isZero(b)) {
                return null; // Ray is on the surface or completely outside
            }
            double t = -c / b;
            if (t <= 0 || alignZero(t - maxDistance) > 0) {
                return null;
            }
            Point intersectionPoint = ray.getPoint(t);
            double pointHeight = v.dotProduct(intersectionPoint.subtract(axisP0));
            if (pointHeight < 0 || pointHeight > height) {
                return null;
            }
            return List.of(new GeoPoint(this, intersectionPoint));
        }

        // Solve the quadratic equation for the cylindrical surface
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null; // No intersections
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        if (t2 <= 0 || alignZero(t1 - maxDistance) > 0) {
            return null;
        }

        List<GeoPoint> intersections = new LinkedList<>();

        // Check the first intersection point
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point p1 = ray.getPoint(t1);
            double h1 = v.dotProduct(p1.subtract(axisP0));
            if (h1 >= 0 && h1 <= height) {
                intersections.add(new GeoPoint(this, p1));
            }
        }

        // Check the second intersection point
        if (alignZero(t2 - maxDistance) <= 0) {
            Point p2 = ray.getPoint(t2);
            double h2 = v.dotProduct(p2.subtract(axisP0));
            if (h2 >= 0 && h2 <= height) {
                intersections.add(new GeoPoint(this, p2));
            }
        }

        // Check for intersections with the bases
        double baseT1 = v.dotProduct(axisP0.subtract(p0)) / v.dotProduct(dir);
        double baseT2 = v.dotProduct(axisP0.add(v.scale(height)).subtract(p0)) / v.dotProduct(dir);

        if (baseT1 > 0 && alignZero(baseT1 - maxDistance) <= 0) {
            Point basePoint1 = ray.getPoint(baseT1);
            if (basePoint1.subtract(axisP0).lengthSquared() <= radius * radius) {
                intersections.add(new GeoPoint(this, basePoint1));
            }
        }

        if (baseT2 > 0 && alignZero(baseT2 - maxDistance) <= 0) {
            Point basePoint2 = ray.getPoint(baseT2);
            if (basePoint2.subtract(axisP0.add(v.scale(height))).lengthSquared() <= radius * radius) {
                intersections.add(new GeoPoint(this, basePoint2));
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }
}
