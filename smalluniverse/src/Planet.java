import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;

/**
 * @class Planet
 * Implements a planet object in the universe
 *
 * @author Aikaterini (Katerina) Iliakopoulou
 * @email ai2315@columbia.edu
 * @author Shloka Kini
 * @email srk2169@columbia.edu
 *
 */
public class Planet extends SpaceObject{

	private Sphere s;

	private Rings rings;

	private ShaderProgram planetShader;

	private List<Moon> moons = new ArrayList<Moon>();

	/**
	 * Planet constructor for simple planet (basic features: planet's radius, planet's orbit radius and tilt.)
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 */
	public Planet(float radius,float orbitRadius, float axisTilt, float speed){
		this.radius = radius;
		this.orbitRadius = orbitRadius;
		this.axisTilt = axisTilt;
		this.speed = speed;
		s = new Sphere();

	}

	/**
	 * Planet constructor for planet with rings (basic features: planet's radius, planet's orbit radius and tilt.)
	 * (extra features: rings specs)
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 * @param rspecs
	 * @param cspecs
	 */
	public Planet(float radius,float orbitRadius, float axisTilt,List<Float []> rspecs, List<Float []> cspecs, float speed){
		this.radius = radius;
		this.orbitRadius = orbitRadius;
		this.axisTilt = axisTilt;
		this.speed = speed;

		s = new Sphere();
		rings = new Rings(rspecs, cspecs);
	}

	/**
	 * Draws the planets based on its features, texture and shader
	 */
	public void draw(){

		GL11.glPushMatrix();
		{
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

			for(Moon m : moons){
				GL11.glPushMatrix();
				m.draw();
				GL11.glPopMatrix();
			}
		
			if(this.rings != null){
				planetShader.setUniform1f("isRing", 1.0f);
				GL11.glRotatef(-this.rotationAngle, 0, 1, 0);
				this.rings.draw();
				planetShader.setUniform1f("isRing", 0.0f);
			}
		}
		GL11.glPopMatrix();
		
	}

	public Sphere getSphere(){
		return s;
	}



	public void addMoons(List<Moon> moons){
		this.moons.addAll(moons);
	}

	public List<Moon> getMoons(){
		return moons;
	}

	public void addMoon(float radius,float orbitRadius, float axisTilt,Texture moonText, float speed){
		Moon moon = new Moon(radius,orbitRadius, axisTilt, speed);
		moon.setTexture(moonText);
		moons.add(moon);
	}

	public void setShader(ShaderProgram p){
		this.planetShader = p;
	}

	public Rings getRings(){
		return this.rings;
	}

	/**
	 * @class Rings
	 * Implements the rings around a planet
	 *
	 * @author Aikaterini (Katerina) Iliakopoulou
	 * @email ai2315@columbia.edu
	 *
	 */
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

		public void setRSpecs(List<Float []>  rspecs){
			this.rspecs = rspecs;
		}

		public List<Float []> getRSpecs(){
			return rspecs;
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
