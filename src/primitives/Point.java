package primitives;

public class Point {
    public static final Point ZERO = new Point(0,0,0) ;

    //or protected
    final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point {" + xyz +'}';
    }

    public double distanceSquared(Point point) {
        double dx = xyz.d1 - point.xyz.d1;
        double dy = xyz.d2 - point.xyz.d2;
        double dz = xyz.d3 - point.xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    //distance between two points
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }
}
