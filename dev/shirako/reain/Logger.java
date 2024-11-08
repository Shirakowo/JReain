package dev.shirako.reain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Logger {
    private static File file;

    public Logger() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        file = new File("Reain_" + timestamp + ".log");

        try {
            file.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
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
        logFatal(msg, 1);
    }

    public static void logFatal(String msg, int exitCode) {
        log("Fatal", msg);
        String fullMessage = msg + "\nCrash Code: " + exitCode;

        Object[] options = {"Exit", "Save Crash Report"};
        int choice = JOptionPane.showOptionDialog(
            null,
            fullMessage,
            "Fatal Error",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 1) {
            saveCrashReport(fullMessage);
        }
        log("Fatal", "Reain exited with crash code: " + exitCode);
        System.exit(exitCode);
    }

    private static void saveCrashReport(String message) {
        try {
            String reportFileName = "CrashReport_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            File reportFile = new File(reportFileName);
            FileWriter fw = new FileWriter(reportFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(message);
            pw.close();
            JOptionPane.showMessageDialog(null, "Crash report saved as " + reportFileName, "Report Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to save crash report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    protected static void log(String type, String msg) {
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
                logFatal("Invalid log type", 2);
                throw new IllegalArgumentException("Invalid log type");
        }
        String m = String.format("[%s] [Reain/%s] %s%s\u001B[0m", time, type, color, msg);
        System.out.println(m);

        try {
            String m0 = String.format("[%s] [Reain/%s] %s", time, type, msg);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(m0);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

