package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Interface representing an intersectable geometry in 3D space.
 * This interface defines a method to find intersections of a ray with the geometry.
 *
 * @see Point
 * @see Ray
 * @see List
 *
 * Authors:
 * Moshe Yaakov Cohen
 * Eliaou Kopinski
 */
public interface Intersectable {

    /**
     * Finds all intersection points between a given ray and the geometry.
     *
     * @param ray the ray to intersect with the geometry
     * @return a list of points where the ray intersects the geometry
     */
    public  List<Point> findIntsersections(Ray ray);
}
