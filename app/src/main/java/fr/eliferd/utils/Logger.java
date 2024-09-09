package fr.eliferd.utils;

public class Logger {
    public static void info(String message) {
        System.out.println("[Info] " + message);
    }

    public static void warn(String message) {
        System.out.println("[Warn] " + message);
    }

    public static void error(String message) {
        System.err.println("[Error] " + message);
    }
}
