/* (C)2023 */
package org.gui;

import javax.swing.*;

public class Notifier extends JLabel {
    Notifier() {
        setText(" ");
    }

    /**
     * Displays the notifier with the selected text for the specified amount of time in
     * milliseconds.
     *
     * @param text The text to display.
     * @param millis The time to display the text for in milliseconds.
     */
    public void showText(String text, int millis) {
        setText(text);
        timedHide(millis);
    }

    /**
     * Hides the notifier after the specified amount of time using the java scheduler.
     *
     * @param millis The time in milliseconds.
     */
    private void timedHide(int millis) {
        new java.util.Timer(true)
                .schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                setText("");
                                cancel();
                            }
                        },
                        millis);
    }

}
