package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

        @Test
        void testConstructor() {
            // ============ Equivalence Partitions Tests ==============
            // TC01: Correct triangle
            assertDoesNotThrow(() ->
                            new Triangle(new Point(0, 0, 0),
                                    new Point(1, 0, 0),
                                    new Point(0, 1, 0)),
                    "Failed constructing a correct triangle");

            // TC02: Zero area
            assertThrows(IllegalArgumentException.class, ()
                    -> new Triangle(new Point(0, 0, 0),
                            new Point(0, 0, 0),
                            new Point(0, 0, 0)),
                    "Failed constructing a triangle with zero area");

            // TC03: Negative area
            assertThrows(IllegalArgumentException.class,
                    () -> new Triangle(new Point(0, 0, 0),
                            new Point(1, 0, 0),
                            new Point(0, -1, 0)),
                    "Failed constructing a triangle with negative area");
        }

        @Test
        void testGetNormal() {

            // ============ Equivalence Partitions Tests ==============
            // TC01: Simple test
            Triangle t = new Triangle(new Point(0, 0, 0),
                    new Point(1, 0, 0),
                    new Point(0, 1, 0));
            assertEquals(new Vector(0, 0, 1), t.getNormal(new Point(0, 0, 0)), "Bad normal to triangle");
        }



}