package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera in a 3D space.
 * Uses the Builder design pattern for construction.
 */
public class Camera implements Cloneable {

    /** The position of the camera in 3D space. */
    private Point p0;

    /** The right vector of the camera. */
    private Vector VRight;

    /** The up vector of the camera. */
    private Vector VUp;

    /** The direction vector the camera is facing. */
    private Vector VTo;

    /** The height of the view plane. */
    private double height = 0.0;

    /** The width of the view plane. */
    private double width = 0.0;

    /** The distance from the camera to the view plane. */
    private double distance = 0.0;

    /** The image writer used to output the rendered image. */
    ImageWriter imageWriter;

    /** The ray tracer used for rendering. */
    RayTracerBase rayTracer;

    /** The number of samples per pixel for anti-aliasing. */
    private int samplesPerPixel = 1;

    /** The number of threads used for multi-threaded rendering. */
    private int threadsCount = 1;

    /** The function for printing rendering progress. */
    private PrintProgress printProgress = null;

    /**
     * Constructs a Camera with default values.
     */
    private Camera() {
        p0 = Point.ZERO;
        VRight = new Vector(1, 0, 0);
        VUp = new Vector(0, 1, 0);
        VTo = new Vector(0, 0, -1);
    }

    /**
     * Creates and returns a copy of this Camera object.
     *
     * @return a clone of this Camera instance
     */
    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            return null; // This should not happen since Camera implements Cloneable
        }
    }

    /**
     * Returns a new Builder object to construct a Camera.
     *
     * @return a new Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Sets the number of samples per pixel for anti-aliasing.
     *
     * @param samplesPerPixel the number of samples per pixel
     * @return this Camera instance
     */
    public Camera setSamplesPerPixel(int samplesPerPixel) {
        this.samplesPerPixel = samplesPerPixel;
        return this;
    }

    /**
     * Sets the debug print function for rendering progress.
     *
     * @param printProgress the progress printing function
     * @return this Camera instance
     */
    public Camera setDebugPrint(PrintProgress printProgress) {
        this.printProgress = printProgress;
        return this;
    }

    /**
     * Sets the number of threads for multi-threaded rendering.
     *
     * @param threads the number of threads to use
     * @return this Camera instance
     */
    public Camera setMultithreading(int threads) {
        if (threads < 1)
            throw new IllegalArgumentException("Multithreading parameter must be 1 or higher");
        this.threadsCount = threads;
        return this;
    }

    /**
     * Constructs a ray for the given pixel coordinates (i, j).
     *
     * @param nX total number of pixels in width
     * @param nY total number of pixels in height
     * @param j  column index of the pixel (from left to right)
     * @param i  row index of the pixel (from top to bottom)
     * @return a Ray object representing the ray passing through the pixel (i, j)
     */
    public Ray constructRay(int nX, int nY, double j, double i) {
        Point pIJ = p0.add(VTo.scale(distance));
        double rX = width / nX;
        double rY = height / nY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        double yI = (i - (nY - 1) / 2.0) * rY;
        if (!isZero(xJ)) {
            pIJ = pIJ.add(VRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(VUp.scale(-yI));
        }
        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval the interval for grid lines
     * @param color    the color of the grid lines
     * @return this Camera instance
     * @throws MissingResourceException if the ImageWriter is missing
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter", "ImageWriter", "ImageWriter is missing");
        }
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * Writes the rendered image to the output using the ImageWriter.
     *
     * @throws MissingResourceException if the ImageWriter is missing
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter", "ImageWriter", "ImageWriter is missing");
        }
        imageWriter.writeToImage();
    }

    /**
     * Casts a ray through the pixel at the given coordinates (i, j) and writes the result to the image.
     *
     * @param nX total number of pixels in width
     * @param nY total number of pixels in height
     * @param j  column index of the pixel (from left to right)
     * @param i  row index of the pixel (from top to bottom)
     * @throws MissingResourceException if the RayTracer is missing
     */
    public void castRay(int nX, int nY, int j, int i) {
        if (rayTracer == null) {
            throw new MissingResourceException("RayTracer", "RayTracer", "RayTracer is missing");
        }
        Color pixelColor;
        if (samplesPerPixel <= 1) {
            double epsilon = 1e-5; // Small offset
            Ray ray = constructRay(nX, nY, j + epsilon, i + epsilon);
            pixelColor = rayTracer.traceRay(ray);
        } else {
            pixelColor = new Color(0, 0, 0);
            double subPixelSize = 1.0 / samplesPerPixel;
            for (int k = 0; k < samplesPerPixel; k++) {
                for (int l = 0; l < samplesPerPixel; l++) {
                    double offsetX = (k + Math.random()) * subPixelSize - 0.5;
                    double offsetY = (l + Math.random()) * subPixelSize - 0.5;
                    Ray ray = constructRay(nX, nY, j + offsetX, i + offsetY);
                    Color sampleColor = rayTracer.traceRay(ray);
                    pixelColor = pixelColor.add(sampleColor);
                }
            }
            pixelColor = pixelColor.scale(1.0 / (samplesPerPixel * samplesPerPixel));
        }
        imageWriter.writePixel(j, i, pixelColor);
    }

    /**
     * Renders the entire image by casting rays through each pixel and writing the results to the image.
     *
     * @return this Camera instance
     * @throws MissingResourceException if the ImageWriter or RayTracer is missing
     */
    public Camera renderImage() {
        // Check for missing resources
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer", ImageWriter.class.getName(), "");
        if (rayTracer == null)
            throw new MissingResourceException("Missing ray tracer", RayTracerBase.class.getName(), "");
        // Get the image size
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        // if printProgress is not null, print the progress
        if (printProgress != null) printProgress.print(0);
        // Counter for the number of pixels rendered, default to 0
        AtomicInteger counter = new AtomicInteger(0);
        //Loop through each pixel and cast a ray
        //The loop is parallelized if the number of threads is greater than 1
        if (threadsCount == 1) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j) {
                    castRay(nX, nY, j, i);
                    if (printProgress != null) printProgress.print(counter.incrementAndGet() / (double)(nY * nX));
                }
        }
        else
        //That means we have more than one thread
            {
                // We need to create a thread pool, that is a collection of threads that can be used to execute tasks
                //iterate over the pixels and create a new thread for each pixel
            ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
            for (int i = 0; i < nY; ++i) {
                final int ii = i;
                //execute the task in a new thread
                executor.execute(() -> {
                    for (int j = 0; j < nX; ++j) {
                        castRay(nX, nY, j, ii);
                        if (printProgress != null) printProgress.print(counter.incrementAndGet() / (double)(nY * nX));
                    }
                });
            }
            //shutdown the executor
            executor.shutdown();
            //wait for all threads to finish
            while (!executor.isTerminated()) {
                try {
                    //sleep for 100 milliseconds
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {}
            }
        }
        return this;
    }

    /**
     * Interface for printing progress during rendering.
     */
    public interface PrintProgress {
        /**
         * Prints the current rendering progress.
         *
         * @param percent the progress as a percentage (0.0 to 1.0)
         */
        void print(double percent);
    }

    /**
     * Inner Builder class for constructing Camera objects.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location of the camera
         * @return this Builder instance
         * @throws IllegalArgumentException if the location is null
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
         *
         * @param directionTo the direction the camera is facing
         * @param directionUp the up direction of the camera
         * @return this Builder instance
         * @throws IllegalArgumentException if the direction vectors are null or not orthogonal
         */
        public Builder setDirection(Vector directionTo, Vector directionUp) {
            if (directionTo == null || directionUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isZero(directionTo.dotProduct(directionUp))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.VTo = directionTo.normalize();
            camera.VUp = directionUp.normalize();
            camera.VRight = camera.VTo.crossProduct(camera.VUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return this Builder instance
         * @throws IllegalArgumentException if width or height are not positive
         */
        public Builder setVUpSize(double width, double height) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance to the view plane
         * @return this Builder instance
         * @throws IllegalArgumentException if distance is not positive
         */
        public Builder setVpDistance(double distance) {
            if (alignZero(distance) <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the image writer used to output the rendered image.
         *
         * @param imageWriter the image writer
         * @return this Builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer used for rendering.
         *
         * @param rayTracer the ray tracer
         * @return this Builder instance
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the number of samples per pixel for anti-aliasing.
         *
         * @param samplesPerPixel the number of samples per pixel
         * @return this Builder instance
         */
        public Builder setSamplesPerPixel(int samplesPerPixel) {
            camera.samplesPerPixel = samplesPerPixel;
            return this;
        }

        /**
         * Sets the number of threads for multi-threaded rendering.
         *
         * @param threads the number of threads to use
         * @return this Builder instance
         */
        public Builder setMultithreading(int threads) {
            camera.threadsCount = threads;
            return this;
        }

        /**
         * Sets the debug print function for rendering progress.
         *
         * @param printProgress the progress printing function
         * @return this Builder instance
         */
        public Builder setDebugPrint(PrintProgress printProgress) {
            camera.printProgress = printProgress;
            return this;
        }

        /**
         * Builds and returns the configured Camera instance.
         *
         * @return the constructed Camera instance
         * @throws MissingResourceException if required properties are missing
         * @throws IllegalArgumentException if invalid properties are set
         */
        public Camera build() {
            if (camera.p0 == null) {
                throw new MissingResourceException("Location", "Point", "Location point is missing");
            }
            if (camera.VTo == null || camera.VUp == null || camera.VRight == null) {
                throw new MissingResourceException("Direction vectors", "Vector", "Direction vectors are missing");
            }
            if (alignZero(camera.width) <= 0 || alignZero(camera.height) <= 0 || alignZero(camera.distance) <= 0) {
                throw new IllegalArgumentException("Width, height, and distance must be positive");
            }
            if (!isZero(camera.VRight.dotProduct(camera.VTo))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.VRight = camera.VTo.crossProduct(camera.VUp).normalize();
            return (Camera) camera.clone();
        }
    }
}
