package org.example;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	
	private String _title;
	private int _width;
	private int _height;
	
	private long _window; // GLFW Window Instance

	public Window(String title, int width, int height) {
		this._title = title;
		this._width = width;
		this._height = height;
	}
	
	public void createWindow() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		this._window = glfwCreateWindow(this._width, this._height, this._title, NULL, NULL);
		
		if(this._window == NULL) {
			throw new IllegalStateException("[GLFW] Failed to create window.");
		}
		
		this.alignCenter();
	}
	
	private void alignCenter() {
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center the window
		glfwSetWindowPos(this._window, (vidmode.width() - this._width) / 2, (vidmode.height() - this._height) / 2);
	}
	
	public void setContextAsCurrent() {
		glfwMakeContextCurrent(this._window);
	}
	
	public void close() {
		glfwDestroyWindow(this._window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(this._window);
	}
	
	public long getWindow() {
		return this._window;
	}
	
	public int getWidth() {
		return this._width;
	}
	
	public int getHeight() {
		return this._height;
	}
}
