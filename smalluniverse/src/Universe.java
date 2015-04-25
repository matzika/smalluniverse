import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class Universe {

	static String windowTitle = "A small universe";
	
	public static boolean closeRequested = false;
	
	static Camera camera;
	
	static int snapshot_count = 0;
	
	static List<SolarSystem> solarSystems = new ArrayList<SolarSystem>();
	
	public static void run() {
        Universe.createWindow();
        Universe.initGL();
        
        //creates the camera
        camera = new Camera();
        
        Universe.createUniverse();
        
        while (!closeRequested) {
            Universe.pollInput();
            Universe.updateLogic();
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
      
        GLU.gluPerspective(60, ((float) width / (float) height), 0.1f, 100); //set perpective projection 
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
      
    }
	
	public static void createUniverse(){
		Sun sun = new Sun(10f);
		SolarSystem ss = new SolarSystem(sun);
		//create mercury
		ss.createPlanet(1f, 3f);
		//create venus
		ss.createPlanet(1.5f, 6f);
		//create earth
		ss.createPlanet(2f, 10f);
		//create mars
		ss.createPlanet(1.7f, 15f);
		solarSystems.add(ss);
		
	}
	
	private static void updateLogic() {
		
	}
	
	private static void renderGL() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
	    GL11.glLoadIdentity(); // Reset The View

	    camera.apply();
		
		for(SolarSystem ss : solarSystems){
			GL11.glTranslatef(0.0f, 0.0f, -70f);
			GL11.glPushMatrix();
		    {
		    	GL11.glColor3f(1f,1f,0f);
		        ss.getSun().draw();
		        for(Planet p : ss.getPlanets()){
		        	GL11.glTranslatef(1.0f, 0.0f, -35 - p.getOrbitRadius());
		        	GL11.glColor3f(1f,0f,0f);
		        	p.draw();
		        }
		    }
		    GL11.glPopMatrix();
		}	
	}

    //Given an object's rotation angle, this will update and return that rotationAngle
    //And it will rotate the object by that angle amount if run
    //after the object's translation but before draw
    public static float rotatePlanet(float rotationAngle){
        rotationAngle = 5f + rotationAngle;
        if(rotationAngle >=360)
            rotationAngle = 0f;
        GL11.glRotatef(rotationAngle, 0f, 1f, 0f ); //  rotate around center
        return rotationAngle;
    }

    //Given an object's x/y coordinates, it's angle from the center, the center x/y, and
    //radius, it will update the coordinate, the angle, and return those values
    //Does the revolution for object
    public static float[] revolutionPlanet(float centerX, float centerY, float xCoord, float yCoord, float angle, float radius){
        angle = 0.01f + angle;
        if(angle >=(2*Math.PI))
            angle = 0;
        xCoord = (float)(centerX+Math.sin(angle)*radius);
        yCoord = (float)(centerY+Math.cos(angle)*radius);
        return new float[]{xCoord, yCoord, angle};
    }
	
	 /**
     * Polls Input from keyboard to create an interactive program
     */
    public static void pollInput() {
        
        //basic movement in the universe on the y axis (Forward, Backward, Left, Right)
        if(Keyboard.isKeyDown(Keyboard.KEY_UP))
        	camera.move(-1,1);
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        	camera.move(1,1);
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        	camera.move(-1,0);
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        	camera.move(1,0);
        
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
            //Display.setVSyncEnabled(true);
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
