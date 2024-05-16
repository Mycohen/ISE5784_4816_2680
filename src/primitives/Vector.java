package primitives;

import static primitives.Util.alignZero;

// Class representing a vector in three-dimensional space, inheriting from Point
public class Vector extends Point {

    // Constructor initializing the vector with specified coordinates
    public Vector(double x, double y, double z) {
        // Call the superclass constructor to set the coordinates
        super(x, y, z);
        // Check if the vector is the zero vector, which is not allowed
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }

    // Constructor initializing the vector with provided Double3 object
    public Vector(Double3 xyz) {
        // Call the superclass constructor to set the coordinates
        super(xyz);
        // Check if the vector is the zero vector, which is not allowed
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }

    // Override toString method to represent the vector as a string
    @Override
    public String toString() {
        return "Vector{" + xyz + "}";
    }

    // Override equals method to compare vectors' coordinates for equality
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

    // Method to add another vector to this vector
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    // Method to scale this vector by a scalar
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    // Method to calculate the squared length of this vector
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    // Method to calculate the length of this vector
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    // Method to normalize this vector (make its length 1)
    // Throws an exception if the vector is the zero vector
    public Vector normalize(){
        double length = alignZero(length());
        if(length == 0)
            throw new ArithmeticException("Cannot normalize vector(0,0,0)");
        return new Vector(xyz.scale(1/length));
    }

    // Method to calculate the cross product of this vector and another vector
    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }

    // Method to calculate the dot product of this vector and another vector
    public double dotProduct(Vector vector) {
        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;
    }
}
