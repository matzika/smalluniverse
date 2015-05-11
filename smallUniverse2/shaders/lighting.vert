varying vec3 normal;
varying vec4 eye;
varying vec4 pos;

void main(){
  normal = gl_NormalMatrix * gl_Normal;
  pos = gl_ModelViewMatrix * gl_Vertex;
  eye = -pos;

  gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;

  gl_Position = ftransform();

}
