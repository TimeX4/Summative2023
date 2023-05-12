/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Pateron;

import java.util.Map;

import javax.swing.*;

public class AllBooks {
    private JPanel AllBooksPanel;
    private JList<String> ItemsList;
    private JPanel ButtonPanel;
    private JLabel TItle;
    private JScrollPane ItemsScroller;
    private JButton checkoutButton;
    private JButton homeButton;

    public AllBooks(Pateron p, PateronPage pateronPage, JFrame frame) {
        // TODO: Continue working on GUI but focus on ensuring files are loaded once and accessed
        // appropriately afterwards.
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Map.Entry<Long, Book> entry : Book.getLoadedBooks().entrySet()) {
            Long key = entry.getKey();
            Book book = entry.getValue();
            if (book != null) {
                listModel.addElement(book.toString());
            }
        }
        ItemsList.setModel(listModel);
        checkoutButton.addActionListener(
                actionEvent -> {
                    int idx = ItemsList.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Book> entry : Book.getLoadedBooks().entrySet()) {
                        Long key = entry.getKey();
                        Book b = Book.getLoadedBooks().get(key);
                        if (i == idx) {
                            p.CheckOut(b);
                            System.out.println("Checked in"); // TODO: Tell them due on GUI.
                            listModel.remove(idx);
                            listModel.insertElementAt(b.toString(), idx);
                            ItemsList.setModel(listModel);
                            ItemsList.revalidate();
                            ItemsList.repaint();
                        }
                        i++;
                    }
                });
        homeButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(pateronPage.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public JPanel getPanel() {
        return AllBooksPanel;
    }
}
