/* (C)2023 */
package org.gui;

import org.library.Pateron;

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
                    String user = usernameTextField.getText(); // TODO: -1 For Librarian
                    String pword = String.valueOf(passwordPasswordField.getPassword());
                    Pateron pateron = Pateron.getLoadedPaterons().get(Long.parseLong(user));
                    if (pateron.getPassword().equals(pword)) {
                        PateronPage page = new PateronPage(pateron, frame, this);
                        frame.setContentPane(page.getPanel());
                        frame.validate();
                        frame.repaint();
                        clear();
                    } // TODO: ELSE Password is wrong or user is wrong
                });
        clearButton.addActionListener(
                actionEvent -> {
                    clear();
                });
    }

    public JPanel getPanel() {
        return Library;
    }

    public void clear() {
        usernameTextField.setText("Username");
        passwordPasswordField.setText("Password");
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
