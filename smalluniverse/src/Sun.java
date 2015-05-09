
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Sun extends SpaceObject{
	private Sphere s;
	private Light l;

	public Sun(float r){
		this.radius = r;
		s = new Sphere();
		l = new Light();
		l.setDiffuse(new float[]{1.0f, 1.0f, 1.0f});
		l.setSpecular(new float[]{1.0f, 1.0f, 1.0f});
	}

	public void draw(){
		s.setDrawStyle(GLU.GLU_FILL);
		s.setTextureFlag(true);
//		s.setNormals(GLU.GLU_SMOOTH);
////		int textId = GL11.glGenLists(1);
////
////		GL11.glNewList(textId, GL11.GL_COMPILE);
		if(this.getTexture() == null)
			System.out.println("TEXURE NULL");
		this.getTexture().bind();
		s.draw(radius, 64, 64);
	}

	public Light getLight(){
		return this.l;
	}

	public void setLight(Light light){
		this.l = light;
	}

	public void setSunlightDiffuse(float[] d){
		this.l.setDiffuse(d);
	}

	public void setSunlighSpecular(float[] s){
		this.l.setSpecular(s);
	}
}
