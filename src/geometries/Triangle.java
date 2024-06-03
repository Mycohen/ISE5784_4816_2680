package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Vector V1 = vertices.get(1).subtract(vertices.get(0));
        Vector V2 = vertices.get(2).subtract(vertices.get(0));
        Vector V3 = vertices.get(3).subtract(vertices.get(0));
    }

}
