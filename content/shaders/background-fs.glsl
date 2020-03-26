#version 300 es

precision highp float;

in vec2 tex1;
in vec2 tex2;

out vec4 fragmentColor;

uniform struct {
	sampler2D colorTexture;
} material;


void main(void) {
  fragmentColor = texture(material.colorTexture, tex1) * 0.65f + texture(material.colorTexture, tex2) * 0.35f;
}
