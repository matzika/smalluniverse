import org.newdawn.slick.opengl.Texture;
import org.lwjgl.opengl.GL11;

public class SpaceObject {

	protected float radius;
	protected float orbitRadius;

	protected int id;

	protected Material material;

	protected float rotationAngle = 0.0f;
	protected float revolutionAngle = 0.0f;
	protected float axisOffestTheta = 0.0f; //an offset theta from the y axis

	protected float rotationAngleDelta = 4.0f;
	protected float revolutionAngleDelta = 0.1f;

	public SpaceObject(){
		this.material = new Material();
	}

	public Material getMaterial(){
		return this.material;
	}

	public void setMaterial(Material mat){
		this.material = mat;
	}

	public Texture getTexture(){
		return this.material.getTexture();
	}

	public void setTexture(Texture texture){
		this.material.setTexture(texture);
	}

	public void draw(){
		//Update the revolution and rotation angles
		this.revolve();
		this.rotate();

		GL11.glRotatef(revolutionAngle, 0, 1, 0);
		GL11.glTranslatef(orbitRadius, 0.0f, 0.0f);

		GL11.glRotatef(axisOffestTheta, 0, 0, 1);
		GL11.glRotatef(rotationAngle, 0, 1, 0);


	}

	public float getRevolutionDelta(){
		return this.revolutionAngleDelta;
	}

	public void setRevolutionDelta(float d){
		this.revolutionAngleDelta = d;
	}

	public float getRotationDelta(){
		return this.rotationAngleDelta;
	}

	public void setRotationDelta(float d){
		this.rotationAngleDelta = d;
	}

	public void revolve(){
		this.revolutionAngle += revolutionAngleDelta;
	}

	public void rotate(){
		this.rotationAngle += rotationAngleDelta;
	}

	public float getRevolutionAngle(){
		return this.revolutionAngle;
	}


	public void setRevolutionAngle(float x){
		revolutionAngle = x;
	}

	public float getRotationAngle(){
		return this.rotationAngle;
	}


	public void setRotationAngle(float x){
		rotationAngle = x;
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

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

}
