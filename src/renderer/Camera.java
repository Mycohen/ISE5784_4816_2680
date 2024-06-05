package renderer;

import primitives.*;

/**
 * Represents a camera in a 3D space.
 * Uses the Builder design pattern.
 */
public class Camera implements Cloneable {


    // Private fields
    private Point p0;
    private Vector VRight;
    private Vector VUp;
    private Vector VTo;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;

    private Camera() {
        p0 = Point.ZERO;
        VRight = new Vector(1, 0, 0);
        VUp = new Vector(0, 1, 0);
        VTo = new Vector(0, 0, -1);
    }

    /**
     * Returns a new Builder object to construct a Camera.
     * @return a new Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray for the given pixel coordinates (i, j).
     * @param nX total number of pixels in width
     * @param nY total number of pixels in height
     * @param j column index of the pixel (from left to right)
     * @param i row index of the pixel (from top to bottom)
     * @return a Ray object representing the ray passing through the pixel (i, j)
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pIJ = p0.add(VTo.scale(distance));
        double rX = width / nX;
        double rY = height / nY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        double yI = (i - (nY - 1) / 2.0) * rY;
        if (!Util.isZero(xJ)) {
            pIJ = pIJ.add(VRight.scale(xJ));
        }
        if (!Util.isZero(yI)) {
            pIJ = pIJ.add(VUp.scale(-yI));
        }
        return new Ray(p0, pIJ.subtract(p0));
    }

//    // Clone method overridden from Cloneable interface
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone(); // Default clone method is used
//    }

    // Inner Builder class for constructing Camera objects
    public static class Builder {

        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         * @param location the location point
         * @return the Builder instance
         * @throws IllegalArgumentException if the location point is null
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.p0 = location;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         * The second vector passed will be adjusted to ensure orthogonality and normalization.
         *
         * @param directionTo the direction vector towards which the camera is facing
         * @param directionUp the up direction vector
         * @return the Builder instance
         * @throws IllegalArgumentException if direction vectors are not orthogonal or cannot be normalized
         */
        public Builder setDirectionVectors(Vector directionTo, Vector directionUp) {
            if (directionTo == null || directionUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            camera.VTo = directionTo.normalize();
            camera.VUp = directionUp.subtract(camera.VTo.scale(directionUp.dotProduct(camera.VTo))).normalize();
            camera.VRight = camera.VTo.crossProduct(camera.VUp).normalize();
            return this;
        }

        /**
         * Sets the size of the viewport (screen plane).
         *
         * @param width  the width of the viewport
         * @param height the height of the viewport
         * @return the Builder instance
         * @throws IllegalArgumentException if width or height are non-positive
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the viewport (screen plane).
         *
         * @param distance the distance from the camera to the viewport
         * @return the Builder instance
         * @throws IllegalArgumentException if distance is non-positive
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera object.
         * @return the constructed Camera object
         */
        public Camera build() {
            // Additional validation or adjustments can be added here if needed
            return camera;
        }
    }

    // Getters for private fields
    public Point getLocation() {
        return p0;
    }

    public Vector getVRight() {
        return VRight;
    }

    public Vector getVUp() {
        return VUp;
    }

    public Vector getVTo() {
        return VTo;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }
}
