package geometries;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import geometries.RadialGeometry;

public class Tube implements Geometry  // extends RadialGeometry
{
    protected final Ray axis;
    public Tube(Ray axis) {
        this.axis = axis;
    }
    public Vector getNormal()
  {
      return null;
  }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }

}
