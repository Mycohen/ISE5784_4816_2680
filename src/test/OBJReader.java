package test;

import geometries.*;
import primitives.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJReader {

    public static List<Triangle> readTrianglesFromOBJ(String filePath) {
        List<Point> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    vertices.add(new Point(x, y, z));
                } else if (line.startsWith("f ")) {

                    String[] parts = line.split("\\s+");
                    int[] indices = new int[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        indices[i - 1] = Integer.parseInt(parts[i].split("/")[0]) - 1;
                    }
                    for (int i = 1; i < indices.length - 1; i++) {
                        triangles.add(new Triangle(vertices.get(indices[0]), vertices.get(indices[i]), vertices.get(indices[i + 1])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return triangles;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\משפחה\\Downloads\\tinker.obj"; // Replace with your OBJ file path
        List<Triangle> triangles = readTrianglesFromOBJ(filePath);

        // Print triangles
        System.out.println("Triangles:");
        for (Triangle triangle : triangles) {
            System.out.println(triangle);
        }
    }
}

//    // Define a Point class
//    static class Point {
//        double x, y, z;
//
//        public Point(double x, double y, double z) {
//            this.x = x;
//            this.y = y;
//            this.z = z;
//        }
//
//        @Override
//        public String toString() {
//            return "Point{" +
//                    "x=" + x +
//                    ", y=" + y +
//                    ", z=" + z +
//                    '}';
//        }
//    }
//
//    // Define a Triangle class
//    static class Triangle {
//        Point p1, p2, p3;
//
//        public Triangle(Point p1, Point p2, Point p3) {
//            this.p1 = p1;
//            this.p2 = p2;
//            this.p3 = p3;
//        }
//
//        @Override
//        public String toString() {
//            return "Triangle{" +
//                    "p1=" + p1 +
//                    ", p2=" + p2 +
//                    ", p3=" + p3 +
//                    '}';
//        }
//    }
//}
