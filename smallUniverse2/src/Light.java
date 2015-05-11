
public class Light{
  private float[] diffuse; //Imd in phong
  private float[] specular; //Ims in phong
  private float[] location;
  private float[] worldLocation;
  private float intensity;

  public Light(){
    //default values for light props
		diffuse = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
		specular = new float[]{0.8f, 0.8f, 0.8f, 1.0f};
    location = new float[]{0.0f, 0.0f, 0.0f};
    intensity = 1.0f;
  }

  public Light(float[] diffuse, float[] specular, float[] location, float intensity){
    this.diffuse = diffuse;
    this.specular = specular;
    this.intensity = intensity;
  }

  public float[] getLocation(){
    return this.location;
  }

  public void setLocation(float[] l){
    this.location = l;
  }

  public float[] getWorldLocation(){
    return this.worldLocation;
  }

  public void setWorldLocation(float[] l){
    this.worldLocation = l;
  }

  public float getIntensity(){
    return this.intensity;
  }

  public void setIntensity(float f){
    this.intensity = f;
  }

  public float[] getDiffuse(){
    return this.diffuse;
  }

  public void setDiffuse(float[] diffuse){
    this.diffuse = diffuse;
  }


  public float[] getSpecular(){
    return this.specular;
  }

  public void setSpecular(float[] spc){
    this.specular = spc;
  }

}
