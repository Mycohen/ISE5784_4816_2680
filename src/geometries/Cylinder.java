package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder  implements Geometry{
    final private double height;
    public Cylinder (double he)
    {
        this.height=he;
    }


    public Vector getNormal(Point p) {
        return null;
    }
    public Vector getNormal ()
    {
        return null;
    }
}
