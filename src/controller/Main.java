package controller;

import tools.LogWritter;

public class Main {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String message = "UNCAUGHT EXCEPTION in thread [" + thread.getName() + "]:"
                    + throwable.getClass().getName() + " - " + throwable.getMessage();
            System.err.println(message);
            LogWritter.writeLog(message);
        });

        Controller controller = new Controller();
        controller.run();
    }
}
