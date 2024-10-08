package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The {@code Triangle} class represents a triangle in 3D space.
 * It extends the {@code Polygon} class and is defined by three points.
 * <p>
 * A triangle is a simple polygon with three edges and three vertices. In 3D space,
 * it is typically used to represent a flat surface.
 * </p>
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 */
public class Triangle extends Polygon {

    /**
     * Constructs a {@code Triangle} with the specified points.
     *
     * @param p1 the first point of the triangle
     * @param p2 the second point of the triangle
     * @param p3 the third point of the triangle
     * @throws IllegalArgumentException if the points do not form a valid triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
        // Additional validation can be added here if necessary
    }

    /**
     * Finds the intersection points of a given ray with the triangle.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        if (boundingBox != null && !boundingBox.hasIntersection(ray)) {
            return null;  // No intersection with the bounding box, so no need to check further
        }
        Plane plane = new Plane(vertices.get(0), vertices.get(1), vertices.get(2));
        List<GeoPoint> planeIntersections = plane.findGeoIntersectionsHelper(ray, maxDistance);

        if (planeIntersections == null) {
            return null;
        }


        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double sign1 = alignZero(ray.getDirection().dotProduct(n1));
        double sign2 = alignZero(ray.getDirection().dotProduct(n2));
        double sign3 = alignZero(ray.getDirection().dotProduct(n3));


        if ((sign1 > 0 && sign2 > 0 && sign3 > 0) || (sign1 < 0 && sign2 < 0 && sign3 < 0)) {
            return List.of(new GeoPoint(this, planeIntersections.get(0).point));
            //return planeIntersections;
        }

        return null;
    }
}
