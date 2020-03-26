#version 300 es

in vec4 vertexPosition;
in vec2 vertexTexCoord;

uniform struct{
    mat4 invViewProjMatrix;
    mat4 invViewProjMatrix2;
} camera;

out vec2 tex1;
out vec2 tex2;

void main(void) {
    gl_Position = vertexPosition;
    tex1 = (vertexPosition * camera.invViewProjMatrix).xy * 0.6f;
    tex2 = (vertexPosition * camera.invViewProjMatrix2).xy;
}