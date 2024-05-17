package geometries;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

public class Cylinder implements Geometry// extends Tube
{
    private final double height;
    public Cylinder(double height) {this.height=height;}
    @Override
    public Vector getNormal(Point p) {return null;}
    public Vector getNormal() {return null;}
}
