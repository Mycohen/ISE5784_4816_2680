package geometries;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

public class Tube implements Geometry{
    protected final Ray axis;

    public Tube(Ray axis) {
        this.axis = axis;
    }

  public Vector getNormal()
  {
      return null;
  }
    public Vector getNormal(Point p) {
        return null;
    }

}
