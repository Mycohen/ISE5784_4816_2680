package renderer;


import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import test.*;
import scene.Scene;


import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class ProjectImage {

    @Test
    public void finalImage() {

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
    public void testVerticalCheckerboardScene() {
        Scene scene = new Scene("Vertical Checkerboard Crystal Scene");

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
        wallDecorations.add(createCube(new Point(-100, 245, 100), 20, cubeColor, cubeMaterial));
        wallDecorations.add(createCube(new Point(0, 245, 150), 30, cubeColor, cubeMaterial));
        wallDecorations.add(createCube(new Point(100, 245, 125), 25, cubeColor, cubeMaterial));

        // Wave-like pattern
        for (int i = 0; i < 3; i++) {
            wallDecorations.add(createWave(i * 60 - 100, 240, 60, 200, 30, 20,
                    new Color(200, 200, 255), new Material().setKd(0.8).setKs(0.2).setShininess(30).setKT(0.3)));
        }

        // Add all geometries to the scene
        scene.geometries.add(crystalSphere, checkerboard, reflectiveSphere, backgroundWall, sphereOnCube, wallDecorations);
        scene.geometries.add(cubeFaces);
        scene.geometries.makeBVH();

        // Lighting setup
        scene.lights.add(new PointLight(new Color(200, 200, 200), new Point(0, -70, 25))
                .setKl(0.0002).setKq(0.00002));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-125, -125, 50))
                .setKl(0.001).setKq(0.0002));

        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, -1500, 90))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setMultithreading(9)
                .setSamplesPerPixel(1)
                .setVUpSize(200, 150)
                .setVpDistance(1000);

        camera.setImageWriter(new ImageWriter("2.0.2 Bubblegum Cube NaturalAxisCrystalScene", 1600, 1200))
                .build()
                .renderImage()
                .writeToImage();
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
