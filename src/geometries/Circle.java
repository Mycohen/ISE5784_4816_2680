package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Circle extends Geometry {
    private final Point center;
    private final double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        // Assuming the circle lies on the XY plane
        return new Vector(0, 0, 1);
    }
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Implementing ray-circle intersection logic
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Vector u = center.subtract(p0);
        double tm = alignZero(u.dotProduct(v));
        double dSquared = alignZero(u.lengthSquared() - tm * tm);
        double radiusSquared = radius * radius;

        if (dSquared >= radiusSquared) return null;

        double th = Math.sqrt(radiusSquared - dSquared);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        List<GeoPoint> intersections = new ArrayList<>();

        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            intersections.add(new GeoPoint(this, ray.getPoint(t1)));
        }

        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            intersections.add(new GeoPoint(this, ray.getPoint(t2)));
        }

        return intersections.isEmpty() ? null : intersections;
    }


    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
