package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;
import org.library.Patron;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AllItems {
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JList<String> itemList;
    private JComboBox<String> typeCombo;
    private JPanel Panel;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public AllItems(JFrame frame, LibrarianPanel librarianPanel) {
        editButton.addActionListener(actionEvent -> {
            int idx = itemList.getSelectedIndex();
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> editItem(idx, Book.getLoadedBooks().entrySet(), frame);
                case "Magazines" -> editItem(idx, Magazine.getLoadedMagazines().entrySet(), frame);
            }
        });
        deleteButton.addActionListener(actionEvent -> {
            int idx = itemList.getSelectedIndex();
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> deleteItem(idx, Book.getLoadedBooks().entrySet(), Book.getLoadedBooks());
                case "Magazines" -> deleteItem(idx, Magazine.getLoadedMagazines().entrySet(), Magazine.getLoadedMagazines());
            }
        });
        backButton.addActionListener(actionEvent -> {
            frame.setContentPane(librarianPanel.getPanel());
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
        itemList.setModel(listModel);
    }

    public <E> void deleteItem(int idx, Set<Map.Entry<Long, E>> entrySet, HashMap<Long, E> loadedItems) {
        int i = 0;
        for (Map.Entry<Long, E> entry : entrySet) {
            if (i == idx) {
                Long id = entry.getKey();
                loadedItems.remove(id);
                listModel.remove(idx);
                itemList.revalidate();
                itemList.repaint();
                return;
            }
            i++;
        }
    }

    public <E> void editItem(int idx, Set<Map.Entry<Long, E>> entrySet, JFrame frame) {
        int i = 0;
        for (Map.Entry<Long, E> entry : entrySet) {
            if (i == idx) {
                EditItem form = new EditItem(frame, this, (Item)entry.getValue(), idx);
                frame.setContentPane(form.getPanel());
                frame.validate();
                frame.repaint();
                return;
            }
            i++;
        }
    }

    public JPanel getPanel() {
        return Panel;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public JList<String> getItemList() {
        return itemList;
    }
}
