import org.newdawn.slick.opengl.Texture;

public class SpaceObject {

	protected float radius;
	protected float orbitRadius;

	protected int id;

	protected Material material;

	protected  float rotationVal = 0;
	protected  float revolutionAngle = 0;

	protected float pX =0f;
	protected float pY = 0f;

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
		this.draw();
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
