package org.gui;

import org.library.Book;
import org.library.Magazine;

import javax.swing.*;

public class AddItem {
    private JComboBox<String> typeCombo;
    private JTextField nameField;
    private JTextField authorRenewalFiled;
    private JTextField copiesField;
    private JTextField maxCheckoutDaysField;
    private JLabel nameLabel;
    private JLabel authorRenewalLabel;
    private JLabel copiesLabel;
    private JLabel maxCheckoutDaysLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel Panel;

    public AddItem(JFrame frame, LibrarianPanel librarianPanel) {
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(librarianPanel.getPanel());
            frame.validate();
            frame.repaint();
        });
        confirmButton.addActionListener(actionEvent -> {
            if (typeCombo.getSelectedItem() == null) return;
            String selected = typeCombo.getSelectedItem().toString();
            // TODO: Safely handle number parsing conversion stuff
            int copies = Integer.parseInt(copiesField.getText());
            int checkoutDays = Integer.parseInt(maxCheckoutDaysField.getText());
            if (selected.equals("Book")) {
                Book b = new Book(nameField.getText(), authorRenewalFiled.getText(), copies, checkoutDays);
                Book.getLoadedBooks().put(b.getID(), b);
            } else if (selected.equals("Magazine")) {
                int renewalDays = Integer.parseInt(authorRenewalFiled.getText());
                Magazine m = new Magazine(nameField.getText(), copies, checkoutDays, renewalDays);
                Magazine.getLoadedMagazines().put(m.getID(), m);
            }
            frame.setContentPane(librarianPanel.getPanel());
            frame.validate();
            frame.repaint();
        });
        typeCombo.addActionListener(actionEvent -> {
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Book" -> authorRenewalLabel.setText("Author");
                case "Magazine" -> authorRenewalLabel.setText("Renewal Days");
                default -> authorRenewalLabel.setText("ERROR");
            }
        });
    }

    public JPanel getPanel() {
        return Panel;
    }
}
