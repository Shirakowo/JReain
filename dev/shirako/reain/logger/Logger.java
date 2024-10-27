package dev.shirako.reain.logger;

import java.io.*;
import java.util.*;
import java.text.*;

public class Logger {
    private static final File file = new File("Reain.log");

    public Logger() {

    }

    private static void log(String type, String msg) {
        // get now time HH:mm:ss
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String color;
        switch (type) {
            case "Info":
                color = "\u001B[0m";
                break;
            case "Warn":
                color = "\u001B[33m";
                break;
            case "Error":
            case "Fatal":
                color = "\u001B[31m";
                break;
            default:
                color = "\u001B[0m";
                break;
        }
        String m = String.format("[%s] [%s] %s%s\u001B[0m", time, type, color, msg);
        System.out.println(m);
    }

    public static void logInfo(String msg) {
        log("Info", msg);
    }

    public static void logWarn(String msg) {
        log("Warn", msg);
    }

    public static void logError(String msg) {
        log("Error", msg);
    }

    public static void logFatal(String msg) {
        log("Fatal", msg);
    }
}

