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

varying vec3 N;
varying vec3 v;

void main(){

    vec4 spec = vec4(0.0);
    vec4 diffuse = vec4(0.0);
    vec4 color = texture2D(mat.texture, gl_TexCoord[0].st);

    for(int i = 0; i < 5; i++){
      vec3 lightDir = vec3(vec3(gl_ModelViewProjectionMatrix * vec4(lights[i].position,1.0)) - v);
      vec3 n = normalize(N);

      float NdotL = max(dot(n,lightDir), 0.0);

      diffuse += NdotL * color * lights[i].diffuse;

      if(NdotL > 0.0){
        vec3 eyeDir = normalize(-v);
        vec3 refDir = normalize(-reflect(lightDir,N));
        float RdotE = max(dot(refDir, eyeDir), 0.0);
        spec += mat.specular * pow(RdotE, mat.shininess) * lights[0].specular;
      }

    }

    gl_FragColor = diffuse + spec;


}
