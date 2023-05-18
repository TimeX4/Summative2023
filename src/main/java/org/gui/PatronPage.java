/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Magazine;
import org.library.Patron;

import java.time.LocalDate;
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
    private JComboBox typeCombo;
    private final Patron Patron;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public PatronPage(Patron p, JFrame frame, LibraryGUI libraryGUI) {
        // TODO: 2023-05-18 Patron page must be rewritten to support all item sub-classes. Add search bars. Add support for ReferenceOnly
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
                    int idx = ItemList.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, LocalDate> entry : p.getCheckedOut().entrySet()) {
                        Long key = entry.getKey();
                        Book b = Book.getLoadedBooks().get(key);
                        if (i == idx) {
                            float due = p.CheckIn(b);
                            System.out.println(due); // TODO: Tell them due on GUI.
                            listModel.remove(idx);
                            ItemList.setModel(listModel);
                            ItemList.revalidate();
                            ItemList.repaint();
                        }
                        i++;
                    }
                });
        typeCombo.addActionListener(actionEvent -> {
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> setModelMap(Book.getLoadedBooks().entrySet());
                case "Magazines" -> setModelMap(Magazine.getLoadedMagazines().entrySet());
                default -> setModelMap(null);
            }
        });
    }

    public void revalidateModel() {
        listModel.clear();
        Patron.getCheckedOut()
                .forEach(
                        (key, value) -> {
                            Book b = Book.getLoadedBooks().get(key);
                            if (b != null) {
                                LocalDate due = value.plusDays(b.getMaxCheckoutDays());
                                String str =
                                        b.getTitle()
                                                + ", "
                                                + b.getAuthor()
                                                + " | Due: "
                                                + due.toString();
                                listModel.addElement(str);
                            }
                        });
        ItemList.setModel(listModel);
    }

    public <E> void setModelMap(Set<Map.Entry<Long, E>> entrySet) {
        listModel.clear();
        if (entrySet == null) return;
        for (Map.Entry<Long, E> entry : entrySet) {
            E item = entry.getValue();
            if (item != null) {
                listModel.addElement(item.toString());
            }
        }
        ItemList.setModel(listModel);
    }

    public JPanel getPanel() {
        return Panel;
    }
}
