package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
//גליל סופי
class CylinderTests {
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct cylinder
        assertDoesNotThrow(() -> new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1),
                "Failed constructing a correct cylinder");

        // TC02: Zero radius
        assertThrows(IllegalArgumentException.class, ()
                -> new Cylinder(0, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1),
                "Failed constructing a cylinder with zero radius");

        // TC03: Negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(-1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1),
                "Failed constructing a cylinder with negative radius");

        // TC04: Zero height
        assertThrows(IllegalArgumentException.class, ()
                -> new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 0),
                "Failed constructing a cylinder with zero height");

        // TC05: Negative height
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), -1),
                "Failed constructing a cylinder with negative height");
    }
    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(1, new Ray(
                new Point(0, 0, 0), new Vector(0, 0, 1)), 5);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Point on the curved surface
        Point pointOnSurface = new Point(1, 0, 2);
        Vector expectedNormalSurface = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormalSurface, cylinder.getNormal(pointOnSurface),
                "getNormal() wrong result for point on the curved surface");

        // TC02: Point on the bottom base
        Point pointOnBottomBase = new Point(0.5, 0.5, 0);
        Vector expectedNormalBottom = new Vector(0, 0, -1).normalize();
        assertEquals(expectedNormalBottom, cylinder.getNormal(pointOnBottomBase),
                "getNormal() wrong result for point on the bottom base");

        // TC03: Point on the top base
        Point pointOnTopBase = new Point(0.5, 0.5, 5);
        Vector expectedNormalTop = new Vector(0, 0, 1).normalize();
        assertEquals(expectedNormalTop, cylinder.getNormal(pointOnTopBase),
                "getNormal() wrong result for point on the top base");

        // =============== Boundary Values Tests ==================



        // TC05: Point at the center of the top base
        Point centerTopBase = new Point(0, 0, 5);
        assertEquals(expectedNormalTop, cylinder.getNormal(centerTopBase),
                "getNormal() wrong result for point at the center of the top base");
    }

}