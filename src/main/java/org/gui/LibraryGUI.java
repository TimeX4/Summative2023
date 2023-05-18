/* (C)2023 */
package org.gui;

import org.library.Patron;

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
                    String user = usernameTextField.getText();
                    String pword = String.valueOf(passwordPasswordField.getPassword());
                    if (user.equals("-1")) {
                        if (!pword.equals("librarian"))
                            return;
                        LibrarianPanel page = new LibrarianPanel(frame, this);
                        frame.setContentPane(page.getPanel());
                        frame.validate();
                        frame.repaint();
                        clear();
                        return;
                    }
                    long id = Long.parseLong(user);
                    if (!Patron.getLoadedPatrons().containsKey(id)) {
                        usernameTextField.setText("Patron not found");
                        return;
                    }
                    Patron patron = Patron.getLoadedPatrons().get(id);
                    if (patron.getPassword().equals(pword)) {
                        PatronPage page = new PatronPage(patron, frame, this);
                        frame.setContentPane(page.getPanel());
                        frame.validate();
                        frame.repaint();
                        clear();
                    } else usernameTextField.setText("Incorrect Password");
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
