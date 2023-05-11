/* (C)2023 */
package org.gui;

import org.library.Book;

import java.util.Map;

import javax.swing.*;

public class AllBooks {
    private JPanel AllBooksPanel;
    private JList<String> ItemsList;
    private JPanel ButtonPanel;
    private JLabel TItle;
    private JScrollPane ItemsScroller;
    private JButton viewButton;

    public AllBooks() {
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
        viewButton.addActionListener(actionEvent -> {});
    }
}
