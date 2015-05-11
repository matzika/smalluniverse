import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;


public class Moon extends SpaceObject{

	private Sphere s;

	private ShaderProgram moonShader;

  private Planet parent;

	public Moon(float r,float or){
		this.radius = r;
		this.orbitRadius = or;
    this.rotationAngleDelta = 0.0f;
    this.revolutionAngleDelta = -5.0f;
		s = new Sphere();
	}

	public Sphere getSphere(){
		return s;
	}

	public void draw(){
			s.setDrawStyle(GLU.GLU_FILL);
			s.setTextureFlag(true);
			this.getTexture().bind();

			float[] spec = material.getSpecular();
			float shi = material.getShininess();

			moonShader.setUniform4f("mat.specular", spec[0], spec[1], spec[2], spec[3]);
			moonShader.setUniform1f("mat.shininess", shi);
			moonShader.setUniform1i("mat.texture", 0);


      GL11.glRotatef(-this.parent.getRotationAngle(), 0, 1, 0);
			super.draw();
			s.draw(radius, 64, 64);

	}

  public void setParent(Planet p){
    this.parent = p;
  }

  public Planet getParent(){
    return this.parent;
  }

  public void setShader(ShaderProgram p){
    this.moonShader = p;
  }
}
