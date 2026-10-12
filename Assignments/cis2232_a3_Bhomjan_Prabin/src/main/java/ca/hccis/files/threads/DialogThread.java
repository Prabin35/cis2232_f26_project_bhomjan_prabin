package ca.hccis.files.threads;

import ca.hccis.files.Controller;

/**
 * Runnable for the thread that runs the application completely
 * using JOptionPane.
 *
 * CIS 2232 | Assignment 3
 *
 * @author Prabin Bhomjan
 * @since 20261012
 */
public class DialogThread implements Runnable {

    @Override
    public void run() {
        Controller.menuGUI();
    }
}

