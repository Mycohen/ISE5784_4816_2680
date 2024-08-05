package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface representing an intersectable geometry in 3D space.
 * This interface defines a method to find intersections of a ray with the geometry.
 *
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 * @see Point
 * @see Ray
 * @see List
 */
public abstract class Intersectable {

    protected BoundingBox boundingBox;
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);



    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint (Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
