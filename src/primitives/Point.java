package primitives;

// Class representing a point in three-dimensional space
public class Point {

    // Constant representing the origin (0, 0, 0)
    public static final Point ZERO = new Point(0, 0, 0);

    // Three-dimensional coordinates of the point
    // Declared as final to maintain immutability
    final Double3 xyz;

    // Constructor initializing the point with specified coordinates
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    // Constructor initializing the point with provided Double3 object
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    // Override equals method to compare points' coordinates for equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        return xyz.equals(point.xyz);
    }

    // Override hashCode method for consistency with equals method
    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    // Override toString method to represent the point as a string
    @Override
    public String toString() {
        return "Point {" + xyz +'}';
    }

    // Method to calculate the squared distance between two points
    public double distanceSquared(Point point) {
        double dx = xyz.d1 - point.xyz.d1;
        double dy = xyz.d2 - point.xyz.d2;
        double dz = xyz.d3 - point.xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    // Method to calculate the distance between two points
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    // Method to subtract another point's coordinates from this point's coordinates,
    // resulting in a vector pointing from the other point to this point
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    // Method to add a vector's coordinates to this point's coordinates,
    // resulting in a new point displaced by the vector from this point
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }
}
