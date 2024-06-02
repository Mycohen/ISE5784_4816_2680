package geometries;

import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {


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

        // TC03: Building a plane by three collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 2, 0),
                        new Point(0, 1, 0), new Point(0, 0, 0)),
                "Constructed a plane with three collinear points");
    }

    @Test
    void testGetNormal() {

        // TC01: There is a simple single test here
        Plane p = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Vector(1, 0, 0),
                p.getNormal(new Point(2, 0, 0)),
                "Bad normal to plane");

        //TC02: Test normal by giving three points
        Plane p2 = new Plane(new Point(0, 0, 0),
                new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(new Vector(0, 0, 1),p2.getNormal(),
                "Bad normal to plane");


    }
@Test
    void testFindIntersections() {
        Plane plane = new Plane(
                new Point(2, 1, 0),
                new Point(0,-2,1),
                new Point (0,0,2));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is parallel to the plane (0 points)
         assertNull(plane.findIntsersections(new Ray(new Point(5, 0, 0),
                    new Vector(0, 0, 10))),
            "Ray's line is parallel to the plane");


        // TC02: Ray's line intersects the plane  (1 point)
        assertEquals(List.of(
                new Point(0, 0, 2)),
                plane.findIntsersections(new Ray(new Point(3, 0, 0),
                        new Vector(-3, 0, 2))),
                "Ray's line is orthogonal to the plane");



        // =============== Boundary Values Tests ==================

        // TC03: Ray is parallel to the plane and include the plane (0 points)
         assertNull(plane.findIntsersections(new Ray(new Point(2, 1, 0),
                    new Vector(-2, -1, 2))),
            "Ray is on the plane");


        // TC04:Ray is parallel to the plane and not include the plane (0 points)
        assertNull(plane.findIntsersections(new Ray(new Point(4, 2, 0),
                    new Vector(-4, -2, 4))),
            "Ray is parallel to the plane and not include the plane");



        //til here for today

        // TC05: Ray's line is orthogonal to the plane and starts after the plane (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(0, 1, 0), new Vector(0, 0, 1))),
                "Ray's line is orthogonal to the plane and starts after the plane");

        // TC06: Ray's line is neither orthogonal nor parallel to and begins at the plane (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1))),
                "Ray's line is neither orthogonal nor parallel to and begins at the plane");



}
}