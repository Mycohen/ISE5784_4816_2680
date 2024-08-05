package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;

public class BoundingBox {
    public Point min;
    public Point max;

    // Default constructor with infinite bounds
    public BoundingBox() {
        this.min = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        this.max = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    // Constructor with specified bounds
    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    // Check if this bounding box intersects with a ray
    public boolean hasIntersection(Ray ray) {
        Point p1 = ray.getHead();
        Vector dir = ray.getDirection();

        double tmin = (min.getX() - p1.getX()) / dir.getX();
        double tmax = (max.getX() - p1.getX()) / dir.getX();

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        double tymin = (min.getY() - p1.getY()) / dir.getY();
        double tymax = (max.getY() - p1.getY()) / dir.getY();

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (min.getZ() - p1.getZ()) / dir.getZ();
        double tzmax = (max.getZ() - p1.getZ()) / dir.getZ();

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        return true;
    }

    // Union of two bounding boxes
    public BoundingBox union(BoundingBox box) {
        return new BoundingBox(
                new Point(
                        Math.min(min.getX(), box.min.getX()),
                        Math.min(min.getY(), box.min.getY()),
                        Math.min(min.getZ(), box.min.getZ())
                ),
                new Point(
                        Math.max(max.getX(), box.max.getX()),
                        Math.max(max.getY(), box.max.getY()),
                        Math.max(max.getZ(), box.max.getZ())
                )
        );
    }

    // Calculate the center of the bounding box
    public Point getCenter() {
        return new Point(
                (min.getX() + max.getX()) / 2,
                (min.getY() + max.getY()) / 2,
                (min.getZ() + max.getZ()) / 2
        );
    }

    public static List<Intersectable> buildBVH(List<Intersectable> intersectableList) {
        if (intersectableList.size() <= 1) {
            return intersectableList;
        }

        // Extract infinite geometries into a separate list
        List<Intersectable> infiniteGeometries = new LinkedList<>();
        for (int i = 0; i < intersectableList.size(); i++) {
            var g = intersectableList.get(i);
            if (g.getBoundingBox() == null) {
                infiniteGeometries.add(g);
                intersectableList.remove(i);
                i--;
            }
        }

        // Sort geometries based on their bounding box centroids along the x-axis
        intersectableList.sort(Comparator.comparingDouble(g -> g.getBoundingBox().getCenter().getX()));

        // Split the list into two halves
        int mid = intersectableList.size() / 2;
        List<Intersectable> leftGeometries = buildBVH(intersectableList.subList(0, mid));
        List<Intersectable> rightGeometries = buildBVH(intersectableList.subList(mid, intersectableList.size()));

        // Create bounding boxes for the left and right geometries
        BoundingBox leftBox = getBoundingBox(leftGeometries);
        BoundingBox rightBox = getBoundingBox(rightGeometries);

        // Create a combined bounding box
        Geometries combined = new Geometries();
        combined.add(leftGeometries.toArray(new Intersectable[0]));
        combined.add(rightGeometries.toArray(new Intersectable[0]));
        combined.boundingBox = leftBox.union(rightBox);

        // Return the list of geometries
        List<Intersectable> result = new LinkedList<>(infiniteGeometries);
        result.add(combined);
        return result;
    }

    // Get the bounding box of a list of intersectable geometries
    private static BoundingBox getBoundingBox(List<Intersectable> intersectableList) {
        if (intersectableList.isEmpty()) {
            return null;
        }
        // Get the bounding box of the first geometry and union it with the rest
        BoundingBox boundingBox = intersectableList.get(0).getBoundingBox();
        for (var g : intersectableList) {
            boundingBox = boundingBox.union(g.getBoundingBox());
        }
        return boundingBox;
    }
}