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
    void testFindIntersections()
    {

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray's line is outside the sphere (0 points)
        Sphere sphere = new Sphere(1, p001);
        Ray ray = new Ray(new Point(0,0,3),new Vector(3,0,-3) );
        assertNull(sphere.findIntsersections(ray), "Ray's line out of sphere");

        //TC02: Ray is tangent to the sphere, starts outside the sphere (0 point)
        Ray ray3 = new Ray(new Point(2,2,0),new Vector (0, -2, 0) );
        assertNull(sphere.findIntsersections(ray3),
                "Ray is tangent to the sphere, starts outside");
        //TC03: Ray is tangent to the sphere, starts on the sphere (0 point)
        Ray ray4 = new Ray(new Point(0,0,2),new Vector (0, -2, 0) );
        assertNull(sphere.findIntsersections(ray3),
                "Ray is tangent to the sphere, starts outside");


        //TC04: Ray starts before and crosses the sphere (2 points)
        var result2 = sphere.findIntsersections(new Ray(p100,
                new Vector(-1.665278089022151,-0.12235841760011,1.736500836326363) ));
        assertEquals(2, result2.size(), "Method did not find 2 intersection points");
        final Point gp1 = new Point(0.713059104111085,-0.021083345897106,0.299213151828546);
        final Point gp2 = new Point(-0.665278089022151,-0.12235841760011,1.736500836326363);
        final var expec = List.of(gp1, gp2);
        assertEquals(expec, result2, "Method did not find 2 intersection points");





        // =============== Boundary Values Tests ==================
        //TC05: Ray starts in the sphere and goes outside (1 point)
        Ray ray5 = new Ray(p001,new Vector (1,0,0) );
        var result5 = sphere.findIntsersections(ray5);
        assertEquals(1, result5.size(),
                "Method did not find 1 intersection point");

    }



}
