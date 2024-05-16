package geometries;
import primitives.Vector;
import primitives.Point;

public class Sphere implements Geometry{
    private final Point center;
    public Sphere (Point myCenter)
    {
        this.center=myCenter;
    }
    public Vector getNormal()
    {
      return null;
    }
    public Vector getNormal (Point p)
    {
        return null;
    }


}
