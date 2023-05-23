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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    public AllItemsUser(Patron p, PatronPage PatronPage, JFrame frame) {
        Patron = p;
        checkoutButton.addActionListener(
                actionEvent -> {
                    int idx = ItemsList.getSelectedIndex();
                    long id = Item.getIDFromToString(ItemsList.getSelectedValue());
                    if (typeCombo.getSelectedItem() == null) return;
                    checkoutItem(id, idx, PatronPage);
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

    private void checkoutItem(long id, int idx, PatronPage PatronPage) {
        Item item = null;
        if (Book.getLoadedBooks().containsKey(id))
            item = Book.getLoadedBooks().get(id);
        else if (Magazine.getLoadedMagazines().containsKey(id))
            item = Magazine.getLoadedMagazines().get(id);
        if (item == null) return;
        Patron.CheckOut(item);
        PatronPage.revalidateModel();
        System.out.println("Checked out"); // TODO: Tell them due on GUI.
        listModel.remove(idx);
        listModel.insertElementAt(item.toString(), idx);
        ItemsList.setModel(listModel);
        ItemsList.revalidate();
        ItemsList.repaint();
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
