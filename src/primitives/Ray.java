package primitives;

// Class representing a ray in three-dimensional space
public class Ray {

    // The starting point of the ray
    private final Double3 head;

    // The direction vector of the ray (normalized)
    private final Vector direction;

    // Constructor initializing the ray with a starting point and direction vector
    public Ray(Double3 p0, Vector dir) {
        // Setting the head to the specified starting point
        head = p0;
        // Normalizing and setting the direction vector
        direction = dir.normalize();
    }

    // Override equals method to compare rays' starting points and direction vectors
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    // Override hashCode method for consistency with equals method
    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    // Override toString method to represent the ray as a string
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
