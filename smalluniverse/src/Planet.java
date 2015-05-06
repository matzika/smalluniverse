import java.util.ArrayList;
import java.util.List;

import org.lwjgl.examples.spaceinvaders.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Planet extends SpaceObject{

	private float radius;
	private float orbitRadius;
	private int id;
	private Sphere s;
	private Material material;

	private List<Planet> moons = new ArrayList<Planet>();


	public Planet(float r,float or){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		this.material = new Material();
	}

	public Planet(float r, float or, Material mat){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		this.material = mat;
	}

	public Material getMaterial(){
		return this.material;
	}

	public void setMaterial(Material mat){
		this.material = mat;
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public void draw(Texture texture){
		/*s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
		s.setNormals(GLU.GLU_SMOOTH);
		int textId = GL11.glGenLists(1);
		
		GL11.glNewList(textId, GL11.GL_COMPILE);
		texture.bind();*/
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
