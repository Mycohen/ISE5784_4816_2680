package primitives;

import static primitives.Util.alignZero;

/**
 * Class representing a vector in three-dimensional space, inheriting from Point.
 */
public class Vector extends Point {

    /**
     * Constructor initializing the vector with specified coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }

    /**
     * Constructor initializing the vector with provided Double3 object.
     *
     * @param xyz the coordinates of the vector as a Double3 object
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }


    /**
     * Method to add another vector to this vector.
     *
     * @param vector the vector to add
     * @return a new vector resulting from the addition
     */
    public Vector add(Vector vector) {
        //I handle the case of the answer being the zero vector
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Method to scale this vector by a scalar.
     *
     * @param scalar the scalar to scale the vector by
     * @return a new scaled vector
     */
    public Vector scale(double scalar) {
        if(scalar==0)
        {
            throw new IllegalArgumentException("Scalar can't be 0");
        }
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Method to calculate the squared length of this vector.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared()
    {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Method to calculate the length of this vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Method to normalize this vector (make its length 1).
     *
     * @return a new normalized vector
     * @throws ArithmeticException if the vector is the zero vector
     */
    public Vector normalize(){
        double length = alignZero(length());
        if(length == 0)
            throw new ArithmeticException("Cannot normalize vector(0,0,0)");
        return new Vector(xyz.scale(1/length));
    }

    /**
     * Method to calculate the cross product of this vector and another vector.
     *
     * @param vector the vector to cross with
     * @return a new vector representing the cross product
     */
    public Vector crossProduct(Vector vector) {
        // Check if the vectors are parallel
        if (isParallel(vector)) {
            throw new IllegalArgumentException("Cross Product of parallel vectors is illegal");
        }
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }

    /**
     * Method to calculate the dot product of this vector and another vector.
     *
     * @param vector the vector to dot with
     * @return the dot product
     */
    public double dotProduct(Vector vector) {

        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;
    }

   //from here help functions
    private boolean isParallel(Vector vector) {
        // Calculate the ratios and compare them
        double ratio1 = xyz.d1 / vector.xyz.d1;
        double ratio2 = xyz.d2 / vector.xyz.d2;
        double ratio3 = xyz.d3 / vector.xyz.d3;

        // Handle cases where the components might be zero to avoid division by zero
        boolean ratio1IsValid = !Double.isInfinite(ratio1) && !Double.isNaN(ratio1);
        boolean ratio2IsValid = !Double.isInfinite(ratio2) && !Double.isNaN(ratio2);
        boolean ratio3IsValid = !Double.isInfinite(ratio3) && !Double.isNaN(ratio3);

        // Check if all valid ratios are equal
        if (ratio1IsValid && ratio2IsValid && ratio3IsValid) {
            return ratio1 == ratio2 && ratio2 == ratio3;
        }
        return false;
    }
    /**
     * Override toString method to represent the vector as a string.
     *
     * @return the string representation of the vector
     */
    @Override
    public String toString() {
        return "Vector{" + xyz + "}";
    }

    /**
     * Override equals method to compare vectors' coordinates for equality.
     *
     * @param o the object to compare with
     * @return true if the vectors are equal, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

}
