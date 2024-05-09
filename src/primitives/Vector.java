package primitives;
import static primitives.Util.alignZero;


public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is illegal");
    }

    @Override
    public String toString() {
        return "Vector{" + xyz + "}";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }
    //normalize of the vector
    public Vector normalize(){
          double length = alignZero(length());
          if(length == 0)
              throw new ArithmeticException("Cannot normalize vector(0,0,0)");
            return new Vector(xyz.scale(1/length));
    }
    //cross product of two vectors
    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }
    //dot product of two vectors
    public double dotProduct(Vector vector) {
        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;
    }
}
