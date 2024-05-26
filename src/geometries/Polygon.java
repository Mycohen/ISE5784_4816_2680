package geometries;

import java.util.List;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;

/**
 * Polygon class represents a two-dimensional polygon in a 3D Cartesian coordinate
 * system.
 * <p>
 * A polygon is defined by a list of vertices that are ordered by the edge path.
 * The polygon must be convex and all vertices must lie in the same plane.
 * </p>
 *
 * <p>
 * This class ensures the polygon is convex and validates the vertices accordingly.
 * </p>
 *
 * <p>Note: The class is immutable once created.</p>
 *
 * @see Geometry
 * @see Plane
 * @see Point
 * @see Vector
 * @see Util
 *
 */
public class Polygon implements Geometry {

   /** List of polygon's vertices */
   protected final List<Point> vertices;

   /** Associated plane in which the polygon lies */
   protected final Plane plane;

   /** The size of the polygon - the number of vertices in the polygon */
   private final int size;

   /**
    * Polygon constructor based on a variable number of vertices. The vertices list must be ordered by the edge path.
    * The polygon must be convex.
    *
    * @param vertices list of vertices according to their order by the edge path
    * @throws IllegalArgumentException in any case of illegal combination of vertices:
    * <ul>
    * <li>Less than 3 vertices</li>
    * <li>Consecutive vertices are the same point</li>
    * <li>The vertices are not in the same plane</li>
    * <li>The order of vertices is not according to the edge path</li>
    * <li>Three consecutive vertices lie on the same line (180&#176; angle between two consecutive edges)</li>
    * <li>The polygon is concave (not convex)</li>
    * </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size = vertices.length;

      // Generate the plane according to the first three vertices and associate the polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon.
      plane = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // No need for more tests for a Triangle

      Vector n = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException because of Zero Vector if they are the same point.
      Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException because of Zero Vector if they connect three vertices that lie on the same line.
      // Generate the direction of the polygon according to the angle between the last and first edge being less than 180 degrees.
      // It is held by the sign of its dot product with the normal. If all the rest of the consequent edges generate the same sign, the polygon is convex.
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lie in the same plane");
         // Test the consequent edges
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }

   /**
    * Returns the normal vector to the polygon at a given point.
    *
    * @param p the point on the polygon
    * @return the normal vector to the polygon at the given point
    */
   @Override
   public Vector getNormal(Point p) {
      return plane.getNormal();
   }
}
