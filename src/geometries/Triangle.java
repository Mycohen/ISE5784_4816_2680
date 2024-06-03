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
    @Override
    public List<Point> findIntersections(Ray ray) {

        Plane plane = new Plane(vertices.get(0), vertices.get(1), vertices.get(2));
        Vector V1 = vertices.get(0).subtract(ray.getHead());
        Vector V2 = vertices.get(1).subtract(ray.getHead());
        Vector V3 = vertices.get(2).subtract(ray.getHead());
        Vector N1 = V1.crossProduct(V2).normalize();
        Vector N2 = V2.crossProduct(V3).normalize();
        Vector N3 = V3.crossProduct(V1).normalize();

        if (alignZero(ray.getDirection().dotProduct(N1)) > 0
                && alignZero(ray.getDirection().dotProduct(N2)) > 0
                && alignZero(ray.getDirection().dotProduct(N3)) > 0)
            return plane.findIntersections(ray);
        if (alignZero(ray.getDirection().dotProduct(N1)) < 0
                && alignZero(ray.getDirection().dotProduct(N2)) < 0
                && alignZero(ray.getDirection().dotProduct(N3)) < 0)
            return plane.findIntersections(ray);

        return null;
    }
}
