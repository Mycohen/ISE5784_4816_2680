package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class SphereTests {
    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct sphere
        assertDoesNotThrow(() -> new Sphere(1, new Point(0, 0, 0)),
                "Failed constructing a correct sphere");

        // TC02: Zero radius
        assertThrows(IllegalArgumentException.class, ()
                        -> new Sphere(0, new Point(0, 0, 0)),
                "Failed constructing a sphere with zero radius");

        // TC03: Negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(-1, new Point(0, 0, 0)),
                "Failed constructing a sphere with negative radius");
    }

    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(1, new Point(0, 0, 0));


        // TC01: Point on the positive X axis
        Point pointX = new Point(1, 0, 0);
        Vector expectedNormalX = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormalX, sphere.getNormal(pointX),
                "getNormal() wrong result for point on the positive X axis");



    }
    public static void printPointsList(List<Point> points) {
        for (Point point : points) {
            System.out.println(point);
        }

    }
    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        Sphere sphere = new Sphere(1, p001);
        Ray rayOutside = new Ray(new Point(0,0,3), new Vector(3,0,-3));
        assertNull(sphere.findIntsersections(rayOutside), "Ray's line out of sphere");

        // TC02: Ray is tangent to the sphere, starts outside the sphere (0 points)
        Ray rayTangentOutside = new Ray(new Point(2,2,0), new Vector(0, -2, 0));
        assertNull(sphere.findIntsersections(rayTangentOutside),
                "Ray is tangent to the sphere, starts outside");

        // TC03: Ray is tangent to the sphere, starts on the sphere (0 points)
        Ray rayTangentOnSphere = new Ray(new Point(0,0,2), new Vector(0, -2, 0));
        assertNull(sphere.findIntsersections(rayTangentOnSphere),
                "Ray is tangent to the sphere, starts on the sphere");

        // TC04: Ray starts inside the sphere (1 point)
        Ray rayInside = new Ray(new Point(0.5,0.5,0.5), new Vector(1,0,0));
        var resultInside = sphere.findIntsersections(rayInside);
        assertEquals(1, resultInside.size(),
                "Method did not find 1 intersection point");

        // TC05: The projection of the ray intersects the sphere (0 points)
        Ray rayProjectionIntersects = new Ray(new Point(0.529579069940077, 0.821985028887127, 0), new Vector(2.491809896699456, -0.070206086613291, 0));
        assertNull(sphere.findIntsersections(rayProjectionIntersects),
                "Method did not find 0 intersection points where the projection of the ray intersects the sphere");

        // TC06: Ray starts before and crosses the sphere (2 points)
        var resultCrosses = sphere.findIntsersections(new Ray(p100, new Vector(-1.665278089022151, -0.12235841760011, 1.736500836326363)));
        assertEquals(2, resultCrosses.size(),
                "Method did not find 2 intersection points");
        final Point gp1 = new Point(0.713059104111085, -0.021083345897106, 0.299213151828546);
        final Point gp2 = new Point(-0.665278089022151, -0.12235841760011, 1.736500836326363);
        final var expectedPoints = List.of(gp1, gp2);
        assertEquals(expectedPoints, resultCrosses,
                "Method did not find 2 intersection points");

        // =============== Boundary Values Tests ==================

        // TC07: Ray starts in the sphere and goes outside (1 point)
        Ray rayStartsInsideGoesOutside = new Ray(p001, new Vector(1,0,0));
        var resultStartsInsideGoesOutside = sphere.findIntsersections(rayStartsInsideGoesOutside);
        assertEquals(1, resultStartsInsideGoesOutside.size(),
                "Method did not find 1 intersection point");
    }




}
