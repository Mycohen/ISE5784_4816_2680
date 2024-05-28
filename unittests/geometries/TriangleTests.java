package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

        @Test
        void testConstructor() {

            // TC01: Correct triangle
            assertDoesNotThrow(() ->
                            new Triangle(new Point(0, 0, 0),
                                    new Point(1, 0, 0),
                                    new Point(0, 1, 0)),
                    "Failed constructing a correct triangle");

            // TC02: Another correct example
            assertDoesNotThrow(() ->
                            new Triangle(new Point(0, 0, 5),
                                    new Point(0, 5, 0),
                                    new Point(5, 0, 0)),
                    "Failed constructing a correct triangle");
            // TC03: Three points on the same line
            assertThrows(IllegalArgumentException.class, ()
                    -> new Triangle(new Point(0, 0, 0),
                    new Point(1, 0, 0),
                    new Point(2, 0, 0)),
                    "Constructed a triangle with three points on the same line");
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