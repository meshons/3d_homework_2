#version 300 es

precision highp float;

uniform struct {
    vec3 solidColor;
} material;

out vec4 fragmentColor;

void main(void) {
    fragmentColor = vec4(material.solidColor.xyz, 1);
}
