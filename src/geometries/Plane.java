package geometries;
import primitives.Point;
import primitives.Vector;
public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;
public Plane(Point x, Point y, Point z)
{
    q=x;
    Vector v1= x.subtract(y);
    Vector v2= x.subtract(z);
    this.normal=v1.crossProduct(v2);

}
public Plane (Point p, Vector vNormal)
{
    this.q=p;
    this.normal=vNormal;
}

public Vector getNormal()
{
    return this.normal.normalize();
}
public Vector getNormal(Point p)
{
    return this.normal.normalize();
}

}
