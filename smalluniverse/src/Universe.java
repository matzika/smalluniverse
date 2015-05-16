import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;;

/**
 * @class Universe
 * The main class of the program implements the small universe
 * The user can navigate around with the keyboard
 * 
 * @author Aikaterini (Katerina) Iliakopoulou
 * @email ai2315@columbia.edu
 * @author Shloka Kini
 * @email srk2169@columbia.edu
 *
 */
public class Universe {
	
	static String windowTitle = "Small Universe";

	public static boolean closeRequested = false;

	static Camera camera;

	static ShaderProgram sunShader;
	static ShaderProgram planetShader;

	static int snapshot_count = 0;
	
	static long lastFrameTime; // used to calculate delta
	static long startTime = Sys.getTime();
	
	static float time;
	
	float triangleAngle; // Angle of rotation for the triangles
	float quadAngle; // Angle of rotation for the quads
	
	static ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	static ArrayList<Asteroid> asteroidsDeleted = new ArrayList<Asteroid>();

	//support multiple solar systems
	static List<SolarSystem> solarSystems = new ArrayList<SolarSystem>();
	static HomeSolarSystem system;
	
	//load the textures for planets and moons
	private static Texture sun, sunChannel0, sunChannel1, sky, asteroidTex;
	
	public static void run() {
		Universe.createWindow();
		Universe.getDelta();
		Universe.initGL();

		//create shaders
		try{
			planetShader = new ShaderProgram("shaders/lighting.vert", "shaders/lighting.frag", true);
		    sunShader = new ShaderProgram("shaders/sun.vert", "shaders/sun.frag", true);
		}catch(Exception e){
			e.printStackTrace();
		}

		//creates the camera
		camera = new Camera();

		//create universe
		Universe.createUniverse();

		while (!closeRequested) {
			Universe.pollInput();
			Universe.updateLogic(Universe.getDelta());
			Universe.renderGL();
			
			Display.sync(60);
			Display.update();
		}

		Universe.cleanup();
	}

	/**
	 * Sets openGL matrixes & states
	 */
	private static void initGL() {

		/* OpenGL */
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();

		GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix

		GLU.gluPerspective(80, ((float) width / (float) height), 0.1f, 6000.0f); //set perpective projection
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix

		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations

		GL11.glEnable(GL11.GL_BLEND);//enables blening so that we see the particles fading smoothly
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		try {
			//read sun's textures
			sun = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun.png"));
		    sunChannel0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun.png"));
		    sunChannel1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun1.png"));
		    
		    sky = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background.png"));
		    asteroidTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/asteroid.png"));
		    

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createUniverse(){
		Sun solar = new Sun(100f);
	    solar.setChannel0(sunChannel0);
	    solar.setChannel1(sunChannel1);
	    
		try {
			system = new HomeSolarSystem(solar,sun);
			system.create();
			system.setShader(planetShader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getDelta() {
		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		int delta = (int) (time - lastFrameTime);
		lastFrameTime = time;

		return delta;
	}

	private static void updateLogic(float delta) {
		time = 0.00011f * (Sys.getTime() - startTime);
		
	}

	private static void renderGL() {
		asteroids.removeAll(asteroidsDeleted);
		if(Math.random() > .99){
			Asteroid a = new Asteroid(1000.0f, (float) Math.random()*(5));
			
			a.setTexture(asteroidTex);
			a.setShader(planetShader);
			asteroids.add(a);
		}
		
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
	    GL11.glLoadIdentity(); // Reset The View
	    GL11.glTranslatef(0.0f, 0.0f, 0.0f); // Move Right And Into The Screen
		
		camera.apply();
		
		drawBackground();
		
		sunShader.begin();
	    Sun s = system.getSun();
	    sunShader.setUniform3f("sunColor", s.getColor()[0], s.getColor()[1], s.getColor()[2]);
	    sunShader.setUniform1f("sunRadius", s.getRadius());
	    sunShader.setUniform1f("time", (float) time);
	    sunShader.setUniform1i("texture", 0);

	    s.updateLightOnCamera(camera.getPos(), camera.getRotation());
	    s.draw();
		
	    sunShader.end();
	    	    
	    planetShader.begin();
	    float[] sunPos = s.getLight().getWorldLocation();
	    float[] sunImd = s.getLight().getDiffuse();
		float[] sunIms = s.getLight().getSpecular();
		planetShader.setUniform1f("isSun", 0.0f);
	    planetShader.setUniform3f("lights[0].position", sunPos[0], sunPos[1], sunPos[2]);
	    planetShader.setUniform1f("lights[0].intensity", s.getLight().getIntensity());
	    planetShader.setUniform4f("lights[0].diffuse", sunImd[0], sunImd[1], sunImd[2], sunImd[3]);
	    planetShader.setUniform4f("lights[0].specular", sunIms[0], sunIms[1], sunIms[2], sunIms[3]);
	    planetShader.setUniform3f("windowDim", (float) Display.getWidth(), (float) Display.getHeight(), 1.0f);
		
	    //draw asteroids
	    for(Asteroid a : asteroids){
	    	if(!asteroidsDeleted.contains(a))
	    		a.draw();
	    }
	    asteroidsDeleted.clear();
	    for(Planet p : system.getPlanets()){
	        p.draw();
	    }
	    
	    planetShader.end();

	    
	    for(Planet p : system.getPlanets()){
	        GL11.glPushMatrix();
	    	GL11.glColor3f(1.0f, 1.0f, 1.0f);
	    	GL11.glLineWidth(0.5f);
	    	GL11.glBegin(GL11.GL_LINE_LOOP);
	    	float angle = 0;
	    	float x=0f, z=0f;
	    	while(angle < (float) (2*Math.PI))
	    	{
	    		x = (float)Math.sin(angle)*p.getOrbitRadius();
	    		z = (float)Math.cos(angle)*p.getOrbitRadius();
	    		GL11.glVertex3f(x,0f, z);
	    		angle = angle + 0.01f;
	    	}

	    	GL11.glEnd();
	    	GL11.glPopMatrix();
	     }
	    
	    for(Asteroid as : asteroids){
			if(as.getDistance() < 0.0f){
				asteroidsDeleted.add(as);
			}
		}

		
	}
	
	public static void drawBackground(){
		Sphere back = new Sphere();
		
		back.setDrawStyle(GLU.GLU_FILL);
		back.setTextureFlag(true);
		
		sky.bind();
		GL11.glPushMatrix();
		back.draw(5000, 32, 32);
		GL11.glPopMatrix();
	}

	/**
	 * Polls Input from keyboard to create an interactive program
	 */
	public static void pollInput() {
		//Delegates Camera input to the camera class
		camera.acceptInput(Universe.getDelta());

		//basic movement in the universe on the y axis (Forward, Backward, Left, Right)
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			camera.moveY(-1,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			camera.moveY(1,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			camera.moveY(-1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			camera.moveY(1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			camera.moveZ(-1,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
			camera.moveZ(+1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
			camera.moveZ(-1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			camera.moveZ(1,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_X))
			camera.moveX(-1,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			camera.moveX(-1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			camera.moveX(1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
			camera.moveX(-1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_O))
			camera.moveX(1,0);

		// scroll through key events
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
					closeRequested = true;
				else if (Keyboard.getEventKey() == Keyboard.KEY_P)
					snapshot();
			}
		}

		if (Display.isCloseRequested()) {
			closeRequested = true;
		}
	}

	/**
	 * Takes a snapshot from the screen
	 */
	public static void snapshot() {
		System.out.println("Taking a snapshot ... snapshot.png");

		GL11.glReadBuffer(GL11.GL_FRONT);

		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );

		File file = new File("snapshot"+(snapshot_count)+".png"); // The file to save to.
		snapshot_count++;
		String format = "PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) { e.printStackTrace(); }
	}

	/**
	 * Creates the screen for the simulation to be displayed
	 */
	private static void createWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setVSyncEnabled(true);
			Display.setTitle(windowTitle);
			Display.create();
		} catch (LWJGLException e) {
			Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Destroy and clean up resources
	 */
	private static void cleanup() {
		Display.destroy();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Universe.run();
	}

}
