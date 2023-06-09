/* (C)2023 */
package org.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import org.file.Parser;
import org.library.Book;
import org.library.DVD;
import org.library.Item;
import org.library.Magazine;

import java.awt.*;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

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
    private JComboBox<String> comboBox1;
    private JLabel referenceOnlyLabel;
    private Notifier notificationText;

    public EditItem(JFrame frame, AllItems allItems, Item item, int idx) {
        // Determine what child class the item is and adjust the fields appropriately.
        $$$setupUI$$$();
        if (item instanceof Book) {
            authorRenewalLabel.setText("Author");
            authorField.setText(((Book) item).getAuthor());
        } else if (item instanceof Magazine) {
            authorRenewalLabel.setText("Renewal Days");
            authorField.setText(String.valueOf(((Magazine) item).getRenewalDate()));
        } else if (item instanceof DVD) {
            authorRenewalLabel.setText("Length (Minutes)");
            authorField.setText(String.valueOf(((DVD) item).getLength()));
        }
        nameField.setText(item.getTitle());
        copiesField.setText(String.valueOf(item.getCopies()));
        checkoutField.setText(String.valueOf(item.getMaxCheckoutDays()));
        comboBox1.setSelectedItem(item.getReferenceOnly() ? "true" : "false");
        cancelButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(allItems.getPanel());
                    frame.revalidate();
                    frame.repaint();
                });
        confirmButton.addActionListener(
                actionEvent -> {
                    String name = nameField.getText();
                    if (name.isBlank()) {
                        notificationText.showText("Name cannot be empty.", 2000);
                        return;
                    }
                    AtomicInteger copies = new AtomicInteger();
                    if (!Parser.GetInt(copiesField.getText(), copies, false)) {
                        notificationText.showText("Invalid copies.", 2000);
                        return;
                    }
                    AtomicInteger checkoutDays = new AtomicInteger();
                    if (!Parser.GetInt(checkoutField.getText(), checkoutDays, false)) {
                        notificationText.showText("Invalid checkout days.", 2000);
                        return;
                    }
                    if (checkoutDays.get() == 0) {
                        notificationText.showText("Cannot have 0 checkout days.", 2000);
                        return;
                    }
                    boolean refOnly = false;
                    if (comboBox1.getSelectedItem() != null)
                        if (comboBox1.getSelectedItem().toString().equals("true")) refOnly = true;
                    if (item instanceof Book) {
                        if (Book.isDuplicate(name, authorField.getText(), refOnly)) {
                            notificationText.showText("This book already exists.", 2000);
                            return;
                        }
                    }
                    item.setTitle(name);
                    item.setCopies(copies.get());
                    item.setMaxCheckoutDays(checkoutDays.get());
                    item.setReferenceOnly(refOnly);
                    // Handle the case of the special fields.
                    if (item instanceof Book) {
                        if (authorField.getText().isBlank()) {
                            notificationText.showText("Author cannot be empty.", 2000);
                            return;
                        }
                        ((Book) item).setAuthor(authorField.getText());
                    } else if (item instanceof Magazine) {
                        AtomicInteger renewalDate = new AtomicInteger();
                        if (!Parser.GetInt(authorField.getText(), renewalDate, false)) {
                            notificationText.showText("Invalid renewal date.", 2000);
                            return;
                        }
                        if (renewalDate.get() == 0 && !refOnly) {
                            notificationText.showText("Cannot add a book with no renewal period.", 2000);
                            return;
                        }
                        ((Magazine) item).setRenewalDate(renewalDate.get());
                    } else if (item instanceof DVD) {
                        AtomicInteger length = new AtomicInteger();
                        if (!Parser.GetInt(authorField.getText(), length, false)) {
                            notificationText.showText("Invalid length.", 2000);
                            return;
                        }
                        if (length.get() == 0) {
                            notificationText.showText("Cannot have a length of 0.", 2000);
                            return;
                        }
                        ((DVD) item).setLength(length.get());
                    }
                    // Update the edited item wherever needed!
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

    /**
     * Gets the objects panel property.
     *
     * @return The JPanel belonging to the object.
     */
    public JPanel getPanel() {
        return Panel;
    }

    private void createUIComponents() {
        notificationText = new Notifier();
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        Panel = new JPanel();
        Panel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial Narrow", -1, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Edit Item");
        Panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel1.add(confirmButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel1.add(cancelButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        Panel.add(scrollPane1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(panel2);
        nameField = new JTextField();
        panel2.add(nameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        panel2.add(nameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Copies");
        panel2.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copiesField = new JTextField();
        panel2.add(copiesField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        authorRenewalLabel = new JLabel();
        authorRenewalLabel.setText("Author");
        panel2.add(authorRenewalLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        authorField = new JTextField();
        authorField.setText("");
        panel2.add(authorField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        checkoutField = new JTextField();
        panel2.add(checkoutField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Max Checkout Days");
        panel2.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("false");
        defaultComboBoxModel1.addElement("true");
        comboBox1.setModel(defaultComboBoxModel1);
        panel2.add(comboBox1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        referenceOnlyLabel = new JLabel();
        referenceOnlyLabel.setText("Reference Only");
        panel2.add(referenceOnlyLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Panel.add(notificationText, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
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
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Panel;
    }

}
