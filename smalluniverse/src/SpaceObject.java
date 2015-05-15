import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

/**
 * @class SpaceObject
 * Class that implements a space object in the universe
 * This class is extended by all other objects in the program (planets, moons, etc.)
 * 
 * @author Aikaterini (Katerina) Iliakopoulou
 * @email ai2315@columbia.edu
 *
 */
public class SpaceObject {

	//space object's radius, orbit radius and tilt
	protected float radius;
	protected float orbitRadius;
	protected float axisTilt;

	protected int id;

	//material used for object's shader
	protected Material material;

	//space object's angles with regards to rotation, revolution and tilt
	protected float rotationAngle = 0.0f;
	protected float revolutionAngle = 0.0f;
	protected float axisOffestTheta = 0.0f; //an offset theta from the y axis

	//delta value for changing rotation and revolution angles
	protected float rotationAngleDelta = 4.0f;
	protected float revolutionAngleDelta = 0.1f;

	public SpaceObject(){
		this.material = new Material();
	}
	
	/**
	 * Draws the space object based on its features 
	 */
	public void draw(){
		//Update the revolution and rotation angles
		this.revolve();
		this.rotate();

		GL11.glRotatef(revolutionAngle, 0, 1, 0);
		GL11.glTranslatef(orbitRadius, 0.0f, 0.0f);

		GL11.glRotatef(axisOffestTheta, 0, 0, 1);
		GL11.glRotatef(rotationAngle, 0, 1, 0);
		
		//this.draw();
	}
	
	/**
	 * Updates revolution angle
	 */
	public void revolve(){
		this.revolutionAngle += revolutionAngleDelta;
	}

	/**
	 * Updates rotation angle
	 */
	public void rotate(){
		this.rotationAngle += rotationAngleDelta;
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

	public float getRevolutionAngle(){
		return this.revolutionAngle;
	}


	public void setRevolutionAngle(float revolutionAngle){
		this.revolutionAngle = revolutionAngle;
	}

	public float getRotationAngle(){
		return this.rotationAngle;
	}


	public void setRotationAngle(float rotationAngle){
		this.rotationAngle = rotationAngle;
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
