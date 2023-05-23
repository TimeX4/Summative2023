/* (C)2023 */
package org.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import org.library.Book;
import org.library.DVD;
import org.library.Magazine;

import java.awt.*;
import java.util.Locale;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

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
        cancelButton.addActionListener(
                actionEvent -> {
                    // Go back to the general librarian page.
                    frame.setContentPane(librarianPanel.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        confirmButton.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null
                            || typeCombo.getSelectedItem().toString().equals("")) return;
                    String selected = typeCombo.getSelectedItem().toString();
                    int copies = Integer.parseInt(copiesField.getText());
                    int checkoutDays = Integer.parseInt(maxCheckoutDaysField.getText());
                    switch (selected) {
                        case "Book" -> {
                            Book b =
                                    new Book(
                                            nameField.getText(),
                                            authorRenewalFiled.getText(),
                                            copies,
                                            checkoutDays);
                            Book.getLoadedBooks().put(b.getID(), b);
                        }
                        case "Magazine" -> {
                            int renewalDays = Integer.parseInt(authorRenewalFiled.getText());
                            Magazine m =
                                    new Magazine(
                                            nameField.getText(), copies, checkoutDays, renewalDays);
                            Magazine.getLoadedMagazines().put(m.getID(), m);
                        }
                        case "DVDs" -> {
                            int length = Integer.parseInt(authorRenewalFiled.getText());
                            DVD d = new DVD(nameField.getText(), length, copies, checkoutDays);
                            DVD.getLoadedDVDs().put(d.getID(), d);
                        }
                    }
                    frame.setContentPane(librarianPanel.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        typeCombo.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Book" -> authorRenewalLabel.setText("Author");
                        case "Magazine" -> authorRenewalLabel.setText("Renewal Days");
                        case "DVDs" -> authorRenewalLabel.setText("Length");
                        default -> authorRenewalLabel.setText("ERROR");
                    }
                });
    }

    /**
     * Gets the objects panel property.
     *
     * @return The JPanel belonging to the object.
     */
    public JPanel getPanel() {
        return Panel;
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
     * call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Panel = new JPanel();
        Panel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                panel1,
                new GridConstraints(
                        3,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel1.add(
                confirmButton,
                new GridConstraints(
                        0,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel1.add(
                cancelButton,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial Narrow", -1, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Add Item");
        Panel.add(
                label1,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JScrollPane scrollPane1 = new JScrollPane();
        Panel.add(
                scrollPane1,
                new GridConstraints(
                        2,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(panel2);
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        panel2.add(
                nameLabel,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        nameField = new JTextField();
        panel2.add(
                nameField,
                new GridConstraints(
                        0,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false));
        authorRenewalLabel = new JLabel();
        authorRenewalLabel.setText("Author");
        panel2.add(
                authorRenewalLabel,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        authorRenewalFiled = new JTextField();
        panel2.add(
                authorRenewalFiled,
                new GridConstraints(
                        1,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false));
        copiesLabel = new JLabel();
        copiesLabel.setText("Copies");
        panel2.add(
                copiesLabel,
                new GridConstraints(
                        2,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        copiesField = new JTextField();
        panel2.add(
                copiesField,
                new GridConstraints(
                        2,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false));
        maxCheckoutDaysLabel = new JLabel();
        maxCheckoutDaysLabel.setText("Max Checkout Days");
        panel2.add(
                maxCheckoutDaysLabel,
                new GridConstraints(
                        3,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        maxCheckoutDaysField = new JTextField();
        panel2.add(
                maxCheckoutDaysField,
                new GridConstraints(
                        3,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false));
        typeCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Book");
        defaultComboBoxModel1.addElement("Magazine");
        defaultComboBoxModel1.addElement("DVDs");
        typeCombo.setModel(defaultComboBoxModel1);
        Panel.add(
                typeCombo,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
    }

    /** @noinspection ALL */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font =
                new Font(
                        resultName,
                        style >= 0 ? style : currentFont.getStyle(),
                        size >= 0 ? size : currentFont.getSize());
        boolean isMac =
                System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback =
                isMac
                        ? new Font(font.getFamily(), font.getStyle(), font.getSize())
                        : new StyleContext()
                                .getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource
                ? fontWithFallback
                : new FontUIResource(fontWithFallback);
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$() {
        return Panel;
    }
}
