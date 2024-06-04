package primitives;

/**
 * Class representing a ray in three-dimensional space.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    private final Point head;

    /**
     * The direction vector of the ray (normalized).
     */
    private final Vector direction;

    /**
     * Constructor initializing the ray with a starting point and direction vector.
     *
     * @param p0 the starting point of the ray
     * @param dir the direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        // Setting the head to the specified starting point
        head = p0;
        // Normalizing and setting the direction vector
        direction = dir.normalize();
    }

    public Point getPoint(double t){
    if(Util.isZero(t))
        return this.head;

    return this.head.add(this.direction.scale(t));
    }

    /**
     * Gets the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDirection() {
        return this.direction;
    }

    /**
     * Gets the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getHead() {
        return this.head;
    }

    /**
     * Override equals method to compare rays' starting points and direction vectors.
     *
     * @param o the object to compare with
     * @return true if the rays are equal, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    /**
     * Override hashCode method for consistency with equals method.
     *
     * @return the hash code of the ray
     */
    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }



    /**
     * Override toString method to represent the ray as a string.
     *
     * @return the string representation of the ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
