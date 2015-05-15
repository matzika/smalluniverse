import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


/**
* @class Camera
* Simulates the camera in the system
* 
* @author Aikaterini (Katerina) Iliakopoulou
* @email ai2315@columbia.edu
* @author Shloka Kini
* @email srk2169@columbia.edu
*
*/
public class Camera {
  public static float moveSpeed = 2f;
  public static float mouseSensitivity = 0.05f;
  
  private Vector3f pos;
  private Vector3f rotation;

  private float x;
  private float y;
  private float z;

  private float rx;
  private float ry;
  private float rz;

  public Camera(){
    this.x = 200;
    this.y = 200;
    this.z = 500;
    this.rx = 45;
    this.ry = -30;
    this.rz = 0;
  }
  
  public void create() {
      pos = new Vector3f(100, 100, 0);
      rotation = new Vector3f(0, 0, 0);
  }

  /**
  * apply correct movement to the camera
  */
  public void apply() {
    if (ry / 360 > 1) {
      ry -= 360;
    } else if (ry / 360 < -1) {
      ry += 360;
    }

    GL11.glRotatef(rx, 1, 0, 0);
    GL11.glRotatef(ry, 0, 1, 0);
    GL11.glRotatef(rz, 0, 0, 1);
    GL11.glTranslatef(-x, -y, -z);
    
    pos = new Vector3f(x, y, z);
    rotation = new Vector3f(rx, ry, rz);
  }

  public void acceptInput(float delta) {
    //System.out.println("delta="+delta);
    acceptInputRotate(delta);
    acceptInputMove(delta);
  }

  public void acceptInputRotate(float delta) {
    if (Mouse.isInsideWindow() && Mouse.isButtonDown(0)) {
      float mouseDX = Mouse.getDX();
      float mouseDY = -Mouse.getDY();
      //System.out.println("DX/Y: " + mouseDX + "  " + mouseDY);
      ry += mouseDX * mouseSensitivity * delta;
      rx += mouseDY * mouseSensitivity * delta;
      //rx = Math.max(-maxLook, Math.min(maxLook, rx));
      
      rotation = new Vector3f(rx, ry, rz);
    }
  }

  public void acceptInputMove(float delta) {
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
      speed = moveSpeed * 10;
    } else if (keySlow) {
      speed = moveSpeed / 2;
    } else {
      speed = moveSpeed;
    }

    speed *= delta;

    if (keyFlyUp) {
      y += speed;
    }
    if (keyFlyDown) {
      y -= speed;
    }

    if (keyDown) {
      x -= Math.sin(Math.toRadians(ry)) * speed;
      z += Math.cos(Math.toRadians(ry)) * speed;
    }
    if (keyUp) {
      x += Math.sin(Math.toRadians(ry)) * speed;
      z -= Math.cos(Math.toRadians(ry)) * speed;
    }
    if (keyLeft) {
      x += Math.sin(Math.toRadians(ry - 90)) * speed;
      z -= Math.cos(Math.toRadians(ry - 90)) * speed;
    }
    if (keyRight) {
      x += Math.sin(Math.toRadians(ry + 90)) * speed;
      z -= Math.cos(Math.toRadians(ry + 90)) * speed;
    }
    
    pos = new Vector3f(x, y, z);
    rotation = new Vector3f(rx, ry, rz);
  }
  
  public void setPos(Vector3f pos) {
      this.pos = pos;
  }

  public Vector3f getPos() {
	  return pos;
  }
  
  public Vector3f getRotation() {
      return rotation;
  }

  public void setRotationX(float x) {
      rotation.x = x;
  }
    
  public float getX()
  {
    return x;
  }

  public float getY()
  {
    return y;
  }

  public float getZ()
  {
    return z;
  }

  public void setX(float x)
  {
    this.x = x;
  }

  public void setY(float y)
  {
    this.y = y;
  }

  public void setZ(float z)
  {
    this.z = z;
  }

  public float getRX()
  {
    return rx;
  }

  public float getRY()
  {
    return ry;
  }

  public float getRZ()
  {
    return rz;
  }

  public void setRX(float rx)
  {
    this.rx = rx;
  }

  public void setRY(float ry)
  {
    this.ry = ry;
  }

  public void setRZ(float rz)
  {
    this.rz = rz;
  }

  /**
  * Move on the y-axis based on directions you were given
  * @param dir1
  * @param dir2
  */
  public void moveY(float dir1, float dir2){
    z += dir1* moveSpeed * Math.sin(Math.toRadians(ry + 90 * dir2));
    x += dir1* moveSpeed * Math.cos(Math.toRadians(ry + 90 * dir2));
  }
  
  public void moveZ(float dir1, float dir2){
	y += dir1* moveSpeed * Math.sin(Math.toRadians(rz + 90 * dir2));
	x += dir1* moveSpeed * Math.cos(Math.toRadians(rz + 90 * dir2));
  }
  
  public void moveX(float dir1, float dir2){
	y += dir1* moveSpeed * Math.sin(Math.toRadians(rx + 90 * dir2));
	z += dir1* moveSpeed * Math.cos(Math.toRadians(rx + 90 * dir2));
  }

  public void moveForward(){
    z -= moveSpeed;
  }

  public void moveBackward(){
    z += moveSpeed;
  }

  public void moveLeft(){
    x -= moveSpeed;
  }

  public void moveRight(){
    x += moveSpeed;
  }

  public void rotateY(int dir){
    ry += dir * moveSpeed;
  }
  
  public void rotateX(int dir){
	rx += dir * moveSpeed;
  }
  
  public void rotateZ(int dir){
	rz += dir * moveSpeed;
  }
}
