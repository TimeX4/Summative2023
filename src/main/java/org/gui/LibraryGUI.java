/* (C)2023 */
package org.gui;

import org.file.Encryptor;
import org.library.Main;
import org.library.Patron;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                    String encrypted_pword = Encryptor.SHA512(pword);
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
                    if (patron.getPassword().equals(encrypted_pword)) {
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.cleanup();
            }
        });
        frame.setSize(200, 500);
        frame.pack();
        frame.setVisible(true);
    }
}
