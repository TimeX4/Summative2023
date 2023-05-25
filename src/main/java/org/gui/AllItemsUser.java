/* (C)2023 */
package org.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import org.library.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

public class AllItemsUser {
    private JPanel AllBooksPanel;
    private JList<String> ItemsList;
    private JPanel ButtonPanel;
    private JLabel Title;
    private JScrollPane ItemsScroller;
    private JButton checkoutButton;
    private JButton homeButton;
    private JComboBox<String> typeCombo;
    private JTextField searchBar;
    private Notifier notificationText;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final Patron Patron;

    public AllItemsUser(Patron p, PatronPage PatronPage, JFrame frame) {
        Patron = p;
        $$$setupUI$$$();
        checkoutButton.addActionListener(
                actionEvent -> {
                    int idx = ItemsList.getSelectedIndex();
                    String selected = ItemsList.getSelectedValue();
                    if (selected == null || selected.equals("")) return;
                    long id = Item.getIDFromToString(selected);
                    if (typeCombo.getSelectedItem() == null) return;
                    checkoutItem(id, idx, PatronPage);
                });
        homeButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(PatronPage.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        typeCombo.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Books" -> setModelMap(Book.getLoadedBooks().entrySet());
                        case "Magazines" -> setModelMap(Magazine.getLoadedMagazines().entrySet());
                        case "DVDs" -> setModelMap(DVD.getLoadedDVDs().entrySet());
                        default -> setModelMap(null);
                    }
                });
        searchBar
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                DocumentChanged();
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                DocumentChanged();
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {}
                        });
    }

    /**
     * Calls the appropriate {@link #FilterModel(DefaultListModel, String, HashMap)} based on which
     * Document is selected.
     */
    public void DocumentChanged() {
        String filter = searchBar.getText();
        if (typeCombo.getSelectedItem() == null) return;
        switch (typeCombo.getSelectedItem().toString()) {
            case "Books" -> FilterModel(listModel, filter, Book.getLoadedBooks());
            case "Magazines" -> FilterModel(listModel, filter, Magazine.getLoadedMagazines());
            case "DVDs" -> FilterModel(listModel, filter, DVD.getLoadedDVDs());
        }
    }

    /**
     * Filters the active model based on the given search.
     *
     * @param model The list model to be updated.
     * @param filter The text to filter by.
     * @param map The map containing all the items.
     * @param <E> The child class of {@link Item}
     */
    private <E> void FilterModel(
            DefaultListModel<String> model, String filter, HashMap<Long, E> map) {
        for (Map.Entry<Long, E> entry : map.entrySet()) {
            // Iterate over the loaded items and get the current string.
            String line = entry.getValue().toString();
            // If our line doesn't contain the active search, remove it.
            if (!line.contains(filter)) {
                if (model.contains(line)) {
                    model.removeElement(line);
                }
            } else {
                // If the model doesn't already have our current line, add it.
                if (!model.contains(line)) {
                    model.addElement(line);
                }
            }
        }
    }

    /**
     * Checks out the selected item.
     *
     * @param id The id of the item.
     * @param idx The index of the item in the active list model.
     * @param PatronPage The PatronPage belonging to the patron, used to update the checked out list
     *     on that page. See {@link PatronPage#revalidateModel()}
     */
    private void checkoutItem(long id, int idx, PatronPage PatronPage) {
        Item item = null;
        // Figure out which child class our item belongs to, so we can grab it from the active list.
        if (Book.getLoadedBooks().containsKey(id)) item = Book.getLoadedBooks().get(id);
        else if (Magazine.getLoadedMagazines().containsKey(id))
            item = Magazine.getLoadedMagazines().get(id);
        else if (DVD.getLoadedDVDs().containsKey(id)) item = DVD.getLoadedDVDs().get(id);
        if (item == null) return;
        // Checkout that item and handle status codes.
        switch (Patron.CheckOut(item)) {
            case 1 -> notificationText.showText("Item is reference only.", 2000);
            case 2 -> notificationText.showText("Item is out of stock.", 2000);
            case 3 -> notificationText.showText("Item already checked out.", 2000);
            default -> notificationText.showText("Checked out " + item.getTitle(), 2000);
        }
        // Revalidate the model on the PatronPage
        PatronPage.revalidateModel();
        // Update the model to reflect the new quantity.
        listModel.remove(idx);
        listModel.insertElementAt(item.toString(), idx);
        ItemsList.setModel(listModel);
        ItemsList.revalidate();
        ItemsList.repaint();
    }

    /**
     * Updates the active model map to the respective hashmap of items.
     *
     * @param entrySet The entry set of the hash map.
     * @param <E> The child class of {@link Item}
     */
    private <E> void setModelMap(Set<Map.Entry<Long, E>> entrySet) {
        listModel.clear();
        if (entrySet == null) return;
        // Iterate over the loaded items list and add each item's to string.
        for (Map.Entry<Long, E> entry : entrySet) {
            E item = entry.getValue();
            if (item != null) {
                listModel.addElement(item.toString());
            }
        }
        ItemsList.setModel(listModel);
    }

    /**
     * Gets the objects panel property.
     *
     * @return The JPanel belonging to the object.
     */
    public JPanel getPanel() {
        return AllBooksPanel;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
     * call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        AllBooksPanel = new JPanel();
        AllBooksPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        ItemsScroller = new JScrollPane();
        AllBooksPanel.add(
                ItemsScroller,
                new GridConstraints(
                        3,
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
        ItemsList = new JList();
        ItemsScroller.setViewportView(ItemsList);
        Title = new JLabel();
        Font TitleFont = this.$$$getFont$$$("Arial Narrow", Font.PLAIN, 20, Title.getFont());
        if (TitleFont != null) Title.setFont(TitleFont);
        Title.setHorizontalTextPosition(11);
        Title.setText("All Library Items");
        AllBooksPanel.add(
                Title,
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
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        AllBooksPanel.add(
                ButtonPanel,
                new GridConstraints(
                        5,
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
        checkoutButton = new JButton();
        checkoutButton.setText("Checkout");
        ButtonPanel.add(
                checkoutButton,
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
        homeButton = new JButton();
        homeButton.setText("Home");
        ButtonPanel.add(
                homeButton,
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
        typeCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Select");
        defaultComboBoxModel1.addElement("Books");
        defaultComboBoxModel1.addElement("Magazines");
        defaultComboBoxModel1.addElement("DVDs");
        typeCombo.setModel(defaultComboBoxModel1);
        AllBooksPanel.add(
                typeCombo,
                new GridConstraints(
                        2,
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
        searchBar = new JTextField();
        searchBar.setToolTipText("Search bar");
        AllBooksPanel.add(
                searchBar,
                new GridConstraints(
                        4,
                        0,
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
        AllBooksPanel.add(
                notificationText,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
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
        return AllBooksPanel;
    }

    private void createUIComponents() {
        notificationText = new Notifier();
    }
}
