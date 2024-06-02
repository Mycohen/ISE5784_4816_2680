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
        Ray rayOut = new Ray(new Point(0, 0, 3), new Vector(3, 0, -3));
        assertNull(sphere.findIntsersections(rayOut), "Ray's line out of sphere");

        // TC02: Only the ray projection intersects the sphere (0 points)
        Ray rayTanOut = new Ray(new Point(3, 0, 1), new Vector(2, 0, 0));
        assertNull(sphere.findIntsersections(rayTanOut),
                "Only the ray projection intersects the sphere");

        // TC03: Ray is tangent to the sphere, starts on the sphere (0 points)
        Ray rayTanOn = new Ray(new Point(0, 0, 2), new Vector(0, -2, 0));
        assertNull(sphere.findIntsersections(rayTanOn), "Ray is tangent to the sphere, starts on the sphere");

        // TC04: Ray starts inside the sphere (1 point)
        Ray rayIn = new Ray(new Point(0.5, 0.5, 0.5), new Vector(1, 0, 0));
        var resIn = sphere.findIntsersections(rayIn);
        assertEquals(1, resIn.size(), "Method did not find 1 intersection point");

        // TC05: Ray starts before and crosses the sphere (2 points)
        var resCross = sphere.findIntsersections(new Ray(p100, new Vector(-1.665278089022151, -0.12235841760011, 1.736500836326363)));
        assertEquals(2, resCross.size(), "Method did not find 2 intersection points");
        final Point gp1 = new Point(0.713059104111085, -0.021083345897106, 0.299213151828546);
        final Point gp2 = new Point(-0.665278089022151, -0.12235841760011, 1.736500836326363);
        final var expPoints = List.of(gp1, gp2);
        assertEquals(expPoints, resCross, "Method did not find 2 intersection points");



        // =============== Boundary Values Tests ==================

        // TC07: Ray starts in the sphere and goes outside (1 point)
        Ray rayInOut = new Ray(p001, new Vector(1, 0, 0));
        var resInOut = sphere.findIntsersections(rayInOut);
        assertEquals(1, resInOut.size(), "Method did not find 1 intersection point");

        // TC08: Ray starts on the sphere and doesn't go through the center (1 point)
        Ray rayOnNotCenter = new Ray(new Point(0.8625257736259, 0.06725163418501, 1.501524184392), new Vector(-1.647933084508, -0.4073885196039, 0.01562401022017));
        var resOnNotCenter = sphere.findIntsersections(rayOnNotCenter);
        assertEquals(1, resOnNotCenter.size(), "Method did not find 1 intersection point when Ray starts on the sphere and goes through the center");

        // TC09: Ray starts on the sphere and doesn't go through the center in reverse (0 points)
        Ray rayOnNotCenterRev = new Ray(new Point(-0.7854073108818, -0.3401368854189, 1.517148194613), new Vector(-1.647933084508, -0.4073885196039, 0.01562401022017));
        var resOnNotCenterRev = sphere.findIntsersections(rayOnNotCenterRev);
        assertNull(resOnNotCenterRev, "Method did not find 0 intersection point Ray starts on the sphere and goes through the center in reverse");

        // TC10: Ray is tangent to the sphere, starts after the sphere (0 points)
        Ray rayTanAfter = new Ray(new Point(0, 1, 2), new Vector(0, 1, 0));
        assertNull(sphere.findIntsersections(rayTanAfter), "Method did not find 0 intersection points Ray is tangent to the sphere, starts after the sphere");

        // TC11: Ray is right angle to the center (0 points)
        Ray rayRightAngle = new Ray(new Point(0, 0, 3), new Vector(0, 1, 0));
        assertNull(sphere.findIntsersections(rayRightAngle), "Method did not find 0 intersection points Ray is right angle to the center");

        // TC12: The Ray projection intersects the center and the ray intersects the sphere (1 point)
        Ray rayProjCenter = new Ray(new Point(0.5, 0, 1), new Vector(0.5, 0, 0));
        var resProjCenter = sphere.findIntsersections(rayProjCenter);
        assertEquals(1, resProjCenter.size(), "Method did not find 1 intersection point when the Ray projection intersects the center and the ray intersects the sphere");

        // TC13: The Ray starts outside the sphere, crosses the center and intersects the sphere (2 points)
        Ray rayOutCrossCenter = new Ray(new Point(2, 0, 1), new Vector(-2, 0, 0));
        var resOutCrossCenter = sphere.findIntsersections(rayOutCrossCenter);
        assertEquals(2, resOutCrossCenter.size(), "Method did not find 2 intersection points when the Ray starts outside the sphere, crosses the center and intersects the sphere");

        // TC14: The Ray starts on the sphere and goes through the center (1 point)
        Ray rayOnCenter = new Ray(new Point(1, 0, 1), new Vector(-2, 0, 0));
        var resOnCenter = sphere.findIntsersections(rayOnCenter);
        assertEquals(1, resOnCenter.size(), "Method did not find 1 intersection point when the Ray starts on the sphere and goes through the center");

        // TC15: The Ray projection intersects the center and the ray does not intersect the sphere (0 points)
        Ray rayProjNoIntersect = new Ray(new Point(-1.5, 0, 1), new Vector(-1.5, 0, 0));
        assertNull(sphere.findIntsersections(rayProjNoIntersect), "Method did not find 0 intersection points when the Ray projection intersects the center and the ray does not intersect the sphere");

        // TC16: The Ray starts on the sphere and the projection intersects the sphere through its center (0 points)
        Ray rayOnProjCenter = new Ray(new Point(1, 0, 1), new Vector(0, 0, 1));
        assertNull(sphere.findIntsersections(rayOnProjCenter), "Method did not find 0 intersection points when the Ray starts on the sphere and the projection intersects the sphere through its center");
    }



}
