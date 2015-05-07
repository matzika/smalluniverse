import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Planet extends SpaceObject{
	
	private Sphere s;

	private List<Planet> moons = new ArrayList<Planet>();

	public Planet(float r,float or){
		this.radius = r;
		this.orbitRadius = or;
		s = new Sphere();

	}

	public Sphere getSphere(){
		return s;
	}

	public void draw(){
		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
		s.setNormals(GLU.GLU_SMOOTH);
		//int textId = GL11.glGenLists(1);
		
		//GL11.glNewList(textId, GL11.GL_COMPILE);
		texture.bind();
		s.draw(radius, 64, 64);
	}
	

}
