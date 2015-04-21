import org.lwjgl.util.glu.Sphere;


public class Sun extends SpaceObject{
	private Sphere s;
	
	private int id;
	private float radius;
	
	public Sun(float r){
		this.radius = r;
		s = new Sphere();
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void draw(){
		s.draw(radius, 32, 32);
	}
}
