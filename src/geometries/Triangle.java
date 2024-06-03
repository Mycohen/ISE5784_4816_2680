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
    public List<Point> findIntsersections(Ray ray) {
    Plane plane = new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
    var p = plane.findIntsersections(ray);
    if (p == null) {
        return null;
    }
        Vector AB = vertices.get(0).subtract(vertices.get(1));
        Vector AC = vertices.get(0).subtract(vertices.get(2));
        Vector AP = vertices.get(0).subtract(p.getFirst());

        Double dot00 = AB.dotProduct(AB);
        Double dot01 = AC.dotProduct(AB);
        Double dot11 = AC.dotProduct(AC);
        Double dot20 = AB.dotProduct(AP);
        Double dot21 = AC.dotProduct(AP);

        Double denominator =   dot01 * dot01 -dot00 * dot11;
        if (denominator == 0) {
            return null;
        }
        Double V = (dot01 * dot21-dot11 * dot20 ) / denominator;
        Double W = (dot01 * dot20-dot00 * dot21 ) / denominator;
        Double U = 1 - V - W;

        if ((0 <= U && U <= 1) && (0 <= V && V <= 1) && (0 <= W && W <= 1)) {
            return List.of(p.getFirst());
        }

        return null;
    }

}
