package ca.hccis.files.threads;

import ca.hccis.files.Controller;

/**
 * Thread that runs the application completely in the console.
 *
 * CIS 2232 | Assignment 3
 *
 * @author Prabin Bhomjan
 * @since 20261012
 */
public class ConsoleThread extends Thread {

    public ConsoleThread() {
        super("console-thread");
    }

    @Override
    public void run() {
        Controller.menuConsole();
    }
}
