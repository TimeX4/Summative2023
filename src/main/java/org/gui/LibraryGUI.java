/* (C)2023 */
package org.gui;

import javax.swing.*;

public class LibraryGUI {
    private JPanel Library;
    private JButton loginButton;
    private JButton clearButton;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;

    public LibraryGUI(JFrame frame) {
        loginButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(PateronPage.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public static void draw() {
        JFrame frame = new JFrame("LibraryGUI");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        frame.setContentPane(new LibraryGUI(frame).Library);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 500);
        frame.pack();
        frame.setVisible(true);
    }
}
