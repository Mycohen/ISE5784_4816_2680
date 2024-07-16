package geometries;

import primitives.*;

import java.util.List;
import java.util.ArrayList;

public class Ring extends Geometry {
    private final Circle outerCircle;
    private final Circle innerCircle;

    public Ring(Point center, double outerRadius, double innerRadius) {
        if (innerRadius >= outerRadius) {
            throw new IllegalArgumentException("Inner radius must be smaller than outer radius");
        }
        this.outerCircle = new Circle(center, outerRadius);
        this.innerCircle = new Circle(center, innerRadius);
    }

    @Override
    public Vector getNormal(Point point) {
        // The normal would be the same as the normal of a circle
        return new Vector(0, 0, 1);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Get intersections with the outer circle
        List<GeoPoint> outerIntersections = outerCircle.findGeoIntersections(ray, maxDistance);
        if (outerIntersections == null) return null;

        // Filter out intersections that are inside the inner circle
        List<GeoPoint> ringIntersections = new ArrayList<>();
        for (GeoPoint geoPoint : outerIntersections) {
            double distanceToCenter = geoPoint.point.distance(outerCircle.getCenter());
            if (distanceToCenter >= innerCircle.getRadius()) {
                ringIntersections.add(geoPoint);
            }
        }

        return ringIntersections.isEmpty() ? null : ringIntersections;
    }
}
