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
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
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
        Geometry reflectiveSphere = new Sphere(30, new Point(0, 100, 30))
                .setEmission(new Color(100, 20, 20))
                .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKR(0.3));



        scene.geometries.add(crystalSphere, checkerboard, reflectiveSphere);

        scene.setAmbientLight(new AmbientLight(new Color(150, 30, 50), 0.1));

        scene.lights.add(new SpotLight(
                new Color(80, 30, 200),
                new Point(100, 100, 500),
                new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));


        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(10, -1200, 30))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setMultithreading(9)
                .setSamplesPerPixel(9)
                .setVUpSize(200, 200).setVpDistance(1500);
        camera.setImageWriter(new ImageWriter("first NaturalAxisCrystalScene", 800, 800))
                .build()
                .renderImage()
                .writeToImage();


    }


}
