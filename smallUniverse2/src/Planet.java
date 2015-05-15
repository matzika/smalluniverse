import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;


public class Planet extends SpaceObject{

	private Sphere s;

	private Rings rings;

	private List<Moon> moons = new ArrayList<Moon>();

	private ShaderProgram planetShader;

	public Planet(float r,float or){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
	}

	public Planet(float r,float or,List<Float []> rspecs, List<Float []> cspecs){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		rings = new Rings(rspecs, cspecs);
	}

	public Sphere getSphere(){
		return s;
	}

	public void draw(){
		GL11.glPushMatrix();
			s.setDrawStyle(GLU.GLU_FILL);
			s.setTextureFlag(true);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			this.getTexture().bind();

			super.draw();

			float[] spec = material.getSpecular();
			float shi = material.getShininess();

			planetShader.setUniform4f("mat.specular", spec[0], spec[1], spec[2], spec[3]);
			planetShader.setUniform1f("mat.shininess", shi);
			planetShader.setUniform1i("mat.texture", 0);

			s.draw(radius, 64, 64);

			if(this.rings != null){
				planetShader.setUniform1f("isRing", 1.0f);
				this.rings.draw();
				planetShader.setUniform1f("isRing", 0.0f);
			}

			for(Moon m : moons){
				GL11.glPushMatrix();
				m.draw();
				GL11.glPopMatrix();
			}
		GL11.glPopMatrix();


	}

	public void setShader(ShaderProgram p){
		this.planetShader = p;
	}

	public void setRingsTexture(Texture ringsTexture){
		rings.setTexture(ringsTexture);
	}

	public void addMoons(List<Moon> moons){
		for(Moon m: moons){
			m.setParent(this);
		}
		this.moons.addAll(moons);
	}

	public List<Moon> getMoons(){
		return moons;
	}

	public void addMoon(float r,float or, Texture moonText){
		Moon moon = new Moon(r,or);
		moon.setTexture(moonText);
		moon.setParent(this);
		moons.add(moon);
	}

	public class Rings{
		private float innerRadius, outerRadius;

		private Texture texture;

		private Disk d;

		private List<Float []> rspecs;
		private List<Float []> cspecs;

		public Rings(float innerRadius, float outerRadius){
			this.innerRadius = innerRadius;
			this.outerRadius = outerRadius;

			d = new Disk();
		}

		public Rings(List<Float []> rspecs, List<Float []> cspecs){
			this.rspecs = rspecs;
			this.cspecs = cspecs;
		}

		public void setTexture(Texture texture){
			this.texture = texture;
		}


		public void draw(){
			for(int i=0;i<rspecs.size();i++){
				GL11.glPushMatrix();
				{
					Disk d = new Disk();
					GL11.glColor3f(cspecs.get(i)[0], cspecs.get(i)[1], cspecs.get(i)[2]);
					d.draw(rspecs.get(i)[0], rspecs.get(i)[1], 64, 64);
				}
				GL11.glPopMatrix();
			}

		}
	}
}
