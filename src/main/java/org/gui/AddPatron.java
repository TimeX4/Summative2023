package org.gui;

import org.file.Encryptor;
import org.library.Patron;

import javax.swing.*;

public class AddPatron {
    private JButton addButton;
    private JButton cancelButton;
    private JTextField nameText;
    private JTextField numberText;
    private JPasswordField passwordText;
    private JPanel Panel;

    public AddPatron(JFrame frame, LibrarianPanel librarianPanel) {
        addButton.addActionListener(actionEvent -> {
            String name = nameText.getText();
            String number = numberText.getText();
            String password = Encryptor.SHA512(String.valueOf(passwordText.getPassword()));
            Patron p = new Patron(name, number, password);
            Patron.getLoadedPatrons().put(p.getID(), p);
            frame.setContentPane(librarianPanel.getPanel());
            frame.validate();
            frame.repaint();
        });
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(librarianPanel.getPanel());
            frame.validate();
            frame.repaint();
        });
    }

    public JPanel getPanel() {
        return Panel;
    }
}
