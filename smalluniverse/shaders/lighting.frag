struct Light {
  vec3 position;
  vec4 diffuse;
  vec4 specular;
};

struct Material {
  vec4 diffuse;
  vec4 specular;
  float shininess;
  sampler2D texture;
};


uniform Material mat;
uniform Light lights[5];
uniform float isSun;
uniform float isRing;
uniform vec3 windowDim;

varying vec3 normal;
varying vec4 pos;
varying vec4 eye;

void main(){
  if(isSun == 0.0 && isRing == 0.0){
    vec4 spec = vec4(0.0);
    vec4 diffuse = vec4(0.0);
    vec4 color = texture2D(mat.texture, gl_TexCoord[0].st);
    float distToEye		= distance( pos.xyz, eye.xyz );


    vec3 n = normalize(normal);
    vec3 e = normalize(vec3(eye));

    for(int i = 0; i < 5; i ++){
      //vec3 lightPos = vec3(gl_ProjectionMatrix * vec4(lights[i].position,1.0));
      vec3 lightPos = vec3(lights[i].position);
      vec3 lightDir = normalize(lightPos - vec3(pos));

      float intensity = max(dot(n, lightDir),0.0);
      diffuse += lights[i].diffuse * intensity * color;

      if(intensity > 0.0){

        vec3 halfDir = normalize(lightDir + e);
        float intSpec = max(dot(halfDir, n), 0.0);
        spec += mat.specular * pow(intSpec, 4.0*mat.shininess) * lights[i].specular;
      }
    }

    gl_FragColor = diffuse + spec;
    gl_FragColor.a = 1.0;
  }
  else{
    gl_FragColor = texture2D(mat.texture, gl_TexCoord[0].st);
    gl_FragColor.a = 1.0;
  }

}
