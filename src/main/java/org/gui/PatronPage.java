/* (C)2023 */
package org.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import org.library.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

public class PatronPage {
    private JButton Back;
    private JButton CheckIn;
    private JButton ViewAll;
    private JPanel Panel;
    private JList<String> ItemList;
    private JScrollPane ListScroller;
    private JPanel HeaderPanel;
    private JPanel ButtonPanel;
    private JComboBox<String> typeCombo;
    private final Patron Patron;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final HashMap<Long, Book> checkedOutBooks = new HashMap<>();
    private final HashMap<Long, Magazine> checkedOutMagazines = new HashMap<>();
    private final HashMap<Long, DVD> checkedOutDVDs = new HashMap<>();

    public PatronPage(Patron p, JFrame frame, LibraryGUI libraryGUI) {
        Patron = p;
        revalidateModel();
        Back.addActionListener(
                actionEvent -> {
                    // Go back to the main libraryGUI panel.
                    frame.setContentPane(libraryGUI.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        ViewAll.addActionListener(
                actionEvent -> {
                    // View all the items in the library.
                    AllItemsUser allItems = new AllItemsUser(p, this, frame);
                    frame.setContentPane(allItems.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        CheckIn.addActionListener(
                actionEvent -> {
                    // Check in the selected item.
                    if (typeCombo.getSelectedItem() == null) return;
                    if (typeCombo.getSelectedItem().toString().equals("Select")) return;
                    if (typeCombo.getSelectedItem().toString().equals("Books"))
                        CheckInItem(checkedOutBooks);
                    else if (typeCombo.getSelectedItem().toString().equals("Magazines"))
                        CheckInItem(checkedOutMagazines);
                    else if (typeCombo.getSelectedItem().toString().equals("DVD"))
                        CheckInItem(checkedOutDVDs);
                });
        typeCombo.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Books" -> setModelMap(checkedOutBooks.entrySet());
                        case "Magazines" -> setModelMap(checkedOutMagazines.entrySet());
                        case "DVDs" -> setModelMap(checkedOutDVDs.entrySet());
                        default -> setModelMap(null);
                    }
                });
    }

    /**
     * Iterates over the Patron's checked out list and repopulate the list models based on which
     * child class the ID is found in.
     */
    public void revalidateModel() {
        for (Map.Entry<Long, LocalDate> entry : Patron.getCheckedOut().entrySet()) {
            Long key = entry.getKey();
            // Check which child class of Item the ID belongs to.
            if (Book.getLoadedBooks().containsKey(key))
                checkedOutBooks.put(key, Book.getLoadedBooks().get(key));
            else if (Magazine.getLoadedMagazines().containsKey(key))
                checkedOutMagazines.put(key, Magazine.getLoadedMagazines().get(key));
            else if (DVD.getLoadedDVDs().containsKey(key))
                checkedOutDVDs.put(key, DVD.getLoadedDVDs().get(key));
        }
        // Check which model is selected and render it appropriately.
        if (typeCombo.getSelectedItem() == null) return;
        switch (typeCombo.getSelectedItem().toString()) {
            case "Books" -> setModelMap(checkedOutBooks.entrySet());
            case "Magazines" -> setModelMap(checkedOutMagazines.entrySet());
            case "DVDs" -> setModelMap(checkedOutDVDs.entrySet());
            default -> setModelMap(null);
        }
    }

    /**
     * Checked in the selected item.
     *
     * @param map The hash map of loaded items corresponding to the model.
     * @param <E> The child class of the {@link Item}.
     */
    private <E> void CheckInItem(HashMap<Long, E> map) {
        int idx = ItemList.getSelectedIndex();
        int i = 0;
        for (Map.Entry<Long, E> entry : map.entrySet()) {
            Long key = entry.getKey();
            Item value = (Item) entry.getValue();
            if (i == idx) {
                float due = Patron.CheckIn(value);
                System.out.println(due); // TODO: Tell them due on GUI.
                map.remove(key);
                setModelMap(map.entrySet());
            }
            i++;
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
        ItemList.setModel(listModel);
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
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                ButtonPanel,
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
        Back = new JButton();
        Back.setText("Logout");
        ButtonPanel.add(
                Back,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        CheckIn = new JButton();
        CheckIn.setText("Check In");
        ButtonPanel.add(
                CheckIn,
                new GridConstraints(
                        0,
                        2,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        ViewAll = new JButton();
        ViewAll.setText("View Library");
        ButtonPanel.add(
                ViewAll,
                new GridConstraints(
                        0,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        HeaderPanel = new JPanel();
        HeaderPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                HeaderPanel,
                new GridConstraints(
                        0,
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
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial Narrow", -1, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Checked Out");
        HeaderPanel.add(
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
        ListScroller = new JScrollPane();
        Panel.add(
                ListScroller,
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
        ItemList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        ItemList.setModel(defaultListModel1);
        ItemList.setSelectionMode(0);
        ListScroller.setViewportView(ItemList);
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
