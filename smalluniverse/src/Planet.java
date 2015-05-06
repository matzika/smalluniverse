import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Planet extends SpaceObject{

	private float radius;
	private float orbitRadius;
	private int id;
	private Sphere s;
	private Material material;
	private  float rotationVal = 0;
	private  float revolutionAngle = 0;
	private float pX =0f;
	private float pY = 0f;
	private Texture t;

	private List<Planet> moons = new ArrayList<Planet>();


	public Planet(float r,float or, Texture texture){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		this.material = new Material();
		t= texture;
	}

	public Planet(float r, float or, Material mat){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		this.material = mat;
	}
	
	public Sphere getSphere(){
		return s;
	}

	public Material getMaterial(){
		return this.material;
	}

	public void setMaterial(Material mat){
		this.material = mat;
	}
	
	public float getPX(){
		return pX;
	}

	public float getPY(){
		return pY;
	}
	
	
	public void setPX(float x){
		pX = x;
	}

	public void setPY(float y){
		pY = y;
	}
	
	
	public float getRevolutionAngle(){
		return this.revolutionAngle;
	}
	
	
	public void setRevolutionAngle(float x){
		revolutionAngle = x;
	}
	
	public float getRotationAngle(){
		return this.rotationVal;
	}
	
	
	public void setRotationAngle(float x){
		rotationVal = x;
	}
	
	
	
	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public void draw(){
		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
//		s.setNormals(GLU.GLU_SMOOTH);
////		int textId = GL11.glGenLists(1);
////		
////		GL11.glNewList(textId, GL11.GL_COMPILE);
		t.bind();
		s.draw(radius, 64, 64);
	}
	
	public float getRadius(){
		return this.radius;
	}

	public void setRadius(float radius){
		this.radius = radius;
	}
	
	public Texture getTexutre(){
		return t;
	}

	public void setTexture(Texture texture){
		t = texture;
	}

	public float getOrbitRadius(){
		return this.orbitRadius;
	}

	public void setOrbitRadius(float orbitRadius){
		this.orbitRadius = orbitRadius;
	}
}
