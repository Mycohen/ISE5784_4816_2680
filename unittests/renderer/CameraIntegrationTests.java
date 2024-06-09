package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {
    @Test
    void testCameraWithSphere() {
        final Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirectionVectors(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);
        final int nX = 3;
        final int nY = 3;

        // ============ Equivalence Partitions Tests ==============
        // EP01: best case, two intersections point
        Camera camera1 = cameraBuilder.setVpSize(nX, nY).build();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        List<Point> intersectionPoints = new ArrayList<>();

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                List<Point> intersections = sphere.findIntersections(camera1.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    intersectionPoints.addAll(intersections);
                }
            }
        }

        assertEquals(2, intersectionPoints.size(), "Bad number of intersection points");
    }
}
