package fr.eliferd.display;

import fr.eliferd.scene.IScene;
import fr.eliferd.utils.Logger;
import fr.eliferd.utils.Time;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window _instance = null;

    private int width,
                height;

    private String title;

    private long _glfwWindow;

    private IScene currentScene = null;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "EliPixel";

        if (!glfwInit()) {
            Logger.error("Failed to init GLFW !");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        this._glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if(this._glfwWindow == NULL) {
            Logger.error("Failed to initialize window !");
        }

        glfwMakeContextCurrent(_glfwWindow);

        GL.createCapabilities();

        this.switchScene(0);

        this.loop();

        this.terminate();
    }

    private void updateTitle(String newTitle) {
        glfwSetWindowTitle(this._glfwWindow, this.title + " - " + newTitle);
    }

    private void loop() {
        float start_dt = Time.startTime();
        while(!glfwWindowShouldClose(_glfwWindow)) {
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(this._glfwWindow);
            glfwPollEvents();

            float end_dt = Time.startTime();
            float dt = (end_dt - start_dt) / 1E-9F;
            this.currentScene.update(dt);
            start_dt = Time.startTime();
        }
    }

    private void terminate() {
        glfwDestroyWindow(this._glfwWindow);
        glfwTerminate();
    }

    public void switchScene(int sceneId) {
        switch (sceneId) {
            case 0:
                // this.currentScene = new ...
                break;
            case 1:
                // this.currentScene = new ...
                break;
            default:
                break;
        }

        if(this.currentScene != null) {
            this.updateTitle(this.currentScene.getSceneTitle());
        }
    }

    public static Window instance() {
        if(_instance == null) {
            _instance = new Window();
        }

        return _instance;
    }
}
