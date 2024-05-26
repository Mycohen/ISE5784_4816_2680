package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    @Test
    void testGetNormal() {

        // TC01: There is a simple single test here
        Plane p = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Vector(1, 0, 0), p.getNormal(new Point(2, 0, 0)), "Bad normal to plane");

    }


}