package org.gui;

import org.library.Book;
import org.library.Item;
import org.library.Magazine;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField searchBar;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public AllItems(JFrame frame, LibrarianPanel librarianPanel) {
        editButton.addActionListener(actionEvent -> {
            int idx = itemList.getSelectedIndex();
            long id = Item.getIDFromToString(itemList.getSelectedValue());
            if (typeCombo.getSelectedItem() == null) return;
            editItem(id, idx, frame);
        });
        deleteButton.addActionListener(actionEvent -> {
            int idx = itemList.getSelectedIndex();
            long id = Item.getIDFromToString(itemList.getSelectedValue());
            if (typeCombo.getSelectedItem() == null) return;
            switch (typeCombo.getSelectedItem().toString()) {
                case "Books" -> deleteItem(id, idx, Book.getLoadedBooks());
                case "Magazines" -> deleteItem(id, idx, Magazine.getLoadedMagazines());
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
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {DocumentChanged();}
            @Override
            public void removeUpdate(DocumentEvent e) {DocumentChanged();}
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    public void DocumentChanged() {
        String filter = searchBar.getText();
        if (typeCombo.getSelectedItem() == null) return;
        switch (typeCombo.getSelectedItem().toString()) {
            case "Books" -> FilterModel(listModel, filter, Book.getLoadedBooks());
            case "Magazines" -> FilterModel(listModel, filter, Magazine.getLoadedMagazines());
        }
    }

    public <E> void FilterModel(DefaultListModel<String> model, String filter, HashMap<Long, E> map) {
        for (Map.Entry<Long, E> entry : map.entrySet()) {
            String line = entry.getValue().toString();
            if (!line.contains(filter)) {
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

    public <E> void deleteItem(long id, int idx, HashMap<Long, E> loadedItems) {
        loadedItems.remove(id);
        listModel.remove(idx);
        itemList.revalidate();
        itemList.repaint();
    }

    public void editItem(long id, int idx, JFrame frame) {
        Item item = null;
        if (Book.getLoadedBooks().containsKey(id))
            item = Book.getLoadedBooks().get(id);
        else if (Magazine.getLoadedMagazines().containsKey(id))
            item = Magazine.getLoadedMagazines().get(id);
        if (item == null) return;
        EditItem form = new EditItem(frame, this, item, idx);
        frame.setContentPane(form.getPanel());
        frame.validate();
        frame.repaint();
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
