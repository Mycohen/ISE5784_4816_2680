package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * Interface for geometric shapes in a 3D space.
 * <p>
 * This interface provides a method to calculate the normal vector to the shape at a given point.
 * Implementing classes should provide the logic to calculate the normal vector specific to the shape.
 * </p>
 *
 * @see Vector
 * @see Point
 */
public interface Geometry {

   /**
    * Calculates the normal vector to the geometry at a given point.
    *
    * @param p the point on the geometry where the normal is to be calculated
    * @return the normal vector to the geometry at the specified point
    */
   public abstract Vector getNormal(Point p);
}
