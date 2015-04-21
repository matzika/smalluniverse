import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.glu.Sphere;


public class Planet extends SpaceObject{

	private float radius;
	private int id;
	private Sphere s;
	
	private List<Planet> moons = new ArrayList<Planet>();
	
	
	public Planet(float r){
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
