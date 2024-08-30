package org.example;
import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

	private Window _window;
	
	private double _mouseX, _mouseY;
	
	public Mouse(Window window) {
		this._window = window;
	}
	
	public void registerCallback() {
		glfwSetMouseButtonCallback(this._window.getWindow(), (window, button, action, mods) -> {
			if(action == GLFW_PRESS)
				this.onButtonPressed(button);
			
			if(action == GLFW_RELEASE)
				this.onButtonReleased(button);
		});
		
		glfwSetCursorPosCallback(this._window.getWindow(), (window, mouseX, mouseY) -> {
			this._mouseX = mouseX;
			this._mouseY = mouseY;
		});
	}
	
	private void onButtonPressed(int button) {
		
	}
	
	private void onButtonReleased(int button) {
		
	}

	public boolean isLeftButtonDown() {
		return glfwGetMouseButton(this._window.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
	}

	public boolean isLeftButtonReleased() {
		return glfwGetMouseButton(this._window.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE;
	}

	public boolean isRightButtonDown() {
		return glfwGetMouseButton(this._window.getWindow(), GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS;
	}

	public boolean isRightButtonReleased() {
		return glfwGetMouseButton(this._window.getWindow(), GLFW_MOUSE_BUTTON_RIGHT) == GLFW_RELEASE;
	}
	
	public double getX() {
		return this._mouseX;
	}
	
	public double getY() {
		return this._mouseY;
	}
}
