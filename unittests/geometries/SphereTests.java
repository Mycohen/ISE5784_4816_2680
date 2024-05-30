package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
    @Test
    void testFindIntersections()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray's line is outside the sphere (0 points)
        Sphere sphere = new Sphere(1, p001);
        Ray ray = new Ray(new Point(0,0,3),new Vector(3,0,-3) );
        assertNull(sphere.findIntsersections(ray), "Ray's line out of sphere");
        //TC02: Ray starts before and crosses the sphere (2 points)





    }



}
