package renderer;

import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {
    final int nX = 3;
    final int nY = 3;

    @Test
    void integrationTestWithSphere() {
         Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirectionVectors(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);

        // ============ Equivalence Partitions Tests ==============
        // EP01: best case, two intersections point
        Camera camera1 = cameraBuilder.setVpSize(nX, nY).build();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2,
                testRayIntersections(camera1, sphere, 2),
                "Bad number of intersection points");

        // EP02: 18 intersection points
        cameraBuilder= Camera.getBuilder()
                .setLocation(new Point(0,0,0.5))
                .setDirectionVectors(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);
        camera1 = cameraBuilder.setVpSize(nX, nY).build();
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18,
                testRayIntersections(camera1, sphere, 18),
                "didn't find all 18 intersection points");
        // EP03: 10 intersection points
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10,
                testRayIntersections(camera1, sphere, 10),
                "didn't find all 10 intersection points");
        //EP04: 9 intersection points
        sphere = new Sphere(4, new Point(0, 0, -2));
        assertEquals(9,
                testRayIntersections(camera1, sphere, 9),
                "didn't find all 9 intersection points");
        //EP05: 0 intersection points
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0,
                testRayIntersections(camera1, sphere, 0),
                "didn't find all 0 intersection points");

    }
    @Test
    void integrationTestWithPlane() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirectionVectors(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);

        // ============ Equivalence Partitions Tests ==============
        // EP01: 9 intersection points
        Camera camera1 = cameraBuilder.setVpSize(nX, nY).build();
        Plane plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(9,
                testRayIntersections(camera1, plane, 9),
                "didn't find all 9 intersection points");
        // EP02: 9 intersection points
        plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(9,
                testRayIntersections(camera1, plane, 9),
                "didn't find all 9 intersection points");
        // EP03: 6 intersection points
        plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(6,
                testRayIntersections(camera1, plane, 6),
                "didn't find all 6 intersection points");
        // EP04: 9 intersection points
        plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(9,
                testRayIntersections(camera1, plane, 9),
                "didn't find all 9 intersection points");
        // EP05: 9 intersection points
        plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(9,
                testRayIntersections(camera1, plane, 9),
                "didn't find all 9 intersection points");
    }


    public int testRayIntersections(Camera camera, Intersectable geometry, int expectedIntersections) {
        List<Point> intersectionPoints = new LinkedList<>();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                List<Point> intersections = geometry.findIntersections(camera.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    intersectionPoints.addAll(intersections);
                }
            }
        }
        return intersectionPoints.size();
    }


}

