varying vec2 texC;
varying float noise;
varying float displacement;

uniform vec3 sunColor;
uniform float sunRadius;
uniform sampler2D texture;

vec3 getColor(){
  vec3 black = vec3(1.0, 1.0, 1.0);

  return mix(sunColor, black, 0.5);

}

void main(){
	vec4 color = texture2D(texture, gl_TexCoord[0].st) * ( 1. - 2. * noise );

  gl_FragColor = color * 2.;

}
