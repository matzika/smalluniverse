import org.lwjgl.examples.spaceinvaders.Texture;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Sun extends SpaceObject{
	private Sphere s;

	public Sun(float r){
		this.radius = r;
		s = new Sphere();
	}

	public void draw(){
		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
//		s.setNormals(GLU.GLU_SMOOTH);
////		int textId = GL11.glGenLists(1);
////		
////		GL11.glNewList(textId, GL11.GL_COMPILE);
		if(texture == null)
			System.out.println("tEXURE NULL");
		texture.bind();
		s.draw(radius, 64, 64);
	}
}
