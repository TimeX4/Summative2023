/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;
import org.library.Patron;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

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
        typeCombo.addActionListener(actionEvent -> {
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
            Item value = (Item)entry.getValue();
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
        }
        else if (Magazine.getLoadedMagazines().containsKey(key)) {
            item = Magazine.getLoadedMagazines().get(key);
            unique = "Renewal period: " + ((Magazine) item).getRenewalDate() + " days";
        }
        if (item == null)
            return string;
        LocalDate value = Patron.getCheckedOut().get(key);
        LocalDate due = value.plusDays(item.getMaxCheckoutDays());
        string = item.getTitle() + ", " + unique + " | Due: " + due.toString();
        return string;
    }

    public <E> void setModelMap(Set<Map.Entry<Long, E>> entrySet) {
        listModel.clear();
        if (entrySet == null) return;
        for (Map.Entry<Long, E> entry : entrySet) {
            Item item = (Item)entry.getValue();
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
}
