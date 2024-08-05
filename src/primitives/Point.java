package primitives;

/**
 * Class representing a point in three-dimensional space.
 */
public class Point {

    /**
     * Constant representing the origin (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Three-dimensional coordinates of the point.
     * Declared as final to maintain immutability.
     */
    final Double3 xyz;

    /**
     * Constructor initializing the point with specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor initializing the point with provided Double3 object.
     *
     * @param xyz the coordinates of the point as a Double3 object
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Method to add a vector's coordinates to this point's coordinates,
     * resulting in a new point displaced by the vector from this point.
     *
     * @param vector the vector to add
     * @return the new point displaced by the vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Method to subtract another point's coordinates from this point's coordinates,
     * resulting in a vector pointing from the other point to this point.
     *
     * @param point the other point
     * @return the vector pointing from the other point to this point
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Method to calculate the distance between two points.
     *
     * @param point the other point
     * @return the distance between this point and the other point
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Method to calculate the squared distance between two points.
     *
     * @param point the other point
     * @return the squared distance between this point and the other point
     */
    public double distanceSquared(Point point) {
        double dx = xyz.d1 - point.xyz.d1;
        double dy = xyz.d2 - point.xyz.d2;
        double dz = xyz.d3 - point.xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }







    /**
     * Override equals method to compare points' coordinates for equality.
     *
     * @param o the object to compare with
     * @return true if the points are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        return xyz.equals(point.xyz);
    }

    /**
     * Override hashCode method for consistency with equals method.
     *
     * @return the hash code of the point
     */
    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    /**
     * Override toString method to represent the point as a string.
     *
     * @return the string representation of the point
     */
    @Override
    public String toString() {
        return "Point {" + xyz + '}';
    }

    public double getX() {
        return xyz.d1;
    }
    public double getY() {
        return xyz.d2;
    }
    public double getZ() {
        return xyz.d3;
    }
}
