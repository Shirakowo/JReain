package dev.shirako.reain.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static File file;

    public Logger() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        file = new File("Reain_" + timestamp + ".log");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String type, String msg) {
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
                throw new IllegalArgumentException("Invalid log type");
        }
        String m = String.format("[%s] [%s] %s%s\u001B[0m", time, type, color, msg);
        System.out.println(m);

        try {
            String m0 = String.format("[%s] [%s] %s", time, type, msg);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(m0);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

