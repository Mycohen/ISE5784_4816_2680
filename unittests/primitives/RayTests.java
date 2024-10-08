package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Ray
 * This class contains unit tests for the Ray class.
 * It tests the constructors and the getPoint method.
 */
class RayTests {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetHead() {
        //============ Equivalence Partitions Tests ==============
        double unitVector = 1 / Math.sqrt(3);
        // TC01: ray Point towards positive distance
        assertEquals(new Point(1 + (2 * unitVector), 2 + (2 * unitVector), 3 + (2 * unitVector)), new Ray(new Point(1, 2, 3), new Vector(1, 1, 1)).getPoint(2), "Ray getHead() not working correctly towards positive distance");
        // TC02: ray Point towards negative distance
        assertEquals(new Point(1 + (-3 * unitVector), 2 + (-3 * unitVector), 3 + (-3 * unitVector)), new Ray(new Point(1, 2, 3), new Vector(1, 1, 1)).getPoint(-3), "Ray getHead() not working correctly towards negative distance");
        // =============== Boundary Values Tests ==================
        // TC03: ray Point towards zero distance
        assertEquals(new Point(1, 1, 1), new Ray(new Point(1, 1, 1), new Vector(1, 2, 3)).getPoint(0), "Ray getHead() not working correctly towards zero distance");
    }

    /**
     * Test method for {@link primitives.Ray#equals(Object)}.
     */
    @Test
    void testTestEquals() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        assertTrue(ray.equals(new Ray(new Point(1, 2, 3), new Vector(1, 1, 1))), " Ray equals() not working correctly");
        assertFalse(ray.equals(new Ray(new Point(1, 2, 8), new Vector(1, 1, 2))), " Ray equals() not working correctly");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List<Point>)}.
     */
    @Test
    void testFindClosestPoint() {

        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));

        //============ Equivalence Partitions Tests ==============

        // TC01: ray Point at the middle of the list
        assertEquals(new Point(1, 2, 3),
                ray.findClosestPoint(List.of(new Point(2, 3, 4),
                        new Point(1, 2, 3), new Point(3, 4, 5))),
                "findClosestPoint not working correctly at the middle of the list");

        // =============== Boundary Values Tests ==================
        // TC01: null list
        assertNull(ray.findClosestPoint(null),
                "Ray findClosestPoint() not working correctly with null list");
        //TC02 ray Point at the beginning of the list
        assertEquals(new Point(1, 2, 3),
                ray.findClosestPoint(List.of(new Point(1, 2, 3),
                        new Point(2, 3, 4), new Point(3, 4, 5))),
                "findClosestPoint not working correctly at the middle of the list");
        //TC03 ray Point at the end of the list
        assertEquals(new Point(1, 2, 3),
                ray.findClosestPoint(List.of(new Point(3, 4, 5),
                        new Point(2, 3, 4), new Point(1, 2, 3))),
                "findClosestPoint not working correctly at the middle of the list");
    }
}