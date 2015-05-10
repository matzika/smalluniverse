import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;


public class Planet extends SpaceObject{

	private Sphere s;
	
	private Rings rings;
	private float axisTilt;
	
	private List<Planet> moons = new ArrayList<Planet>();

	public Planet(float r,float or, float at){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		axisTilt=at;
	}
	
	public Planet(float r,float or,List<Float []> rspecs, List<Float []> cspecs, float at){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();
		rings = new Rings(rspecs, cspecs);
		axisTilt = at;
	}

	public Sphere getSphere(){
		return s;
	}

	public void draw(){
		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
		s.setNormals(GLU.GLU_SMOOTH);
		
		this.getTexture().bind();
		s.draw(radius, 64, 64);
		
		if(this.rings != null){
			this.rings.draw();
		}
	
	}
	
	public float getAxisTilt(){
		return axisTilt;
	}
	
	
	public void setRingsTexture(Texture ringsTexture){
		rings.setTexture(ringsTexture);
	}

	public void addMoons(List<Planet> moons){
		this.moons.addAll(moons);
	}

	public List<Planet> getMoons(){
		return moons;
	}

	public void addMoon(float r,float or, Texture moonText, float axisTilt){
		Planet moon = new Planet(r,or, axisTilt);
		moon.setTexture(moonText);
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
