package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Point class
 * Testing various operations and edge cases for points.
 *
 * Authors: Moshe Yaakov Cohen and Eliaou Kopinski
 */
class PointTests {

    private final double DELTA = 0.000001;

    // ============ Equivalence Partitions Tests ==============
/**
     * Test method for {@link primitives.Point#Point(double, double, double)}.
     * This method tests the creation of a Point object using the constructor.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct point
        assertDoesNotThrow(() ->
                new Point(1, 2, 3), "Failed constructing a correct point");

        // TC02: Correct point with Double3 parameter
        assertDoesNotThrow(() -> new Point(new Double3(1, 2, 3)),
                "Failed constructing a correct point with Double3 parameter");
    }

    /**
     * Testing {@link Point#add(Vector)} method.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Simple addition
        Point p1 = new Point(1, 2, 3);
        Vector v = new Vector(2, 4, 6);
        Point result = p1.add(v);
        assertEquals(new Point(3, 6, 9), result, "add() method failed");

        // No boundary tests for add since no edge cases like zero vector addition
    }

    /**
     * Testing {@link Point#subtract(Point)} method.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Simple subtraction
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Vector result = p1.subtract(p2);
        assertEquals(new Vector(-1, -2, -3), result, "subtract() method failed");

        // TC02: Subtraction resulting in zero vector
        Point p3 = new Point(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () ->
                p1.subtract(p3), "subtract() method did not throw for zero vector result");
    }


    /**
     * Testing {@link Point#distance(Point)} method.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 6, 8);
        double result = p1.distance(p2);
        assertEquals(7.071067, result, DELTA, "distance() method failed");
    }

    /**
     * Testing {@link Point#distanceSquared(Point)} method.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 6, 8);
        double result = p1.distanceSquared(p2);
        assertEquals(50, result, DELTA, "distanceSquared() method failed");
    }
}
