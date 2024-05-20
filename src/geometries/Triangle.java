package geometries;
import primitives.Point;

/**
 * The {@code Triangle} class represents a triangle in 3D space.
 * It extends the {@code Polygon} class and is defined by three points.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a {@code Triangle} with the specified points.
     *
     * @param p1 the first point of the triangle
     * @param p2 the second point of the triangle
     * @param p3 the third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

}
