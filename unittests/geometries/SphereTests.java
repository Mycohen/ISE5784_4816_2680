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

        // TC08: Ray starts on the sphere and  doesn't go through the center (1 point)
        Ray rayStartsOnSphereDoesntGoThroughCenter = new Ray(new Point(0.8625257736259, 0.06725163418501, 1.501524184392)  , new Vector(-1.647933084508,-0.4073885196039,0.01562401022017));
        var resultStartsOnSphereDoesntGoThroughCenter = sphere.findIntsersections(rayStartsOnSphereDoesntGoThroughCenter);
        assertEquals(1,resultStartsOnSphereDoesntGoThroughCenter.size(),
                "Method did not find 1 intersection point Ray starts on the sphere and goes through the center");

        // TC09: Ray starts on the sphere and  doesn't go through the center in reverse (0 point)
        Ray rayStartsOnSphereDoesntGoThroughCenterInReverse = new Ray(new Point(-0.7854073108818,-0.3401368854189,1.517148194613) , new Vector(-1.647933084508,-0.4073885196039,0.01562401022017));
        var resultStartsOnSphereDoesntGoThroughCenterInReverse = sphere.findIntsersections(rayStartsOnSphereDoesntGoThroughCenterInReverse);
        assertNull(resultStartsOnSphereDoesntGoThroughCenterInReverse,
                "Method did not find 0 intersection point Ray starts on the sphere and goes through the center in reverse");

        // TC010: Ray is tangent to the sphere, starts after sphere (0 points)
        Ray rayTangentOutsideStartsAfterTheSphere = new Ray(new Point(0, 1, 2) , new Vector(0,1,0));
        assertNull(sphere.findIntsersections(rayTangentOutsideStartsAfterTheSphere),
                "Method did not find 0 intersection points Ray is tangent to the sphere, starts after the sphere");
        // right angle to the center
        Ray rightAngleToTheCenter = new Ray(new Point(0,0,3) , new Vector(0,1,0));
        assertNull(sphere.findIntsersections(rightAngleToTheCenter),
                "Method did not find 0 intersection points Ray is right angle to the center");

        // TC11: The Ray projection intersects the center and the ray intersects the sphere (1 point)
        Ray ray11 = new Ray(new Point(0.5,0,1) , new Vector(0.5,0,0));
        var result11 = sphere.findIntsersections(ray11);
        assertEquals(1, result11.size(),
                "Method did not find 1 intersection point when the Ray projection intersects" +
                        " the center and the ray intersects the sphere");

        // TC12: The Ray starts outside the sphere, crosses the center and intersects the sphere (2 points)
        Ray ray12 = new Ray(new Point(2,0,1) , new Vector(-2,0,0));
        var result12 = sphere.findIntsersections(ray12);
        assertEquals(2, result12.size(),
                "Method did not find 2 intersection points when the Ray starts outside the sphere," +
                        " crosses the center and intersects the sphere");

        // TC13: The Ray starts on the sphere and goes through the center (1 point)
        Ray ray13 = new Ray(new Point(1,0,1) , new Vector(-2,0,0));
        var result13 = sphere.findIntsersections(ray13);
        assertEquals(1, result13.size(),
                "Method did not find 1 intersection " +
                        "point when the Ray starts on the sphere and goes through the center");

        //TC14: The Ray projection intersects the center and the ray does not intersect the sphere (0 points)
        Ray ray14 = new Ray(new Point(-1.5,0,1) , new Vector(-1.5,0,0));
        assertNull(sphere.findIntsersections(ray14),
                "Method did not find 0 intersection points when the Ray projection intersects the center" +
                        " and the ray does not intersect the sphere");

        // TC15: The Ray starts on the sphere and the projection intersect the sphere throw its center (0 points)
        Ray ray15 = new Ray(new Point(1,0,1) , new Vector(0,0,1));
        assertNull(sphere.findIntsersections(ray15),
                "Method did not find 0 intersection points when the Ray starts on the sphere" +
                        " and the projection intersect the sphere throw its center");
    }




}
