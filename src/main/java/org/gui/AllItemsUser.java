/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;
import org.library.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

public class AllItemsUser {
    private JPanel AllBooksPanel;
    private JList<String> ItemsList;
    private JPanel ButtonPanel;
    private JLabel TItle;
    private JScrollPane ItemsScroller;
    private JButton checkoutButton;
    private JButton homeButton;
    private JComboBox<String> typeCombo;
    private JTextField searchBar;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final Patron Patron;

// TODO: 2023-05-19 find a way to know which book is selected regardless of the order, sort?, add id to the tostring and parse?, scrap the sorter cuz im lazy af. 
    
    public AllItemsUser(Patron p, PatronPage PatronPage, JFrame frame) {
        Patron = p;
        checkoutButton.addActionListener(
                actionEvent -> {
                    int idx = ItemsList.getSelectedIndex();
                    if (typeCombo.getSelectedItem() == null) return;
                    switch (typeCombo.getSelectedItem().toString()) {
                        case "Books" -> checkoutItem(idx, Book.getLoadedBooks().entrySet(), frame, PatronPage);
                        case "Magazines" -> checkoutItem(idx, Magazine.getLoadedMagazines().entrySet(), frame, PatronPage);
                    }
                });
        homeButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(PatronPage.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        typeCombo.addActionListener(actionEvent -> {
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> setModelMap(Book.getLoadedBooks().entrySet());
                case "Magazines" -> setModelMap(Magazine.getLoadedMagazines().entrySet());
                default -> setModelMap(null);
            }
        });
        searchBar.addActionListener(e -> {
            String filter = searchBar.getText();
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> FilterModel(listModel, filter, Book.getLoadedBooks());
                case "Magazines" -> FilterModel(listModel, filter, Magazine.getLoadedMagazines());
            }
        });
    }

    public <E> void FilterModel(DefaultListModel<String> model, String filter, HashMap<Long, E> map) {
        for (Map.Entry<Long, E> entry : map.entrySet()) {
            String line = entry.getValue().toString();
            if (!line.startsWith(filter)) {
                if (model.contains(line)) {
                    model.removeElement(line);
                }
            } else {
                if (!model.contains(line)) {
                    model.addElement(line);
                }
            }
        }
    }

    private <E> void checkoutItem(int idx, Set<Map.Entry<Long, E>> entrySet, JFrame frame, PatronPage PatronPage) {
        int i = 0;
        for (Map.Entry<Long, E> entry : entrySet) {
            Long key = entry.getKey();
            Item item = null;
            if (entry.getValue() instanceof Book) {
                item = Book.getLoadedBooks().get(key);
            } else if (entry.getValue() instanceof Magazine) {
                item = Magazine.getLoadedMagazines().get(key);
            }
            if (item == null) return;
            if (i == idx) {
                Patron.CheckOut(item);
                PatronPage.revalidateModel();
                System.out.println("Checked out"); // TODO: Tell them due on GUI.
                listModel.remove(idx);
                listModel.insertElementAt(item.toString(), idx);
                ItemsList.setModel(listModel);
                ItemsList.revalidate();
                ItemsList.repaint();
            }
            i++;
        }
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
        ItemsList.setModel(listModel);
    }

    public JPanel getPanel() {
        return AllBooksPanel;
    }
}
