/* (C)2023 */
package org.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LibraryGUI {
    private JPanel Library;
    private JButton loginButton;
    private JButton clearButton;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;

    public LibraryGUI() {
        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {}
                });
    }

    public static void draw() {
        JFrame frame = new JFrame("LibraryGUI");
        frame.setContentPane(new LibraryGUI().Library);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 500);
        frame.pack();
        frame.setVisible(true);
    }
}
