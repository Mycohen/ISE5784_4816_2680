package geometries;

import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class.
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Vector)} and
     * {@link geometries.Plane#Plane(Point, Point, Point)} constructors.
     * This method tests the creation of a Plane object using both constructors.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 0),
                        new Vector(1, 0, 0)),
                "Failed constructing a correct plane");

        // TC02: Building a plane by three non-collinear points
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1),
                        new Point(1, 0, 0), new Point(0, 1, 0)),
                "Failed constructing a correct plane with three non-collinear points");

        // =============== Boundary Values Tests ==================

        // TC03: Building a plane by three collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 2, 0),
                        new Point(0, 1, 0), new Point(0, 0, 0)),
                "Constructed a plane with three collinear points");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)} and
     * {@link geometries.Plane#getNormal()}.
     * This method tests the getNormal functionality of the Plane class.
     */
    @Test
    void testGetNormal() {

        // TC01: Simple test for getNormal with a specific point
        Plane p = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Vector(1, 0, 0),
                p.getNormal(new Point(2, 0, 0)),
                "Bad normal to plane");

        // TC02: Test normal for plane defined by three points
        Plane p2 = new Plane(new Point(0, 0, 0),
                new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(new Vector(0, 0, 1), p2.getNormal(),
                "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     * This method tests the findIntersections functionality of the Plane class with various cases.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(
                new Point(2, 1, 0),
                new Point(0, -2, 1),
                new Point(0, 0, 2));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is parallel to the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(5, 0, 0),
                        new Vector(0, 0, 10))),
                "Ray's line is parallel to the plane");

        // TC02: Ray's line intersects the plane (1 point)
        assertEquals(List.of(
                        new Point(0, 0, 2)),
                plane.findIntersections(new Ray(new Point(3, 0, 0),
                        new Vector(-3, 0, 2))),
                "Ray's line intersects the plane");

        // =============== Boundary Values Tests ==================

        // TC03: Ray is parallel to the plane and included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2, 1, 0),
                        new Vector(-2, -1, 2))),
                "Ray is on the plane");

        // TC04: Ray is parallel to the plane and not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 2, 0),
                        new Vector(-4, -2, 4))),
                "Ray is parallel to the plane and not included in the plane");

        // TC05: Ray is neither orthogonal nor parallel to the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0.9236262812122, -0.7195234248737, 2.0),
                        new Vector(-4, -2, 4))),
                "Ray is neither orthogonal nor parallel to the plane");

        Plane newPlane = new Plane(
                new Point(1, 1, 1),
                new Point(1, 1, 0),
                new Point(1, 0, 0));

        // TC06: Ray's line is orthogonal to the plane and starts before the plane (1 point)
        var result = newPlane.findIntersections(new Ray(new Point(2, 1, 1), new Vector(-3, 0, 0)));
        assertEquals(1, result.size(),
                "Ray's line is orthogonal to the plane and starts before the plane");

        // TC07: Ray's line is orthogonal to the plane and starts on the plane (0 points)
        assertNull(newPlane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-3, 0, 0))),
                "Ray's line is orthogonal to the plane and starts on the plane");

        // TC08: Ray's line is orthogonal to the plane and starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(-3, 0, 0))),
                "Ray's line is orthogonal to the plane and starts after the plane");

        // TC09: Ray's line is neither orthogonal nor parallel and begins at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-3, 1, 0))),
                "Ray's line is neither orthogonal nor parallel and begins at the plane");

        // TC10: Ray's line is neither orthogonal nor parallel and begins at reference point in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-3, 1, 0))),
                "Ray's line is neither orthogonal nor parallel and begins at the reference point in the plane");
    }
}
