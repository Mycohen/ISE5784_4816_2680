package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class.
 */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#Triangle(Point, Point, Point)} constructor.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct triangle
        assertDoesNotThrow(() ->
                        new Triangle(new Point(0, 0, 0),
                                new Point(1, 0, 0),
                                new Point(0, 1, 0)),
                "Failed constructing a correct triangle");

        // TC02: Another correct example
        assertDoesNotThrow(() ->
                        new Triangle(new Point(0, 0, 5),
                                new Point(0, 5, 0),
                                new Point(5, 0, 0)),
                "Failed constructing a correct triangle");

        // =============== Boundary Values Tests ==================

        // TC03: Three points on the same line
        assertThrows(IllegalArgumentException.class, () ->
                        new Triangle(new Point(0, 0, 0),
                                new Point(1, 0, 0),
                                new Point(2, 0, 0)),
                "Constructed a triangle with three points on the same line");
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Simple test
        Triangle t = new Triangle(new Point(0, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 1, 0));
        assertEquals(new Vector(0, 0, 1), t.getNormal(new Point(0, 0, 0)),
                "Bad normal to triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(1, 2, 0),
                new Point(1, 1, 2));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside the triangle (1 point)
        assertEquals(List.of(new Point(1, 1, 1)),
                triangle.findIntersections(new Ray(new Point(-1, 1, 1),
                        new Vector(3, 0, 0))),
                "Ray's line is inside the triangle");

        // TC02: Ray's line is outside the triangle's vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 1, 1),
                        new Vector(3, -1, 0))),
                "Ray's line is outside the triangle's vertex");

        // TC03: Ray's line is outside the triangle's edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 1, 1),
                        new Vector(2, 0, 2))),
                "Ray's line is outside the triangle's edge");

        // =============== Boundary Values Tests ==================
        // TC04: Ray's line is on the triangle's edge  (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, 0),
                        new Vector(2, 1, 2))),
                "Ray's line is on the triangle's edge");

        // TC05: Ray's line is on the triangle's Vertex  (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, 0),
                        new Vector(2, 0.5, 1))),
                "Ray's line is on the triangle's vertex");

        // TC06: Ray's line is on the vertex continuation   (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, 0),
                        new Vector(2,0.512374251079869,2.975251497840261))),
                "Ray's line is on the vertex continuation");

        // TC07: Standard case (1 point)
        assertEquals(List.of(new Point(1, 1, 0.814387568779128)),
                triangle.findIntersections(new Ray(new Point(-1, 0, 0),
                        new Vector (2, 1, 0.814387568779128))),
                "Ray's line intersects the triangle");

    }
}
