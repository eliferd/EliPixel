package org.example;
import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
	
	private Window _window;

	public Keyboard(Window window) {
		this._window = window;
	}
	
	public void registerCallback() {
		glfwSetKeyCallback(this._window.getWindow(), (windowInstance, key, scancode, action, mods) -> {
			if(action == GLFW_PRESS) 
				this.onKeyDown(key);
			
			if(action == GLFW_RELEASE)
				this.onKeyUp(key);
		});
	}
	
	/**
	 * Triggered when a key down is performed.
	 * @param key - The key code (ex: GLFW_KEY_ESC)
	 */
	private void onKeyDown(int key) {
		
	}
	
	/**
	 * Triggered when a key release is performed.
	 * @param key - The key code (ex: GLFW_KEY_ESC)
	 */
	private void onKeyUp(int key) {
		if (key == GLFW_KEY_ESCAPE) {
			glfwSetWindowShouldClose(this._window.getWindow(), true);
		}
	}
	
	/**
	 * Returns true if the targeted key is released.
	 * @param key
	 * @return boolean
	 */
	public boolean isKeyUp(int key) {
		return glfwGetKey(this._window.getWindow(), key) == GLFW_RELEASE;
	}

	/**
	 * Returns true if the targeted key is pressed.
	 * @param key
	 * @return boolean
	 */
	public boolean isKeyDown(int key) {
		return glfwGetKey(this._window.getWindow(), key) == GLFW_PRESS;
	}
}
