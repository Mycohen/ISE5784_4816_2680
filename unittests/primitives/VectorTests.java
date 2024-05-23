package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Vector class
 * Testing various operations and edge cases for vectors.
 *
 * Authors: Moshe Yaakov Cohen and Eliaou Kopinski
 */
class VectorTests {

    private final double DELTA = 0.000001;

    // ============ Equivalence Partitions Tests ==============

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct vector
        assertDoesNotThrow(() ->
                new Vector(1, 2, 3), "Failed constructing a correct vector");

        // TC01: Correct vector with Double3 parameter
        assertDoesNotThrow(() -> new Double3(1, 2, 3),
                "Failed constructing a correct vector with Double3 parameter");

        // TC02: Zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "Failed constructing a zero vector");
    }


    /**
     * Testing {@link Vector#add(Vector)} method.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Simple addition
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, -5, 6);
        Vector result = v1.add(v2);
        assertEquals(new Vector(5, -3, 9), result, "add() method failed");

        // =============== Boundary Values Tests ==================

        // TC10: Addition resulting in zero vector
        Vector v3 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () ->
                v1.add(v3), "add() method did not throw for zero vector result");
    }

    /**
     * Testing {@link Vector#scale(double)} method.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Scaling by positive scalar
        Vector v = new Vector(1, 2, 3);
        Vector result = v.scale(2);
        assertEquals(new Vector(2, 4, 6), result, "scale() method failed");

        // =============== Boundary Values Tests ==================

        // TC10: Scaling by zero
        assertThrows(IllegalArgumentException.class, () ->
                v.scale(0), "scale() method did not throw for zero scalar");
    }

    /**
     * Testing {@link Vector#lengthSquared()} method.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Vector v = new Vector(1, 2, 3);
        assertEquals(14, v.lengthSquared(), DELTA, "lengthSquared() method failed");
    }

    /**
     * Testing {@link Vector#length()} method.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Vector v = new Vector(1, 2, 2);
        assertEquals(3, v.length(), DELTA, "length() method failed");
    }

    /**
     * Testing {@link Vector#normalize()} method.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Vector v = new Vector(0, 3, 4);
        Vector result = v.normalize();
        assertEquals(1, result.length(), DELTA, "normalize() method failed");
    }

    /**
     * Testing {@link Vector#crossProduct(Vector)} method.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector result = v1.crossProduct(v2);
        assertEquals(new Vector(-3, 6, -3), result, "crossProduct() method failed");

        // =============== Boundary Values Tests ==================

        // TC10: Parallel vectors
        Vector v3 = new Vector(2, 4, 6);
        assertThrows(IllegalArgumentException.class, () ->
                v1.crossProduct(v3), "crossProduct() method did not throw for parallel vectors");
    }

    /**
     * Testing {@link Vector#dotProduct(Vector)} method.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: General case
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, -5, 6);
        assertEquals(12, v1.dotProduct(v2), DELTA, "dotProduct() method failed");

        // =============== Boundary Values Tests ==================
        // TC02: Orthogonal vectors
        Vector v3 = new Vector(1, 0, 0);
        Vector v4 = new Vector(0, 1, 0);
        assertEquals(0, v3.dotProduct(v4), DELTA,
               "dotProduct() method failed for orthogonal vectors");
    }

    /**
     * Testing {@link Vector#equals(Object)} method.
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Equal vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);
        assertTrue(v1.equals(v2), "equals() method failed for equal vectors");

        // TC02: Different vectors
        Vector v3 = new Vector(1, 2, 4);
        assertFalse(v1.equals(v3), "equals() method failed for different vectors");
    }


}
