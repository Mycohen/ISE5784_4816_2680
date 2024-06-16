package primitives;

import org.junit.jupiter.api.Test;
import primitives.*;


import static org.junit.jupiter.api.Assertions.*;
class RayTests {

    @Test
    void testGetHead() {
        //============ Equivalence Partitions Tests ==============
        double unitVector= 1/Math.sqrt(3);
        // TC01: ray Point towards positive distance
        assertEquals(new Point(1+(2*unitVector), 2+(2*unitVector), 3+(2*unitVector)),new Ray(new Point(1,2,3), new Vector(1,1 , 1)).getPoint(2), "Ray getHead() not working correctly towards positive distance");
        // TC02: ray Point towards negative distance
        assertEquals(new Point(1+(-3*unitVector), 2+(-3*unitVector), 3+(-3*unitVector)),new Ray(new Point(1, 2, 3), new Vector(1, 1, 1)).getPoint(-3), "Ray getHead() not working correctly towards negative distance");
        // =============== Boundary Values Tests ==================
        // TC03: ray Point towards zero distance
        assertEquals(new Point(1, 1, 1),new Ray(new Point(1, 1, 1), new Vector(1, 2, 3)).getPoint(0), "Ray getHead() not working correctly towards zero distance");
    }

    @Test
    void testTestEquals() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        assertTrue(ray.equals(new Ray(new Point(1, 2, 3), new Vector(1, 1, 1)))," Ray equals() not working correctly");
        assertFalse(ray.equals(new Ray(new Point(1, 2, 8), new Vector(1, 1, 2)))," Ray equals() not working correctly");
    }
}