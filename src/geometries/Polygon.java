package geometries;

import java.util.List;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
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
 * @autor Moshe Yaakov Cohen
 * @autor Eliaou Kopinski
 *
 * @see Geometry
 * @see Plane
 * @see Point
 * @see Vector
 */
public class Polygon extends Geometry {

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

      // Generate the plane according to the first three vertices
      plane = new Plane(vertices[0], vertices[1], vertices[2]);

      // Check that the other vertices lie in the same plane
      Vector n = plane.getNormal();
      for (int i = 3; i < vertices.length; ++i)
         if (!isZero(n.dotProduct(vertices[i].subtract(vertices[0]))))
            throw new IllegalArgumentException("All vertices of a polygon must lie in the same plane");

      // Check that the polygon is convex
      if (!isConvex())
         throw new IllegalArgumentException("The polygon must be convex");

      // Calculate and set the bounding box
      double minX = Double.POSITIVE_INFINITY;
      double minY = Double.POSITIVE_INFINITY;
      double minZ = Double.POSITIVE_INFINITY;
      double maxX = Double.NEGATIVE_INFINITY;
      double maxY = Double.NEGATIVE_INFINITY;
      double maxZ = Double.NEGATIVE_INFINITY;

      for (Point vertex : vertices) {
         minX = Math.min(minX, vertex.getX());
         minY = Math.min(minY, vertex.getY());
         minZ = Math.min(minZ, vertex.getZ());
         maxX = Math.max(maxX, vertex.getX());
         maxY = Math.max(maxY, vertex.getY());
         maxZ = Math.max(maxZ, vertex.getZ());
      }

      this.boundingBox = new BoundingBox(
              new Point(minX, minY, minZ),
              new Point(maxX, maxY, maxZ)
      );
   }

   private boolean isConvex() {
      // Implementation of convexity check
      // This is a simplified version, you might want to implement a more robust check
      Vector v1 = vertices.get(1).subtract(vertices.get(0));
      Vector v2 = vertices.get(2).subtract(vertices.get(1));
      Vector normal = v1.crossProduct(v2);

      for (int i = 3; i < vertices.size(); i++) {
         Vector vi = vertices.get(i).subtract(vertices.get(i - 1));
         if (normal.dotProduct(v2.crossProduct(vi)) <= 0)
            return false;
         v2 = vi;
      }

      Vector v0 = vertices.get(0).subtract(vertices.get(vertices.size() - 1));
      return normal.dotProduct(v2.crossProduct(v0)) > 0;
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

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

      if (boundingBox != null && !boundingBox.hasIntersection(ray)) {
         return null;  // No intersection with the bounding box, so no need to check further
      }
      // First, find intersections with the plane containing the polygon
      List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);

      if (planeIntersections == null) {
         return null;  // No intersections with the plane
      }

      Point intersectionPoint = planeIntersections.get(0).point;

      // Check if the intersection point is inside the polygon
      Vector v1 = vertices.get(1).subtract(vertices.get(0));
      Vector v2 = intersectionPoint.subtract(vertices.get(0));

      double sign = v1.crossProduct(v2).dotProduct(plane.getNormal());

      for (int i = 1; i < vertices.size(); i++) {
         v1 = vertices.get((i + 1) % vertices.size()).subtract(vertices.get(i));
         v2 = intersectionPoint.subtract(vertices.get(i));

         if (sign * v1.crossProduct(v2).dotProduct(plane.getNormal()) <= 0) {
            return null;  // Point is outside
         }
      }

      // The point is inside the polygon
      return List.of(new GeoPoint(this, intersectionPoint));
   }

}
