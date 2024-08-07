package test;

import geometries.*;
import primitives.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
/**
 * A class for reading triangle meshes from OBJ files.
 */
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
                        Point p1 = vertices.get(indices[0]);
                        Point p2 = vertices.get(indices[i]);
                        Point p3 = vertices.get(indices[i + 1]);
                        if (!p1.equals(p2) && !p1.equals(p3) && !p2.equals(p3)) {
                            triangles.add(new Triangle(p1, p2, p3));
                        } else {
                            System.out.println("Skipping degenerate triangle: " + p1 + ", " + p2 + ", " + p3);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return triangles;
    }

    public static List<Triangle> readTrianglesFromOBJ_MTL(String objFilePath, String mtlFilePath) {
        List<Point> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();
        Map<String, Material> materials = new HashMap<>();
        String currentMaterial = null;

        // קריאת החומרים מקובץ MTL
        try (BufferedReader br = new BufferedReader(new FileReader(mtlFilePath))) {
            String line;
            Material currentMaterialObj = null;
            String currentMaterialName = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("newmtl ")) {
                    if (currentMaterialObj != null && currentMaterialName != null) {
                        materials.put(currentMaterialName, currentMaterialObj);
                    }
                    currentMaterialName = line.split("\\s+")[1];
                    currentMaterialObj = new Material();
                } else if (line.startsWith("Kd ")) {
                    String[] parts = line.split("\\s+");
                    Double3 kd = new Double3(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    currentMaterialObj.setkD(kd);
                } else if (line.startsWith("Ks ")) {
                    String[] parts = line.split("\\s+");
                    Double3 ks = new Double3(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    currentMaterialObj.setkS(ks);
                } else if (line.startsWith("Ns ")) {
                    String[] parts = line.split("\\s+");
                    int shininess = Integer.parseInt(parts[1]);
                    currentMaterialObj.setShininess(shininess);
                } else if (line.startsWith("Kr ")) {
                    String[] parts = line.split("\\s+");
                    Double3 kr = new Double3(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    currentMaterialObj.setKR(kr);
                } else if (line.startsWith("Kt ")) {
                    String[] parts = line.split("\\s+");
                    Double3 kt = new Double3(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    currentMaterialObj.setKT(kt);
                }
            }
            if (currentMaterialObj != null && currentMaterialName != null) {
                materials.put(currentMaterialName, currentMaterialObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // קריאת המשולשים מקובץ OBJ והחלת החומרים
        try (BufferedReader br = new BufferedReader(new FileReader(objFilePath))) {
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
                        Point p1 = vertices.get(indices[0]);
                        Point p2 = vertices.get(indices[i]);
                        Point p3 = vertices.get(indices[i + 1]);
                        if (!p1.equals(p2) && !p1.equals(p3) && !p2.equals(p3)) {
                            Triangle triangle = new Triangle(p1, p2, p3);
                            if (currentMaterial != null && materials.containsKey(currentMaterial)) {
                                triangle.setMaterial(materials.get(currentMaterial));
                            }
                            triangles.add(triangle);
                        } else {
                            System.out.println("Skipping degenerate triangle: " + p1 + ", " + p2 + ", " + p3);
                        }
                    }
                } else if (line.startsWith("usemtl ")) {
                    currentMaterial = line.split("\\s+")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return triangles;
    }

    public static void main(String[] args) {
        String objFilePath = "path/to/your/file.obj";
        String mtlFilePath = "path/to/your/file.mtl";

        // קריאת המשולשים מקובץ OBJ והחלת החומרים
        List<Triangle> triangles = readTrianglesFromOBJ_MTL(objFilePath, mtlFilePath);

        // ביצוע פעולות נוספות עם המשולשים
        for (Triangle triangle : triangles) {
            System.out.println(triangle);
        }
    }
}
