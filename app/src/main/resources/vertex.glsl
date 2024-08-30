#version 400 core

in vec2 vPos;
in vec4 vColor;

uniform mat4 uModel;
uniform mat4 uView;
uniform mat4 uProjection;

out vec4 color;

void main() {
	color = vColor;
	gl_Position = uProjection * uView * uModel * vec4(vPos, 1.0f, 1.0f);
}