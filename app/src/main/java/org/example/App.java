package org.example;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLUtil;

import org.example.renderer.Renderer;

public class App {
	
	private Window _window;
	private Keyboard _keyboard;
	private Mouse _mouse;
    private Renderer _renderer;

	public App() {
		// Initialize GLFW, throws exception if it fails
		if(!glfwInit()) {
			throw new IllegalStateException("[GLFW] Initialization failed.");
		}
		
		// In case of any error, print it to the console.
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		// Draw the window
		this._window = new Window("OpenGL Fun", 1280, 720);
		this._window.createWindow();
		this._window.setContextAsCurrent();
		
		// Register keyboard input events
		this._keyboard = new Keyboard(this._window);
		this._keyboard.registerCallback();
		
		// Register mouse input events
		this._mouse = new Mouse(this._window);
		this._mouse.registerCallback();
		
		// Enable V-Sync
		glfwSwapInterval(1);
		
		// Rendering manager
		this._renderer = new Renderer(this._window, this._keyboard, this._mouse);
		//GLUtil.setupDebugMessageCallback();
		this._renderer.render();
		
		// When the window should close, terminate GLFW and close the program.
		this.terminate();
	}

	
	private void terminate() {
		this._window.close();
		glfwTerminate();
	}
	
    public static void main(String[] args) {
        new App();
    }
}
