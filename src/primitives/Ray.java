package primitives;

public class Ray {
    private final Double3 head;
    private final Vector direction;

    public Ray(Double3 p0, Vector dir) {
        head = p0;
        direction = dir.normalize();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
