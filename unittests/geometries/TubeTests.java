package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;


class TubeTests {

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct tube
        assertDoesNotThrow(() ->
                        new Tube(1, new Ray(new Point(0, 0, 0),
                                new Vector(0, 0, 1))),
                "Failed constructing a correct tube");

        // TC02: Zero radius
        assertThrows(IllegalArgumentException.class, ()
                -> new Tube(0, new Ray(new Point(0, 0, 0),
                        new Vector(0, 0, 1))),
                "Failed constructing a tube with zero radius");

        // TC03: Negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(-1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1))),
                "Failed constructing a tube with negative radius");
    }

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal at a point on the side surface of the tube
        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Tube tube = new Tube(1, axis);

        // A point on the side surface of the tube (1,0,1)
        Point p1 = new Point(1, 0, 1);
        Vector expectedNormal1 = new Vector(1, 0, 0);
        assertEquals(expectedNormal1, tube.getNormal(p1), "Bad normal to tube at side surface");

        // ============ Boundary Value Analysis ==============
        // TC02: Test normal at a point directly in front of the head of the axis ray
        Point p2 = new Point(1, 0, 0);
        Vector expectedNormal2 = new Vector(1, 0, 0);
        assertEquals(expectedNormal2, tube.getNormal(p2),
                "Bad normal to tube at front of the head of the axis ray");
        //TC03: Test normal if the point is exactly on the axis of the tube
        Point p3 = new Point(0, 0, 0);
       assertEquals(axis.getDirection(), tube.getNormal(p3),
                "Bad normal to tube at point on the axis");
    }

}
