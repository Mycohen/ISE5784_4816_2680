/**
 *
 */
package renderer;

import static java.awt.Color.*;


import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));


    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVUpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVUpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVUpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }


    @Test
    public void MosaicTriangles() {

        scene.background = new Color(50, 50, 50);

        // Define colors
        Color[] colors = {new Color(RED), new Color(GREEN), new Color(BLUE), new Color(YELLOW),
                new Color(CYAN), new Color(MAGENTA), new Color(ORANGE), new Color(PINK)};

        // Create triangles in a grid pattern
        int gridSize = 10; // Adjust the grid size as needed
        double size = 50;  // Size of each triangle
        for (int i = -gridSize; i < gridSize; i++) {
            for (int j = -gridSize; j < gridSize; j++) {
                Color color = colors[(i + gridSize) % colors.length];
                scene.geometries.add(
                        new Triangle(
                                new Point(i * size, j * size, -100),
                                new Point(i * size, (j + 1) * size, -100),
                                new Point((i + 1) * size, j * size, -100))
                                .setEmission(color)
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                );
            }
        }

        // Add spheres with new colors
        scene.geometries.add(
                new Sphere(100d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(255, 215, 0)) // Gold
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        // Camera view configurations
        cameraBuilder.setVpDistance(1000).setVUpSize(500, 500);

        // View 1: Default front view
        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setImageWriter(new ImageWriter("mosaicTriangles_frontView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 2: Left front top view
        cameraBuilder.setLocation(new Point(-500, 500, 1000))
                .setDirection(new Vector(0.5, -0.5, -1).normalize(), new Vector(-0.5, -0.5, 0).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_leftFrontTopView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 3: Right front top view
        cameraBuilder.setLocation(new Point(500, 500, 1000))
                .setDirection(new Vector(-0.5, -0.5, -1).normalize(), new Vector(0.5, -0.5, 0).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_rightFrontTopView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 4: Left bottom view
        cameraBuilder.setLocation(new Point(-500, -500, 1000))
                .setDirection(new Vector(0.5, 0.5, -1).normalize(), new Vector(-0.5, 0.5, 0).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_leftBottomView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 5: Right bottom view
        cameraBuilder.setLocation(new Point(500, -500, 1000))
                .setDirection(new Vector(-0.5, 0.5, -1).normalize(), new Vector(0.5, 0.5, 0).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_rightBottomView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 6: Top down view
        cameraBuilder.setLocation(new Point(0, 1000, 0))
                .setDirection(new Vector(0, -1, 0).normalize(), new Vector(0, 0, -1).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_topDownView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();

        // View 7: Bottom up view
        cameraBuilder.setLocation(new Point(0, -1000, 0))
                .setDirection(new Vector(0, 1, 0).normalize(), new Vector(0, 0, 1).normalize())
                .setImageWriter(new ImageWriter("mosaicTriangles_bottomUpView", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }



}
