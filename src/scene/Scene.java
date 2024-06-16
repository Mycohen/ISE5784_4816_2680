package scene;
import geometries.Geometries;
import primitives.*;
import lighting.AmbientLight;
public class Scene {
   public  String Name;
    public   Color background;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public Scene(String name) {
        Name = name;
    }
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }




}
