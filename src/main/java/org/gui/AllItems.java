/* (C)2023 */
package org.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import org.library.Book;
import org.library.DVD;
import org.library.Item;
import org.library.Magazine;

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

public class AllItems {
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JList<String> itemList;
    private JComboBox<String> typeCombo;
    private JPanel Panel;
    private JTextField searchBar;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public AllItems(JFrame frame, LibrarianPanel librarianPanel) {
        editButton.addActionListener(
                actionEvent -> {
                    int idx = itemList.getSelectedIndex();
                    long id = Item.getIDFromToString(itemList.getSelectedValue());
                    if (typeCombo.getSelectedItem() == null) return;
                    editItem(id, idx, frame);
                });
        deleteButton.addActionListener(
                actionEvent -> {
                    int idx = itemList.getSelectedIndex();
                    long id = Item.getIDFromToString(itemList.getSelectedValue());
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Books" -> deleteItem(id, idx, Book.getLoadedBooks());
                        case "Magazines" -> deleteItem(id, idx, Magazine.getLoadedMagazines());
                        case "DVDs" -> deleteItem(id, idx, DVD.getLoadedDVDs());
                    }
                });
        backButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(librarianPanel.getPanel());
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
    private void DocumentChanged() {
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
        itemList.setModel(listModel);
    }

    /**
     * Deletes the selected item from the passed loaded items hash map and removes it from the
     * active item list.
     *
     * @param id The id of the item to delete.
     * @param idx The index of the item in the list model.
     * @param loadedItems The hash map containing the item to delete.
     * @param <E> The child class of {@link Item}
     */
    private <E> void deleteItem(long id, int idx, HashMap<Long, E> loadedItems) {
        loadedItems.remove(id);
        listModel.remove(idx);
        itemList.revalidate();
        itemList.repaint();
    }

    /**
     * Creates an EditItem form with the selected item.
     *
     * @param id The id of the item to be edited.
     * @param idx The index of the item in the list model.
     * @param frame The LibrarianGUI frame.
     */
    private void editItem(long id, int idx, JFrame frame) {
        Item item = null;
        // Figure out which child class our item belongs to, so we can grab it from the active list.
        if (Book.getLoadedBooks().containsKey(id)) item = Book.getLoadedBooks().get(id);
        else if (Magazine.getLoadedMagazines().containsKey(id))
            item = Magazine.getLoadedMagazines().get(id);
        else if (DVD.getLoadedDVDs().containsKey(id)) item = DVD.getLoadedDVDs().get(id);
        if (item == null) return;
        // Create out edit form with that item!
        EditItem form = new EditItem(frame, this, item, idx);
        frame.setContentPane(form.getPanel());
        frame.validate();
        frame.repaint();
    }

    /**
     * Gets the objects panel property.
     *
     * @return The JPanel belonging to the object.
     */
    public JPanel getPanel() {
        return Panel;
    }

    /**
     * Gets the objects list model property.
     *
     * @return The DefaultListMod belonging to the object.
     */
    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    /**
     * Gets the objects list property.
     *
     * @return The JList belonging to the object.
     */
    public JList<String> getItemList() {
        return itemList;
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
        Panel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial Narrow", -1, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("All Library Items");
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                panel1,
                new GridConstraints(
                        4,
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
        editButton = new JButton();
        editButton.setText("Edit");
        panel1.add(
                editButton,
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
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        panel1.add(
                deleteButton,
                new GridConstraints(
                        0,
                        2,
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
        backButton = new JButton();
        backButton.setText("Back");
        panel1.add(
                backButton,
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
        itemList = new JList();
        scrollPane1.setViewportView(itemList);
        typeCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Select");
        defaultComboBoxModel1.addElement("Books");
        defaultComboBoxModel1.addElement("Magazines");
        defaultComboBoxModel1.addElement("DVDs");
        typeCombo.setModel(defaultComboBoxModel1);
        Panel.add(
                typeCombo,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        searchBar = new JTextField();
        Panel.add(
                searchBar,
                new GridConstraints(
                        3,
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
