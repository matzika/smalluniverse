import java.nio.ByteBuffer;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

public class Universe {
  ShaderProgram sunShader;
  ShaderProgram planetShader;

  String windowTitle = "Small Universe";
  public boolean closeRequested = false;

  long lastFrameTime; // used to calculate delta

  long startTime = Sys.getTime();
  float time;
  float triangleAngle; // Angle of rotation for the triangles
  float quadAngle; // Angle of rotation for the quads

  List<SolarSystem> solarSystems = new ArrayList<SolarSystem>();
  Texture sun, sunChannel0, sunChannel1,mercury, venus, earth, mars, jupiter, saturn, uranus, neptune,pluto;
  Texture moon, phobos, deimos, io, callisto, ganymedes, europa, charon, rings;

  public void run() {

    createWindow();
    getDelta(); // Initialise delta timer
    initGL();

    try{
      planetShader = new ShaderProgram("shaders/lighting.vert", "shaders/lighting.frag", true);
      sunShader = new ShaderProgram("shaders/sun.vert", "shaders/sun.frag", true);
    }catch(Exception e){
      e.printStackTrace();
    }

    createUniverse();

    while (!closeRequested) {
      float delta = getDelta();
      pollInput(delta);
      updateLogic(delta);
      renderGL();

      Display.update();
    }

    cleanup();
  }

  private void initGL() {

    /* OpenGL */
    int width = Display.getDisplayMode().getWidth();
    int height = Display.getDisplayMode().getHeight();

    GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
    GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
    GL11.glLoadIdentity(); // Reset The Projection Matrix
    GLU.gluPerspective(60.0f, ((float) width / (float) height), 0.1f, 1000.0f); // Calculate The Aspect Ratio Of The Window
    GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
    GL11.glLoadIdentity(); // Reset The Modelview Matrix

    GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
    GL11.glClearDepth(1.0f); // Depth Buffer Setup
    GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
    GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
    GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
    Camera.create();

    try {
			sun = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun.png"));
      sunChannel0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun0.png"));
      sunChannel1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sun1.png"));

			mercury = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/mercury.png"));
			venus = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/venus.png"));
			earth = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earth.png"));
			mars = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/mars.png"));
			jupiter = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/jupiter.png"));
			saturn = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/saturn.png"));

			uranus = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/uranus.png"));
			neptune = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/neptune.png"));
			pluto = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pluto.png"));

			//moon - textures
			moon = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/moon.png"));
			phobos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/phobos.png"));
			deimos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/deimos.png"));
			europa = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/europa.png"));
			io = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/io.png"));
			ganymedes = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ganymede.png"));
			callisto = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/callisto.png"));
			charon = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/charon.png"));

			rings = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/rings.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  private void createUniverse(){
    Sun solar = new Sun(100f);
    solar.setChannel0(sunChannel0);
    solar.setChannel1(sunChannel1);

		SolarSystem ss = new SolarSystem(solar,sun);

		//create mercury
		ss.drawMercury(mercury);
		//create venus
		ss.drawVenus(venus);
		//create earth and moon
		ss.drawEarth(earth, moon);
		//create mars and its moons
		ss.drawMars(mars, phobos, deimos);
		//create Jupiter
		ss.drawJupiter(jupiter, io, ganymedes, europa, callisto);
		//create Saturn
		ss.drawSaturn(saturn);
		//create Uranus
		ss.drawUranus(uranus);
		//create Neptune
		ss.drawNeptune(neptune);
		//create Pluto
		ss.drawPluto(pluto,charon);


		solarSystems.add(ss);

    for(Planet p : ss.getPlanets()){
      p.setShader(planetShader);
      for(Moon m : p.getMoons()){
        m.setShader(planetShader);
      }
    }
  }

  private void updateLogic(float delta) {
    this.time = 0.00011f * (Sys.getTime() - startTime);
  }


  private void renderGL() {


    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
    GL11.glLoadIdentity(); // Reset The View
    GL11.glTranslatef(0.0f, 0.0f, -200.0f); // Move Right And Into The Screen

    Camera.apply();

    for(SolarSystem ss : solarSystems){
      sunShader.begin();
      Sun s = ss.getSun();
      sunShader.setUniform3f("sunColor", s.getColor()[0], s.getColor()[1], s.getColor()[2]);
      sunShader.setUniform1f("sunRadius", s.getRadius());
      sunShader.setUniform1f("time", (float) this.time);
      sunShader.setUniform1i("texture", 0);

      s.updateLightOnCamera(Camera.getPos(), Camera.getRotation());
      s.draw();

      sunShader.end();
      planetShader.begin();
      //get Sun Position (right not hardcoded)
      float[] sunPos = s.getLight().getWorldLocation();
      float[] sunImd = s.getLight().getDiffuse();
		  float[] sunIms = s.getLight().getSpecular();

      planetShader.setUniform1f("isSun", 0.0f);
      planetShader.setUniform3f("lights[0].position", sunPos[0], sunPos[1], sunPos[2]);
      planetShader.setUniform1f("lights[0].intensity", s.getLight().getIntensity());
      planetShader.setUniform4f("lights[0].diffuse", sunImd[0], sunImd[1], sunImd[2], sunImd[3]);
      planetShader.setUniform4f("lights[0].specular", sunIms[0], sunIms[1], sunIms[2], sunIms[3]);
      planetShader.setUniform3f("windowDim", (float) Display.getWidth(), (float) Display.getHeight(), 1.0f);

      for(Planet p : ss.getPlanets()){
        p.draw();

      }

      planetShader.end();

      for(Planet p : ss.getPlanets()){
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

    }

  }

  /**
  * Poll Input
  */
  public void pollInput(float delta) {
    Camera.acceptInput(delta);
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

  public void snapshot() {
    System.out.println("Taking a snapshot ... snapshot.png");

    GL11.glReadBuffer(GL11.GL_FRONT);

    int width = Display.getDisplayMode().getWidth();
    int height= Display.getDisplayMode().getHeight();
    int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
    GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );

    File file = new File("snapshot.png"); // The file to save to.
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
  * Calculate how many milliseconds have passed
  * since last frame.
  *
  * @return milliseconds passed since last frame
  */
  public int getDelta() {
    long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    int delta = (int) (time - lastFrameTime);
    lastFrameTime = time;

    return delta;
  }

  private void createWindow() {
    try {
      Display.setDisplayMode(new DisplayMode(640, 480));
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
  private void cleanup() {
    Display.destroy();
  }

  public static void main(String[] args) {
    new Universe().run();
  }

  public static class Camera {
    public static float moveSpeed = 0.05f;

    private static float maxLook = 85;

    private static float mouseSensitivity = 0.01f;

    private static Vector3f pos;
    private static Vector3f rotation;

    public static void create() {
      pos = new Vector3f(0, 0, 0);
      rotation = new Vector3f(0, 0, 0);
    }

    public static void apply() {
      if (rotation.y / 360 > 1) {
        rotation.y -= 360;
      } else if (rotation.y / 360 < -1) {
        rotation.y += 360;
      }

      //System.out.println(rotation);
      GL11.glRotatef(rotation.x, 1, 0, 0);
      GL11.glRotatef(rotation.y, 0, 1, 0);
      GL11.glRotatef(rotation.z, 0, 0, 1);
      GL11.glTranslatef(-pos.x, -pos.y, -pos.z);
    }

    public static void acceptInput(float delta) {
      //System.out.println("delta="+delta);
      acceptInputRotate(delta);
      acceptInputMove(delta);
    }

    public static void acceptInputRotate(float delta) {
      if (Mouse.isInsideWindow() && Mouse.isButtonDown(0)) {
        float mouseDX = Mouse.getDX();
        float mouseDY = -Mouse.getDY();
        //System.out.println("DX/Y: " + mouseDX + "  " + mouseDY);
        rotation.y += mouseDX * mouseSensitivity * delta;
        rotation.x += mouseDY * mouseSensitivity * delta;
        rotation.x = Math.max(-maxLook, Math.min(maxLook, rotation.x));
      }
    }

    public static void acceptInputMove(float delta) {
      boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
      boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
      boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
      boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
      boolean keyFast = Keyboard.isKeyDown(Keyboard.KEY_Q);
      boolean keySlow = Keyboard.isKeyDown(Keyboard.KEY_E);
      boolean keyFlyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
      boolean keyFlyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

      float speed;

      if (keyFast) {
        speed = moveSpeed * 5;
      } else if (keySlow) {
        speed = moveSpeed / 2;
      } else {
        speed = moveSpeed;
      }

      speed *= delta;

      if (keyFlyUp) {
        pos.y += speed;
      }
      if (keyFlyDown) {
        pos.y -= speed;
      }

      if (keyDown) {
        pos.x -= Math.sin(Math.toRadians(rotation.y)) * speed;
        pos.z += Math.cos(Math.toRadians(rotation.y)) * speed;
      }
      if (keyUp) {
        pos.x += Math.sin(Math.toRadians(rotation.y)) * speed;
        pos.z -= Math.cos(Math.toRadians(rotation.y)) * speed;
      }
      if (keyLeft) {
        pos.x += Math.sin(Math.toRadians(rotation.y - 90)) * speed;
        pos.z -= Math.cos(Math.toRadians(rotation.y - 90)) * speed;
      }
      if (keyRight) {
        pos.x += Math.sin(Math.toRadians(rotation.y + 90)) * speed;
        pos.z -= Math.cos(Math.toRadians(rotation.y + 90)) * speed;
      }
    }

    public static void setSpeed(float speed) {
      moveSpeed = speed;
    }

    public static void setPos(Vector3f pos) {
      Camera.pos = pos;
    }

    public static Vector3f getPos() {
      return pos;
    }

    public static void setX(float x) {
      pos.x = x;
    }

    public static float getX() {
      return pos.x;
    }

    public static void addToX(float x) {
      pos.x += x;
    }

    public static void setY(float y) {
      pos.y = y;
    }

    public static float getY() {
      return pos.y;
    }

    public static void addToY(float y) {
      pos.y += y;
    }

    public static void setZ(float z) {
      pos.z = z;
    }

    public static float getZ() {
      return pos.z;
    }

    public static void addToZ(float z) {
      pos.z += z;
    }

    public static void setRotation(Vector3f rotation) {
      Camera.rotation = rotation;
    }

    public static Vector3f getRotation() {
      return rotation;
    }

    public static void setRotationX(float x) {
      rotation.x = x;
    }

    public static float getRotationX() {
      return rotation.x;
    }

    public static void addToRotationX(float x) {
      rotation.x += x;
    }

    public static void setRotationY(float y) {
      rotation.y = y;
    }

    public static float getRotationY() {
      return rotation.y;
    }

    public static void addToRotationY(float y) {
      rotation.y += y;
    }

    public static void setRotationZ(float z) {
      rotation.z = z;
    }

    public static float getRotationZ() {
      return rotation.z;
    }

    public static void addToRotationZ(float z) {
      rotation.z += z;
    }

    public static void setMaxLook(float maxLook) {
      Camera.maxLook = maxLook;
    }

    public static float getMaxLook() {
      return maxLook;
    }

    public static void setMouseSensitivity(float mouseSensitivity) {
      Camera.mouseSensitivity = mouseSensitivity;
    }

    public static float getMouseSensitivity() {
      return mouseSensitivity;
    }
  }
}
