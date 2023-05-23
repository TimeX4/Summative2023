/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;
import org.library.Patron;

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

    public PatronPage(Patron p, JFrame frame, LibraryGUI libraryGUI) {
        // TODO: 2023-05-18 Add search bars.
        Patron = p;
        revalidateModel();
        Back.addActionListener(
                actionEvent -> {
                    frame.setContentPane(libraryGUI.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        ViewAll.addActionListener(
                actionEvent -> {
                    AllItemsUser allBooks = new AllItemsUser(p, this, frame);
                    frame.setContentPane(allBooks.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        CheckIn.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null) return;
                    if (typeCombo.getSelectedItem().toString().equals("Select")) return;
                    if (typeCombo.getSelectedItem().toString().equals("Books"))
                        CheckInItem(checkedOutBooks);
                    else if (typeCombo.getSelectedItem().toString().equals("Magazines"))
                        CheckInItem(checkedOutMagazines);
                });
        typeCombo.addActionListener(
                actionEvent -> {
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Books" -> setModelMap(checkedOutBooks.entrySet());
                        case "Magazines" -> setModelMap(checkedOutMagazines.entrySet());
                        default -> setModelMap(null);
                    }
                });
    }

    public void revalidateModel() {
        for (Map.Entry<Long, LocalDate> entry : Patron.getCheckedOut().entrySet()) {
            Long key = entry.getKey();
            if (Book.getLoadedBooks().containsKey(key))
                checkedOutBooks.put(key, Book.getLoadedBooks().get(key));
            else if (Magazine.getLoadedMagazines().containsKey(key))
                checkedOutMagazines.put(key, Magazine.getLoadedMagazines().get(key));
        }
        if (typeCombo.getSelectedItem() == null) return;
        switch (typeCombo.getSelectedItem().toString()) {
            case "Books" -> setModelMap(checkedOutBooks.entrySet());
            case "Magazines" -> setModelMap(checkedOutMagazines.entrySet());
            default -> setModelMap(null);
        }
    }

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

    public String fancyString(long key) {
        Item item = null;
        String string = "ERROR";
        String unique = "";
        if (Book.getLoadedBooks().containsKey(key)) {
            item = Book.getLoadedBooks().get(key);
            unique = ((Book) item).getAuthor();
        } else if (Magazine.getLoadedMagazines().containsKey(key)) {
            item = Magazine.getLoadedMagazines().get(key);
            unique = "Renewal period: " + ((Magazine) item).getRenewalDate() + " days";
        }
        if (item == null) return string;
        LocalDate value = Patron.getCheckedOut().get(key);
        LocalDate due = value.plusDays(item.getMaxCheckoutDays());
        string = item.getTitle() + ", " + unique + " | Due: " + due.toString();
        return string;
    }

    public <E> void setModelMap(Set<Map.Entry<Long, E>> entrySet) {
        listModel.clear();
        if (entrySet == null) return;
        for (Map.Entry<Long, E> entry : entrySet) {
            Item item = (Item) entry.getValue();
            if (item != null) {
                listModel.addElement(fancyString(item.getID()));
            }
        }
        ItemList.setModel(listModel);
        ItemList.revalidate();
        ItemList.repaint();
    }

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
        Panel.setLayout(
                new com.intellij.uiDesigner.core.GridLayoutManager(
                        4, 1, new Insets(0, 0, 0, 0), -1, -1));
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(
                new com.intellij.uiDesigner.core.GridLayoutManager(
                        1, 3, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                ButtonPanel,
                new com.intellij.uiDesigner.core.GridConstraints(
                        3,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        Back = new JButton();
        Back.setText("Logout");
        ButtonPanel.add(
                Back,
                new com.intellij.uiDesigner.core.GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        CheckIn = new JButton();
        CheckIn.setText("Check In");
        ButtonPanel.add(
                CheckIn,
                new com.intellij.uiDesigner.core.GridConstraints(
                        0,
                        2,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        ViewAll = new JButton();
        ViewAll.setText("View Library");
        ButtonPanel.add(
                ViewAll,
                new com.intellij.uiDesigner.core.GridConstraints(
                        0,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        HeaderPanel = new JPanel();
        HeaderPanel.setLayout(
                new com.intellij.uiDesigner.core.GridLayoutManager(
                        1, 1, new Insets(0, 0, 0, 0), -1, -1));
        Panel.add(
                HeaderPanel,
                new com.intellij.uiDesigner.core.GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
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
                new com.intellij.uiDesigner.core.GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        ListScroller = new JScrollPane();
        Panel.add(
                ListScroller,
                new com.intellij.uiDesigner.core.GridConstraints(
                        2,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
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
        typeCombo.setModel(defaultComboBoxModel1);
        Panel.add(
                typeCombo,
                new com.intellij.uiDesigner.core.GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
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
