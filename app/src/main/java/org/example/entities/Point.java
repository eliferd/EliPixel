package org.example.entities;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.GL_ACTIVE_ATTRIBUTES;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public abstract class Point {
	
	protected float _posX, _posY;
	protected float _r, _g, _b;
	
	protected int positionVBO, colorVBO;
	
	private int _shaderProgramId;
	
	public Point(float positionX, float positionY, float red, float green, float blue) {
		this._posX = positionX;
		this._posY = positionY;
		this._r = red;
		this._g = green;
		this._b = blue;
		this.positionVBO = glGenBuffers();
		this.colorVBO = glGenBuffers();
	}
	
	public void setShaderProgramId(final int shaderProgramId) {
		this._shaderProgramId = shaderProgramId;
	}
	
	/**
	 * This method is called once every frame.
	 */
	public void update() {
		float[] positionVertices = new float[] { this._posX, this._posY };
		float[] colorVertices = new float[] { this._r, this._g, this._b, 1.0f };

		int vPos = glGetAttribLocation(this._shaderProgramId, "vPos");
		int vColor = glGetAttribLocation(this._shaderProgramId, "vColor");

		int uModel = glGetUniformLocation(this._shaderProgramId, "uModel");
		int uView = glGetUniformLocation(this._shaderProgramId, "uView");
		int uProjection = glGetUniformLocation(this._shaderProgramId, "uProjection");
		
		Matrix4f modelMatrix = new Matrix4f().rotate(-55.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		Matrix4f viewMatrix = new Matrix4f().translate(new Vector3f(this._posX, this._posY, 1.0f));
		Matrix4f projectionMatrix = new Matrix4f().ortho(0f, 1280f, 0f, 720f, -1f, 100f);
		
		FloatBuffer modelMatrixBuffer = BufferUtils.createFloatBuffer(16);
		FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
		FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
		
		modelMatrix.get(modelMatrixBuffer);
		viewMatrix.get(viewMatrixBuffer);
		projectionMatrix.get(projectionMatrixBuffer);

		this.updateVecAttribute(this.positionVBO, vPos, 2, positionVertices);
		this.updateVecAttribute(this.colorVBO, vColor, 4, colorVertices);
		
		glUniformMatrix4fv(uModel, false, modelMatrixBuffer);
		glUniformMatrix4fv(uView, false, viewMatrixBuffer);
		glUniformMatrix4fv(uProjection, false, projectionMatrixBuffer);

		
		glPointSize(50.0f);
		glDrawArrays(GL_POINTS, 0, 1);
	}
	
	/**
	 * Update vector attribute from its location
	 * @param vbo The VBO where the attribute is/will be stored.
	 * @param index The attribute location (used for glVertexAttribPointer)
	 * @param vecAttribCount Tells OpenGL how many elements that this attribute contains. E.g : 2 for a Vec2.
	 * @param values The values to be passed into the buffer
	 */
	private void updateVecAttribute(int vbo, int index, int vecAttribCount, float[] values) {
		glEnableVertexAttribArray(index);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, values, GL_STATIC_DRAW);
		glVertexAttribPointer(index, vecAttribCount, GL_FLOAT, false, 4 * vecAttribCount, 0);
		glDisableVertexAttribArray(index);
	}
}
