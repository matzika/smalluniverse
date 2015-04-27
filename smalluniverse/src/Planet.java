import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.glu.Sphere;


public class Planet extends SpaceObject{

	private float radius;
	private float orbitRadius;
	private int id;
	private Sphere s;

	private List<Planet> moons = new ArrayList<Planet>();


	public Planet(float r,float or){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public void draw(){
		s.draw(radius, 64, 64);
	}

	public float getRadius(){
		return this.radius;
	}

	public void setRadius(float radius){
		this.radius = radius;
	}

	public float getOrbitRadius(){
		return this.orbitRadius;
	}

	public void setOrbitRadius(float orbitRadius){
		this.orbitRadius = orbitRadius;
	}
}
