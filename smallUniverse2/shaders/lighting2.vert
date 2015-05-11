varying vec3 N;
varying vec3 v;

void main(){

  N = normalize(gl_NormalMatrix * gl_Normal);
  v = vec3(gl_ModelViewMatrix * gl_Vertex);
  gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;

  gl_Position = ftransform();
}
