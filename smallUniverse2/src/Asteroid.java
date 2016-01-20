import org.ejml.factory.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import org.newdawn.slick.opengl.Texture;

public class Asteroid{

  //This class describes an Asteroid that will be randomly spawned within the solar
  //system. The direction is a vector in which the asteroid will fly
  // Theta describes where along the circle whose radius is == spawnDistance the
  // asteroid will spawn

  //NOTE: as of now, this implementation has Asteroids flying towards the sun
  // everytime

  private float[] direction; //currently unused
  private float theta;
  private float[] worldCoordinates;
  private float spawnDistance;
  private float currentDistance;
  private float velocity = 6.0f;
  private float radius;
  private Sphere s;
  private Material m;

  private ShaderProgram sp;


  public Asteroid(float spawn, float r){
    theta = (float) Math.random()*360;
    spawnDistance = spawn;
    currentDistance = spawnDistance;
    worldCoordinates = new float[]{0.0f, 0.0f, 0.0f};
    updateWorldCoordinates();
    radius = r;
    s = new Sphere();
    m = new Material();
  }

  public void draw(){
    GL11.glPushMatrix();
      GL11.glRotatef(theta, 0, 1, 0);
      GL11.glTranslatef(currentDistance, 0.0f, 0.0f);

      float[] spec = m.getSpecular();
			float shi = m.getShininess();
      sp.setUniform4f("mat.specular", spec[0], spec[1], spec[2], spec[3]);
      sp.setUniform1f("mat.shininess", shi);
      sp.setUniform1i("mat.texture", 0);

      s.setDrawStyle(GLU.GLU_FILL);
			s.setTextureFlag(true);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
      this.m.getTexture().bind();

      s.draw(radius, 64, 64);

    GL11.glPopMatrix();

    currentDistance -= velocity;
    updateWorldCoordinates();
  }

  public void setShader(ShaderProgram p){
    this.sp = p;
  }

  public void setTexture(Texture t){
    this.m.setTexture(t);
  }

  public void updateWorldCoordinates(){
    double[][] posA = new double[][]{
      {0},
      {0},
      {0},
      {1}
    };
    SimpleMatrix pos = new SimpleMatrix(posA);

    double[][] transA = new double[][]{
      {1, 0, 0, currentDistance},
      {0, 1, 0, 0},
      {0, 0, 1, 0},
      {0, 0, 0, 1}
    };
    SimpleMatrix trans = new SimpleMatrix(transA);

    double thetaRad = Math.toRadians(theta);
		double[][] rotYA = new double[][]{
			{Math.cos(thetaRad), 0, Math.sin(thetaRad), 0},
      {0,1,0,0},
      {-Math.sin(thetaRad), 0, Math.cos(thetaRad), 0},
      {0,0,0,1}
		};
    SimpleMatrix rot = new SimpleMatrix(rotYA);

    SimpleMatrix resultT = rot.mult(trans.mult(pos));

    worldCoordinates = new float[]{ (float) resultT.get(0), (float) resultT.get(1), (float) resultT.get(2)};

  }

  public void setTheta(float t){
    this.theta = t;
  }

  public float getTheta(){
    return this.theta;
  }

  public void setDirection(float[] d){
    this.direction = d;
  }

  public float[] getDirection(){
    return this.direction;
  }

  public void setSpawnDistance(float s){
    this.spawnDistance = s;
  }

  public float getSpawnDistance(){
    return this.spawnDistance;
  }
}
