package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItem {
    private JTextField nameField;
    private JTextField copiesField;
    private JTextField authorField;
    private JTextField checkoutField;
    private JLabel nameLabel;
    private JLabel authorRenewalLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel Panel;

    public EditItem(JFrame frame, AllItems allItems, Item item, int idx) {
        if (item instanceof Book) {
            authorRenewalLabel.setText("Author");
            authorField.setText(((Book) item).getAuthor());
        } else if (item instanceof Magazine) {
            authorRenewalLabel.setText("Renewal Days");
            authorField.setText(String.valueOf(((Magazine)item).getRenewalDate()));
        }
        nameField.setText(item.getTitle());
        copiesField.setText(String.valueOf(item.getCopies()));
        checkoutField.setText(String.valueOf(item.getMaxCheckoutDays()));

        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(allItems.getPanel());
            frame.revalidate();
            frame.repaint();
        });
        confirmButton.addActionListener(actionEvent -> {
            // TODO: Safely handle number parsing conversion stuff
            String name = nameField.getText();
            int copies = Integer.parseInt(copiesField.getText());
            int checkoutDays = Integer.parseInt(checkoutField.getText());
            item.setTitle(name);
            item.setCopies(copies);
            item.setMaxCheckoutDays(checkoutDays);
            if (item instanceof Book) {
                ((Book)item).setAuthor(authorField.getText());
            } else if (item instanceof Magazine) {
                int renewalDate = Integer.parseInt(authorField.getText());
                ((Magazine)item).setRenewalDate(renewalDate);
            }
            allItems.getListModel().remove(idx);
            allItems.getListModel().insertElementAt(item.toString(), idx);
            allItems.getItemList().setModel(allItems.getListModel());
            allItems.getItemList().revalidate();
            allItems.getItemList().repaint();
            frame.setContentPane(allItems.getPanel());
            frame.validate();
            frame.repaint();
        });
    }

    public JPanel getPanel() {
        return Panel;
    }
}
