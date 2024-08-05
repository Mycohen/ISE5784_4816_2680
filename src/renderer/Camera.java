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
    ImageWriter imageWriter;
    RayTracerBase rayTracer;
    private int samplesPerPixel = 1;
    private int threadsCount = 1;
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
            Ray ray = constructRay(nX, nY, j, i);
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
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer", ImageWriter.class.getName(), "");
        if (rayTracer == null)
            throw new MissingResourceException("Missing ray tracer", RayTracerBase.class.getName(), "");

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (printProgress != null) printProgress.print(0);
        AtomicInteger counter = new AtomicInteger(0);

        if (threadsCount == 1) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j) {
                    castRay(nX, nY, j, i);
                    if (printProgress != null) printProgress.print(counter.incrementAndGet() / (double)(nY * nX));
                }
        } else {
            ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
            for (int i = 0; i < nY; ++i) {
                final int ii = i;
                executor.execute(() -> {
                    for (int j = 0; j < nX; ++j) {
                        castRay(nX, nY, j, ii);
                        if (printProgress != null) printProgress.print(counter.incrementAndGet() / (double)(nY * nX));
                    }
                });
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
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
        void print(double percent);
    }

    /**
     * Inner Builder class for constructing Camera objects.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.p0 = location;
            return this;
        }

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

        public Builder setVUpSize(double width, double height) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        public Builder setVpDistance(double distance) {
            if (alignZero(distance) <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        public Builder setSamplesPerPixel(int samplesPerPixel) {
            camera.samplesPerPixel = samplesPerPixel;
            return this;
        }

        public Builder setMultithreading(int threads) {
            camera.threadsCount = threads;
            return this;
        }

        public Builder setDebugPrint(PrintProgress printProgress) {
            camera.printProgress = printProgress;
            return this;
        }


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