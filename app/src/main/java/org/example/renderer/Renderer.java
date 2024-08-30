package org.example.renderer;

import static org.example.shaders.ShaderTypeEnum.FRAGMENT_SHADER;
import static org.example.shaders.ShaderTypeEnum.VERTEX_SHADER;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.example.Keyboard;
import org.example.Mouse;
import org.example.Window;
import org.example.entities.Point;
import org.example.entities.Sand;
import org.example.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class Renderer {
	
	private Window _window;
	
	private Keyboard _keyboard;
	
	private Mouse _mouse;
	
	private int _shaderProgramId;
	
	private int _vao;
	
	private List<Point> pointList = new ArrayList<Point>();

	public Renderer(Window window, Keyboard keyboard, Mouse mouse) {
		this._window = window;
		this._keyboard = keyboard;
		this._mouse = mouse;
		
		this.initRendererData();
		
		this._shaderProgramId = glCreateProgram();
		
		this.buildShaders();

	}
	
	private void initRendererData() {
		// Create LWJGL capabilities to work with OpenGL
		GL.createCapabilities();
		
		System.out.println("--------------------------");
		System.out.println("OpenGL (Version) : " + glGetString(GL_VERSION));
		System.out.println("OpenGL (Vendor) : " + glGetString(GL_VENDOR));
		System.out.println("OpenGL (Renderer) : " + glGetString(GL_RENDERER));
		System.out.println("Supported GLSL version : " + glGetString(GL46.GL_SHADING_LANGUAGE_VERSION));
		System.out.println("--------------------------");
		
		// (OpenGL) Sets the viewport size
		glViewport(0, 0, this._window.getWidth(), this._window.getHeight());
		
		// (OpenGL) Sets the clear color for that viewport
		glClearColor(0.0f,  0.0f,  0.0f, 1.0f);
	}
	
	private void buildShaders() {
		List<Shader> shaders = new ArrayList<Shader>();

		Shader vertexShader = new Shader(VERTEX_SHADER, "src/main/resources/vertex.glsl");
		Shader fragmentShader = new Shader(FRAGMENT_SHADER, "src/main/resources/fragment.glsl");
		
		shaders.add(vertexShader);
		shaders.add(fragmentShader);
		
		shaders.forEach(shader -> shader.attach(this._shaderProgramId));
		
		// Link the shader program after attaching the compiled shaders to it.
		glLinkProgram(this._shaderProgramId);
		// Validate the shader program
		glValidateProgram(this._shaderProgramId);
	}
	
	public void render() {
		
		this._vao = glGenVertexArrays();

		glBindVertexArray(this._vao);
		
		while(!this._window.shouldClose()) {
			// (OpenGL) Clears the window
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			if(this._mouse.isLeftButtonDown()) {
				float x = (float) (this._mouse.getX() / (this._window.getWidth()/2) - 1.0f);
				float y = -1.0f * (float)(this._mouse.getY() / (this._window.getHeight()/2) - 1.0f);
				System.out.println("x : " + x + ", y : " + y);
				Sand sand = new Sand(x, y);
				sand.setShaderProgramId(this._shaderProgramId);
				this.pointList.add(sand);
			}
			
			glUseProgram(this._shaderProgramId);
			glBindVertexArray(this._vao);
			
			this.pointList.forEach(point -> point.update());
			
			// Swap front and back buffers & poll all window events
			glfwSwapBuffers(this._window.getWindow());
			glfwPollEvents();
		}
		
		glDisable(GL_DEPTH_TEST);
		
	}
	
}
