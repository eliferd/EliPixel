package org.example.shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {

	private int _shaderId;
	
	public Shader(ShaderTypeEnum type, String location) {

		switch (type) {
			case VERTEX_SHADER:
				this._shaderId = glCreateShader(GL_VERTEX_SHADER);
				break;
			case FRAGMENT_SHADER:
				this._shaderId = glCreateShader(GL_FRAGMENT_SHADER);
				break;
		}
	
		glShaderSource(this._shaderId, this.getShaderSourceFromLocation(location));
		glCompileShader(this._shaderId);
		
		this.checkForCompilationError(this._shaderId, type);
	}
	

	/**
	 * Fetch shader source code from file location
	 * @param location Relative path to the shader file
	 * @return The shader source as string
	 */
	private String getShaderSourceFromLocation(String location) {
		String bufferedSource = "";
		
		try {
			File shaderFile = new File(location);
			try (Scanner sc = new Scanner(shaderFile)) {
				while(sc.hasNextLine()) {
					bufferedSource = bufferedSource + "\n" + sc.nextLine();
				}
			}
		} catch(FileNotFoundException ex) {
			System.err.println("Couldn't locate any shader file at " + location);
			ex.printStackTrace();
		}
		
		return bufferedSource;
	}
	
	/**
	 * Checks if the compilation was successful or not.
	 * When an error occurred : Prints to the console as an error.
	 * @param shaderId The shader to check the compilation for
	 * @param shaderType The shader type enum
	 */
	private void checkForCompilationError(int shaderId, ShaderTypeEnum shaderType) {
		int compiled = glGetShaderi(shaderId, GL_COMPILE_STATUS);
		
		String shaderTypeLabel = null;

		switch (shaderType) {
			case VERTEX_SHADER:
				shaderTypeLabel = "Vertex";
				break;
			case FRAGMENT_SHADER:
				shaderTypeLabel = "Fragment";
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + shaderType);
		}
		
		if(compiled == GL_FALSE) {
			System.err.println("[" + shaderTypeLabel + " Shader] Failed to compile shader.");
			System.err.println(glGetShaderInfoLog(shaderId, 2048));
		}
	}
	
	/**
	 * Attach this shader to the shader program by the program id
	 * @param shaderProgramId
	 */
	public void attach(int shaderProgramId) {
		glAttachShader(shaderProgramId, this._shaderId);
	}
}
