import org.lwjgl.util.glu.Sphere;


public class Sun extends SpaceObject{
	private Sphere s;
	
	private int id;
	private float radius;
	
	public Sun(float r){
		this.radius = r;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
