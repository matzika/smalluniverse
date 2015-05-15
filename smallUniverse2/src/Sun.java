import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import org.ejml.factory.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;

import org.newdawn.slick.opengl.Texture;

public class Sun extends SpaceObject{
	private Sphere s;
	private Light light;
	private float[] color; // for shader
	private ShaderProgram sunShader;
	private Texture channel0;
	private Texture channel1;

	public Sun(float r){
		this.radius = r;
		s = new Sphere();
		light = new Light();
		light.setDiffuse(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
		light.setSpecular(new float[]{0.8f, 0.8f, 0.8f, 1.0f});
		this.revolutionAngleDelta = 0.0f;
		this.rotationAngleDelta = 0.0f;

		this.orbitRadius = 0.0f;

		color = new float[]{1.0f, .5f, 0.0f};
	}

	public void draw(){

		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);

		if(this.getTexture() == null)
			System.out.println("TEXURE NULL");
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		channel0.bind();
		// GL13.glActiveTexture(GL13.GL_TEXTURE1);
		// channel1.bind();

		super.draw();
		s.draw(radius, 64, 64);
	}

	public float[] getColor(){
		return this.color;
	}

	public void setChannel0(Texture t){
		this.channel0 = t;
	}

	public void setChannel1(Texture t){
		this.channel1 = t;
	}

	public Light getLight(){
		return this.light;
	}

	public void setLight(Light light){
		this.light = light;
	}

	public void setSunlightDiffuse(float[] d){
		this.light.setDiffuse(d);
	}

	public void setSunlighSpecular(float[] s){
		this.light.setSpecular(s);
	}

	public void revolve(){
		super.revolve();
		updateLightLocation();
	}

	public void rotate(){
		super.rotate();
		updateLightLocation();
	}

	public void updateLightOnCamera(Vector3f pos){
		float[] lpos = light.getLocation();
		double[][] posA = new double[][]{
			{(double) lpos[0]},
			{(double) lpos[1]},
			{(double) lpos[2]},
			{1}
		};
		SimpleMatrix posL = new SimpleMatrix(posA);

		double[][] tranA = new double[][]{
			{1,0,0, -pos.x},
			{0,1,0, -pos.y},
			{0,0,1, -pos.z},
			{0,0,0,1}
		};
		SimpleMatrix tran = new SimpleMatrix(tranA);


		SimpleMatrix resultT = tran.mult(posL);

		light.setWorldLocation(new float[]{(float) resultT.get(0), (float) resultT.get(1), (float) resultT.get(2)});

	}

	public void updateLightOnCamera(Vector3f pos, Vector3f rot){
		float[] lpos = light.getLocation();
		double[][] posA = new double[][]{
			{(double) lpos[0]},
			{(double) lpos[1]},
			{(double) lpos[2]},
			{1}
		};
		SimpleMatrix posL = new SimpleMatrix(posA);

		double thetaRad = Math.toRadians(rot.x);
		double[][] rotXA = new double[][]{
			{1,0,0,0},
      {0,Math.cos(thetaRad), -Math.sin(thetaRad), 0},
      {0,Math.sin(thetaRad), Math.cos(thetaRad), 0},
      {0,0,0,1}
		};
		SimpleMatrix rotX = new SimpleMatrix(rotXA);

		thetaRad = Math.toRadians(rot.y);
		double[][] rotYA = new double[][]{
			{Math.cos(thetaRad), 0, Math.sin(thetaRad), 0},
      {0,1,0,0},
      {-Math.sin(thetaRad), 0, Math.cos(thetaRad), 0},
      {0,0,0,1}
		};
		SimpleMatrix rotY = new SimpleMatrix(rotYA);

		thetaRad = Math.toRadians(rot.z);
		double[][] rotZA = new double[][]{
			{Math.cos(thetaRad), -Math.sin(thetaRad), 0, 0},
      {Math.sin(thetaRad), Math.cos(thetaRad), 0, 0},
      {0,0,1,0},
      {0,0,0,1}
		};
		SimpleMatrix rotZ = new SimpleMatrix(rotZA);

		double[][] tranA = new double[][]{
			{1,0,0, -pos.x},
			{0,1,0, -pos.y},
			{0,0,1, -pos.z},
			{0,0,0,1}
		};
		SimpleMatrix tran = new SimpleMatrix(tranA);


		SimpleMatrix resultT = rotZ.mult(rotY.mult(rotX.mult(tran.mult(posL))));

		light.setWorldLocation(new float[]{(float) resultT.get(0), (float) resultT.get(1), (float) resultT.get(2)});
	}

	private void updateLightLocation(){
		float[] lpos = light.getLocation();
		//get position
		double[][] posA = new double[][]{
			{(double) lpos[0]},
			{(double) lpos[1]},
			{(double) lpos[2]},
			{1}
		};
		SimpleMatrix pos = new SimpleMatrix(posA);

		//translate by orbitRadius
		double[][] torA = new double[][]{
			{1,0,0,orbitRadius},
			{0,1,0,0},
			{0,0,1,0},
			{0,0,0,1},
		};
		SimpleMatrix tor = new SimpleMatrix(torA);

		//revolve by revolution theta
		double thetaRad = Math.toRadians(revolutionAngle);
		double[][] revA = new double[][]{
			{Math.cos(thetaRad), 0, Math.sin(thetaRad), 0},
      {0,1,0,0},
      {-Math.sin(thetaRad), 0, Math.cos(thetaRad), 0},
      {0,0,0,1}
		};
		SimpleMatrix rev = new SimpleMatrix(revA);

		SimpleMatrix resultT = rev.mult(tor.mult(pos));

		light.setLocation(new float[]{(float) resultT.get(0), (float) resultT.get(1), (float) resultT.get(2)});


	}
}
