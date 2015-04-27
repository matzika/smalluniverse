

public class Material{
  private float[] diffuse;
  private float[] ambient;
  private float[] specular;
  private float shininess;
  //TODO: add texture here?

  public Material(){
    //default values for material props
		diffuse = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
		ambient = new float[]{0.2f, 0.0f, 0.0f, 1.0f};
		specular = new float[]{0.8f, 0.8f, 0.8f, 1.0f};
		shininess = 10.0f;
  }

  public Material(float[] diffuse, float[] ambient, float[] specular, float shininess){
    this.diffuse = diffuse;
    this.ambient = ambient;
    this.specular = specular;
    this.shininess = shininess;
  }

  public float[] getDiffuse(){
    return this.diffuse;
  }

  public void setDiffuse(float[] diffuse){
    this.diffuse = diffuse;
  }

  public float[] getAmbient(){
    return this.ambient;
  }

  public void setAmbient(float[] am){
    this.ambient = am;
  }

  public float[] getSpecular(){
    return this.specular;
  }

  public void setSpecular(float[] spc){
    this.specular = spc;
  }

  public float getShininess(){
    return this.shininess;
  }

  public void setShininess(float s){
    this.shininess = s;
  }

}
