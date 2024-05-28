package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {


    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct plane
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 0),
                        new Vector(1, 0, 0)),
                "Failed constructing a correct plane");

        // TC02: Building a plane by three non-collinear points
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1),
                        new Point(1, 0, 0), new Point(0, 1, 0)),
                "Failed constructing a correct plane with three non-collinear points");

        // TC03: Building a plane by three collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 2, 0),
                        new Point(0, 1, 0), new Point(0, 0, 0)),
                "Constructed a plane with three collinear points");
    }

    @Test
    void testGetNormal() {

        // TC01: There is a simple single test here
        Plane p = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Vector(1, 0, 0),
                p.getNormal(new Point(2, 0, 0)),
                "Bad normal to plane");

        //TC02: Test normal by giving three points
        Plane p2 = new Plane(new Point(0, 0, 0),
                new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(new Vector(0, 0, 1),p2.getNormal(),
                "Bad normal to plane");


    }


}