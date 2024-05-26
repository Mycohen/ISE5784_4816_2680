package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

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

        // TC02: Point on the positive Y axis
        Point pointY = new Point(0, 1, 0);
        Vector expectedNormalY = new Vector(0, 1, 0).normalize();
        assertEquals(expectedNormalY, sphere.getNormal(pointY),
                "getNormal() wrong result for point on the positive Y axis");

        // TC03: Point on the positive Z axis
        Point pointZ = new Point(0, 0, 1);
        Vector expectedNormalZ = new Vector(0, 0, 1).normalize();
        assertEquals(expectedNormalZ, sphere.getNormal(pointZ),
                "getNormal() wrong result for point on the positive Z axis");



        // TC04: Point on the negative X axis
        Point pointNegX = new Point(-1, 0, 0);
        Vector expectedNormalNegX = new Vector(-1, 0, 0).normalize();
        assertEquals(expectedNormalNegX, sphere.getNormal(pointNegX),
                "getNormal() wrong result for point on the negative X axis");

        // TC05: Point on the negative Y axis
        Point pointNegY = new Point(0, -1, 0);
        Vector expectedNormalNegY = new Vector(0, -1, 0).normalize();
        assertEquals(expectedNormalNegY, sphere.getNormal(pointNegY),
                "getNormal() wrong result for point on the negative Y axis");

        // TC06: Point on the negative Z axis
        Point pointNegZ = new Point(0, 0, -1);
        Vector expectedNormalNegZ = new Vector(0, 0, -1).normalize();
        assertEquals(expectedNormalNegZ, sphere.getNormal(pointNegZ),
                "getNormal() wrong result for point on the negative Z axis");

        // TC07: Point on the sphere surface with arbitrary coordinates
        Point pointArbitrary = new Point(0.57735, 0.57735, 0.57735);
        Vector expectedNormalArbitrary = new Vector(0.57735, 0.57735, 0.57735).normalize();
        assertEquals(expectedNormalArbitrary, sphere.getNormal(pointArbitrary),
                "getNormal() wrong result for point on the sphere surface with arbitrary coordinates");
    }
}
