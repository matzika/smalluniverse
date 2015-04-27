import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
* @class Camera
* @author Aikaterini (Katerina) Iliakopoulou
* @email ai2315@columbia.edu
*
* Simulates the camera
*/
public class Camera {
  public static float moveSpeed = 0.5f;
  public static float mouseSensitivity = 0.05f;

  private float x;
  private float y;
  private float z;

  private float rx;
  private float ry;
  private float rz;

  public Camera(){
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.rx = 0;
    this.ry = 0;
    this.rz = 0;
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
      speed = moveSpeed * 5;
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
  public void move(float dir1, float dir2){
    z += dir1* moveSpeed * Math.sin(Math.toRadians(ry + 90 * dir2));
    x += dir1* moveSpeed * Math.cos(Math.toRadians(ry + 90 * dir2));
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

  public void rotateY(){
    ry += moveSpeed;
  }
}
