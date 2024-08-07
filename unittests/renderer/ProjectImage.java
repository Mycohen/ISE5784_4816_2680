package renderer;


import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import test.*;
import scene.Scene;


import java.util.List;

import static java.awt.Color.*;

public class ProjectImage {

    @Test
    public void imageBasedTriangles() {

        Scene scene = new Scene("Test final image");
        final Camera.Builder camera2 = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setMultithreading(9)
                .setSamplesPerPixel(9)
                .setVUpSize(200, 200).setVpDistance(1000);

        // Replace with your OBJ file path
        List<Triangle> triangles = Generated3DCode.Triangles();
        for (Triangle triangle : triangles) {
            scene.geometries.add(triangle.setEmission(new Color(255, 0, 255))
                    .setMaterial(new Material()
                            .setkD(new Double3(0.2, 0.6, 0.4))
                            .setkS(new Double3(0.2, 0.4, 0.3))
                            .setShininess(1000)
                            .setKR(0.1)//));
                            .setKT(new Double3(0))));
        }
        triangles = Generated3DCode1.Triangles();
        for (Triangle triangle : triangles) {
            scene.geometries.add(triangle.setEmission(new Color(0, 0, 255))
                    .setMaterial(new Material()
                            .setkD(new Double3(0.2, 0.6, 0.4))
                            .setkS(new Double3(0.2, 0.4, 0.3))
                            .setShininess(1000)
                            .setKR(0.1)//));
                            .setKT(new Double3(0))));
        }
        triangles = Generated3DCode2.Triangles();
        for (Triangle triangle : triangles) {
            scene.geometries.add(triangle.setEmission(new Color(0, 255, 0))
                    .setMaterial(new Material()
                            .setkD(new Double3(0.2, 0.6, 0.4))
                            .setkS(new Double3(0.2, 0.4, 0.3))
                            .setShininess(1000)
                            .setKR(0.1)//));
                            .setKT(new Double3(0))));
        }
        triangles = Generated3DCode3.Triangles();
        for (Triangle triangle : triangles) {
            scene.geometries.add(triangle.setEmission(new Color(255, 0, 0))
                    .setMaterial(new Material()
                            .setkD(new Double3(0.2, 0.6, 0.4))
                            .setkS(new Double3(0.2, 0.4, 0.3))
                            .setShininess(1000)
                            .setKR(0)//));
                            .setKT(new Double3(0))));
        }

        scene.geometries.add(new Sphere(5, new Point(15, -400, 5)).setEmission(new Color(0, 255, 255)).setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setKR(0.97).setkS(new Double3(0.2, 0.4, 0.3)).setShininess(10000)));
        scene.geometries.add(new Sphere(4, new Point(-10, -400, 4)).setEmission(new Color(255, 0, 0)).setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setKR(0.97).setkS(new Double3(0.2, 0.4, 0.3)).setShininess(10000)));
        scene.geometries.add(new Sphere(3, new Point(0, -400, 3)).setEmission(new Color(0, 0, 255)).setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setKR(0.97).setkS(new Double3(0.2, 0.4, 0.3)).setShininess(10000)));
        scene.lights.add(new SpotLight(new Color(300, 100, 150), new Point(0, -420, 0), new Vector(0, 1, 0)));
        scene.geometries.add(new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0)).setEmission(new Color(WHITE).reduce(5)).setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setShininess(301)));
        scene.geometries.add(new Plane(new Point(0, 350, 0), new Point(0, 350, 1), new Point(1, 350, 0)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setShininess(100000)));
        scene.geometries.makeBVH();

        scene.lights.add(new SpotLight(new Color(300, 200, 10000), new Point(-200, 200, 150), new Vector(382, -353, -150))
                .setKl(0.001).setKq(0.00004).setNarrowBeam(10));
        scene.lights.add(new SpotLight(new Color(10000, 200, 100), new Point(200, 200, 150), new Vector(-382, -353, -150))
                .setKl(0.001).setKq(0.00004).setNarrowBeam(10));


        camera2.setImageWriter(new ImageWriter("light83", 500, 500))
                .setLocation(new Point(10, -780, 30))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void testShineess() {

        Scene scene = new Scene("Test final image");
        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setMultithreading(9)
                .setSamplesPerPixel(9)
                .setVUpSize(200, 200).setVpDistance(1000);


        Geometry crystalSphere = new Sphere(50, new Point(0, 0, -200))
                .setEmission(new Color(20, 20, 20))
                .setMaterial(new Material()
                        .setKd(0.2).setKs(0.9).setShininess(300)
                        .setKR(0.4).setKT(0.6));

        // Create a checkered floor
        int squareSize = 50;
        Geometries floor = new Geometries();
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                Color color = (i + j) % 2 == 0 ? new Color(20, 20, 20) : new Color(180, 180, 180);
                floor.add(new Polygon(
                        new Point(i * squareSize, -50, j * squareSize - 200),
                        new Point((i + 1) * squareSize, -50, j * squareSize - 200),
                        new Point((i + 1) * squareSize, -50, (j + 1) * squareSize - 200),
                        new Point(i * squareSize, -50, (j + 1) * squareSize - 200))
                        .setEmission(color)
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(30).setKR(0.1)));
            }
        }

        // Create a backdrop
        Geometry backdrop = new Plane(new Point(0, 0, -300), new Vector(0, 0, 1))
                .setEmission(new Color(50, 50, 100))
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));

        // Add some additional objects for interesting reflections
        Geometry reflectiveSphere = new Sphere(30, new Point(100, 0, -150))
                .setEmission(new Color(100, 20, 20))
                .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKR(0.3));

        Geometry reflectiveSphere2 = new Sphere(30, new Point(-100, -20, -130))
                .setEmission(new Color(20, 20, 180))
                .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKR(0.3));

        // Add all geometries to the scene
        scene.geometries.add(crystalSphere, floor, backdrop, reflectiveSphere, reflectiveSphere2);

        // Set up lighting
        scene.setAmbientLight(new AmbientLight(new Color(150, 30, 50), 0.1));

        scene.lights.add(new SpotLight(
                new Color(80, 30, 200),
                new Point(100, 100, 100),
                new Vector(-1, -1, -2))
                .setKl(0.00001).setKq(0.000005));

        // Create an ImageWriter
        ImageWriter imageWriter = new ImageWriter("CrystalSphereScene", 800, 800);

        // Create a ray tracer


        // Render the image
        camera.setImageWriter(imageWriter)
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void testCylinder() {
        Scene scene = new Scene("Test cylinder");
        scene.geometries.add(new Cylinder(10, new Ray(Point.ZERO, new Vector(1, 0, 0)), 100)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.9).setShininess(300).setKR(0.4).setKT(0.6)));
        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setMultithreading(9)
                .setSamplesPerPixel(9)
                .setVUpSize(200, 200).setVpDistance(1000);
        camera.setImageWriter(new ImageWriter("Cylinder", 500, 500))
                .build()
                .renderImage()
                .writeToImage();


    }

    @Test
    public void testFinalImage() {
        Scene scene = new Scene("Final image");

        // Crystal sphere at the origin
        Geometry crystalSphere = new Sphere(50, new Point(0, 0, 50))
                .setEmission(new Color(20, 20, 20))
                .setMaterial(new Material()
                        .setKd(0.2).setKs(0.9).setShininess(300)
                        .setKR(0.4).setKT(0.6));

        // Create a checkered floor on YX plane (vertical)
        int squareSize = 50;
        Geometries checkerboard = new Geometries();
        for (int i = -18; i <= 5; i++) {
            for (int j = -18; j <= 5; j++) {
                Color color = (i + j) % 2 == 0 ? new Color(20, 20, 20) : new Color(180, 180, 180);
                checkerboard.add(new Polygon(
                        new Point(i * squareSize, j * squareSize, 0),
                        new Point((i + 1) * squareSize, j * squareSize, 0),
                        new Point((i + 1) * squareSize, (j + 1) * squareSize, 0),
                        new Point(i * squareSize, (j + 1) * squareSize, 0))
                        .setEmission(color)
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(30).setKR(0.1)));
            }
        }

        // Add some additional objects for interesting reflections
        Geometry reflectiveSphere = new Sphere(30, new Point(100, 0, 30))
                .setEmission(new Color(100, 20, 20))
                .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKR(0.3));

        // Create background wall
        Geometry backgroundWall = new Plane(new Point(0, 250, 0), new Vector(0, -1, 0))
                .setEmission(new Color(50, 50, 80))
                .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(30));

        // Define the base points for the cube
        Point p1 = new Point(-150, 150, 0);
        Point p2 = new Point(-100, 150, 0);
        Point p3 = new Point(-150, 100, 0);
        Point p4 = new Point(-100, 100, 0);

        // Set material properties to match a matte, bubblegum-like appearance
        Material bubblegumMaterial = new Material()
                .setKd(0.9).setKs(0.1).setShininess(10);

        // Set a solid pink color for the bubblegum look
        Color bubblegumColor = new Color(255, 105, 180);

        // Create the cube faces and apply the material and color
        Geometry[] cubeFaces = {
                new Polygon(p1, p2, p4, p3),
                new Polygon(p1.add(new Vector(0, 0, 15)), p2.add(new Vector(0, 0, 15)),
                        p4.add(new Vector(0, 0, 15)), p3.add(new Vector(0, 0, 15))),
                new Polygon(p1, p2, p2.add(new Vector(0, 0, 15)), p1.add(new Vector(0, 0, 15))),
                new Polygon(p2, p4, p4.add(new Vector(0, 0, 15)), p2.add(new Vector(0, 0, 15))),
                new Polygon(p4, p3, p3.add(new Vector(0, 0, 15)), p4.add(new Vector(0, 0, 15))),
                new Polygon(p3, p1, p1.add(new Vector(0, 0, 15)), p3.add(new Vector(0, 0, 15)))
        };

        // Apply material and color to each face
        for (Geometry face : cubeFaces) {
            face.setEmission(bubblegumColor).setMaterial(bubblegumMaterial);
        }

        Geometry sphereOnCube = new Sphere(15, new Point(-125, 125, 30))
                .setEmission(new Color(231, 158, 110))
                .setMaterial(new Material()
                        .setKd(0.7).setKs(0.3).setShininess(10)
                        .setKT(0.2).setKR(0.1));


        // Add decorative elements to the background wall
        Geometries wallDecorations = new Geometries();

        // Create cubes at specific locations
        Color cubeColor = new Color(0.5, 0.5, 1); // Light blue color
        Material cubeMaterial = new Material().setKd(0.7).setKs(0.3).setShininess(100).setKR(0.1);

        // First cube with mirror
        Point cube1Center = new Point(-100, 245, 100);
        wallDecorations.add(createCube(cube1Center, 20, cubeColor, cubeMaterial));
        // Adjust mirror position and orientation to reflect the crystal sphere
        wallDecorations.add(createMirrorWithFrame(new Point(-100, 245, 120), 18, new Vector(-1, -5, -2)));

        // Second cube with flower
        Point cube2Center = new Point(0, 245, 150);
        wallDecorations.add(createCube(cube2Center, 30, cubeColor, cubeMaterial));
        wallDecorations.add(createFlower(new Point(0, 245, 180), 25));

        // Third cube with
        Point cube3Center = new Point(100, 245, 125);
        wallDecorations.add(createCube(cube3Center, 25, cubeColor, cubeMaterial));
        wallDecorations.add(createMirrorWithFrame(new Point(100, 245, 150), 20, new Vector(-1, -5, -2).normalize()));

        Geometry mirror = new Polygon(
                new Point(-150, 150, 0),
                new Point(-150, 150, 80),
                new Point(-50, 200, 80),
                new Point(-50, 200, 0)
        );
        Material mirrorMaterial = new Material()
                .setKD(0.1)  // Low diffuse reflection
                .setKS(0.9)  // High specular reflection
                .setShininess(300)  // High shininess for a sharp reflection
                .setKR(0.9);  // High reflection coefficient
        mirror.setMaterial(mirrorMaterial);

        // Wave-like pattern
        for (int i = 0; i < 3; i++) {
            wallDecorations.add(createWave(i * 60 - 100, 240, 60, 250, 30, 20,
                    new Color(200, 200, 255), new Material().setKd(0.8).setKs(0.2).setShininess(30).setKT(0.3)));
        }

        // Add all geometries to the scene
        scene.geometries.add(crystalSphere, checkerboard, reflectiveSphere, backgroundWall, sphereOnCube, wallDecorations, mirror);
        scene.geometries.add(cubeFaces);
        scene.geometries.makeBVH();

        // Lighting setup
        scene.lights.add(new PointLight(new Color(200, 200, 200), new Point(0, -70, 25))
                .setKl(0.0002).setKq(0.00002));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-125, -125, 50))
                .setKl(0.001).setKq(0.0002));

        //Camera setup
        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, -1500, 90))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setMultithreading(9)
                .setSamplesPerPixel(1)
                .setVUpSize(200, 150)
                .setVpDistance(1000);

        camera.setImageWriter(new ImageWriter("1.0.1 Final Image", 1600, 1200))
                .build()
                .renderImage()
                .writeToImage();
    }

    private Geometries createMirrorWithFrame(Point center, double size, Vector normal) {
        Geometries mirrorWithFrame = new Geometries();

        // Mirror
        double mirrorSize = size * 0.8;
        Vector up = new Vector(0, 1, 0);
        Vector right = normal.crossProduct(up).normalize();
        up = right.crossProduct(normal).normalize();

        Point p1 = center.add(up.scale(-mirrorSize / 2)).add(right.scale(-mirrorSize / 2));
        Point p2 = center.add(up.scale(-mirrorSize / 2)).add(right.scale(mirrorSize / 2));
        Point p3 = center.add(up.scale(mirrorSize / 2)).add(right.scale(mirrorSize / 2));
        Point p4 = center.add(up.scale(mirrorSize / 2)).add(right.scale(-mirrorSize / 2));

        Geometry mirror = new Polygon(p1, p2, p3, p4)
                .setEmission(new Color(255, 255, 255))  // White emission
                .setMaterial(new Material()
                        .setKd(0.2)  // Low diffuse reflection
                        .setKs(0.9)  // High specular reflection
                        .setShininess(300)
                        .setKR(0.9)); // High reflectivity

        mirrorWithFrame.add(mirror);

        // Frame
        double frameThickness = (size - mirrorSize) / 2;
        Color frameColor = new Color(150, 75, 0);
        Material frameMaterial = new Material().setKd(0.8).setKs(0.2).setShininess(30);

        Point[] framePoints = {
                p1.add(up.scale(-frameThickness / 2)).add(right.scale(-frameThickness / 2)),
                p2.add(up.scale(-frameThickness / 2)).add(right.scale(frameThickness / 2)),
                p3.add(up.scale(frameThickness / 2)).add(right.scale(frameThickness / 2)),
                p4.add(up.scale(frameThickness / 2)).add(right.scale(-frameThickness / 2))
        };

        for (int i = 0; i < 4; i++) {
            Point frameCenter = framePoints[i].add(framePoints[(i + 1) % 4].subtract(framePoints[i]).scale(0.5));
            mirrorWithFrame.add(createCube(frameCenter, frameThickness, frameColor, frameMaterial));
        }

        return mirrorWithFrame;
    }


    private Geometries createFlower(Point center, double size) {
        Geometries flower = new Geometries();
        Color petalColor = new Color(255, 182, 193); // Light pink
        Material petalMaterial = new Material().setKd(0.8).setKs(0.2).setShininess(30);

        // Petals
        for (int i = 0; i < 5; i++) {
            double angle = i * 2 * Math.PI / 5;
            Point tip = center.add(new Vector(Math.cos(angle) * size / 2, 0.1, Math.sin(angle) * size / 2));
            Point left = center.add(new Vector(Math.cos(angle - 0.5) * size / 4, 0.1, Math.sin(angle - 0.5) * size / 4));
            Point right = center.add(new Vector(Math.cos(angle + 0.5) * size / 4, 0.1, Math.sin(angle + 0.5) * size / 4));

            Geometry petal = new Triangle(center, left, tip)
                    .setEmission(petalColor)
                    .setMaterial(petalMaterial);
            flower.add(petal);

            petal = new Triangle(center, right, tip)
                    .setEmission(petalColor)
                    .setMaterial(petalMaterial);
            flower.add(petal);
        }

        // Center
        Geometry flowerCenter = new Sphere(size / 8, center.add(new Vector(0, 0.2, 0)))
                .setEmission(new Color(255, 215, 0)) // Gold
                .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(30));
        flower.add(flowerCenter);

        return flower;
    }

    private Geometries createCube(Point center, double size, Color color, Material material) {
        double halfSize = size / 2;
        Geometries cube = new Geometries();
        Polygon[] faces = {
                new Polygon(center.add(new Vector(-halfSize, -halfSize, -halfSize)), center.add(new Vector(halfSize, -halfSize, -halfSize)),
                        center.add(new Vector(halfSize, halfSize, -halfSize)), center.add(new Vector(-halfSize, halfSize, -halfSize))),
                new Polygon(center.add(new Vector(-halfSize, -halfSize, halfSize)), center.add(new Vector(halfSize, -halfSize, halfSize)),
                        center.add(new Vector(halfSize, halfSize, halfSize)), center.add(new Vector(-halfSize, halfSize, halfSize))),
                new Polygon(center.add(new Vector(-halfSize, -halfSize, -halfSize)), center.add(new Vector(-halfSize, -halfSize, halfSize)),
                        center.add(new Vector(-halfSize, halfSize, halfSize)), center.add(new Vector(-halfSize, halfSize, -halfSize))),
                new Polygon(center.add(new Vector(halfSize, -halfSize, -halfSize)), center.add(new Vector(halfSize, -halfSize, halfSize)),
                        center.add(new Vector(halfSize, halfSize, halfSize)), center.add(new Vector(halfSize, halfSize, -halfSize))),
                new Polygon(center.add(new Vector(-halfSize, -halfSize, -halfSize)), center.add(new Vector(halfSize, -halfSize, -halfSize)),
                        center.add(new Vector(halfSize, -halfSize, halfSize)), center.add(new Vector(-halfSize, -halfSize, halfSize))),
                new Polygon(center.add(new Vector(-halfSize, halfSize, -halfSize)), center.add(new Vector(halfSize, halfSize, -halfSize)),
                        center.add(new Vector(halfSize, halfSize, halfSize)), center.add(new Vector(-halfSize, halfSize, halfSize)))
        };

        for (Polygon face : faces) {
            cube.add(face.setEmission(color).setMaterial(material));
        }

        return cube;
    }

    private Geometries createWave(double x, double y, double z, double width, double height, double amplitude, Color color, Material material) {
        Geometries wave = new Geometries();
        int segments = 50;
        double segmentWidth = width / segments;

        for (int i = 0; i < segments; i++) {
            double t1 = (double) i / segments;
            double t2 = (double) (i + 1) / segments;

            double x1 = x + t1 * width;
            double x2 = x + t2 * width;
            double y1 = y + amplitude * Math.sin(t1 * Math.PI * 4);
            double y2 = y + amplitude * Math.sin(t2 * Math.PI * 4);
            double z1 = z + height * t1;
            double z2 = z + height * t2;

            Geometry segment = new Polygon(
                    new Point(x1, y1, z1),
                    new Point(x2, y2, z2),
                    new Point(x2, y2, z2 + 1),
                    new Point(x1, y1, z1 + 1)
            ).setEmission(color).setMaterial(material);

            wave.add(segment);
        }

        return wave;
    }
}
