package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;

/**
 * Represents a bounding box, typically used in spatial partitioning algorithms like BVH (Bounding Volume Hierarchy).
 * A bounding box is defined by its minimum and maximum corners.
 */
public class BoundingBox {
    public Point min;
    public Point max;

    /**
     * Default constructor that creates a bounding box with infinite bounds.
     */
    public BoundingBox() {
        this.min = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        this.max = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Constructor that creates a bounding box with specified bounds.
     *
     * @param min The minimum point of the bounding box.
     * @param max The maximum point of the bounding box.
     */
    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Determines whether a ray intersects with this bounding box.
     *
     * <p>This method uses the "slab" method to determine if a ray intersects
     * the box. It calculates the intersection of the ray with the planes of the box
     * for each axis, and checks if these intervals overlap.</p>
     *
     * @param ray The ray to test for intersection with the bounding box.
     * @return true if the ray intersects the bounding box, false otherwise.
     */
    public boolean hasIntersection(Ray ray) {
        // Get the origin point and direction vector of the ray
        Point p1 = ray.getHead();
        Vector dir = ray.getDirection();

        // Calculate intersection intervals on the X-axis
        double tmin = (min.getX() - p1.getX()) / dir.getX();
        double tmax = (max.getX() - p1.getX()) / dir.getX();

        // Ensure tmin is the smaller value (swap if necessary)
        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        // Calculate intersection intervals on the Y-axis
        double tymin = (min.getY() - p1.getY()) / dir.getY();
        double tymax = (max.getY() - p1.getY()) / dir.getY();

        // Ensure tymin is the smaller value (swap if necessary)
        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        // Check if the intervals on the X and Y axes overlap
        if ((tmin > tymax) || (tymin > tmax))
            return false;

        // Update tmin and tmax based on the Y intervals
        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        // Calculate intersection intervals on the Z-axis
        double tzmin = (min.getZ() - p1.getZ()) / dir.getZ();
        double tzmax = (max.getZ() - p1.getZ()) / dir.getZ();

        // Ensure tzmin is the smaller value (swap if necessary)
        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        // Check if the intervals on the X and Z axes overlap
        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        // If we reach this point, the ray intersects the bounding box
        return true;
    }

    /**
     * Creates a new bounding box that is the union of this bounding box and another bounding box.
     *
     * @param box The other bounding box to union with.
     * @return A new bounding box that encompasses both bounding boxes.
     */
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

    /**
     * Calculates the center point of the bounding box.
     *
     * @return The center point of the bounding box.
     */
    public Point getCenter() {
        return new Point(
                (min.getX() + max.getX()) / 2,
                (min.getY() + max.getY()) / 2,
                (min.getZ() + max.getZ()) / 2
        );
    }

    /**
     * Builds a Bounding Volume Hierarchy (BVH) from a list of intersectable geometries.
     *
     * <p>This method recursively splits the list of geometries into two halves based on the
     * x-axis centroid of their bounding boxes, and creates a BVH tree structure by combining
     * bounding boxes. Geometries without a bounding box are handled separately.</p>
     *
     * @param intersectableList The list of geometries to build the BVH from.
     * @return A list containing the root geometry of the BVH and any infinite geometries.
     */
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

    /**
     * Computes the bounding box that encompasses all geometries in a given list.
     *
     * @param intersectableList The list of geometries to compute the bounding box for.
     * @return The bounding box that encompasses all geometries in the list, or null if the list is empty.
     */
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
