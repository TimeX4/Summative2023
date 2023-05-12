/* (C)2023 */
package org.gui;

import org.library.Book;
import org.library.Pateron;

import java.time.LocalDate;
import java.util.Map;

import javax.swing.*;

public class PateronPage {
    private JButton Back;
    private JButton CheckIn;
    private JButton ViewAll;
    private JPanel Panel;
    private JList<String> Books;
    private JScrollPane ListScroller;
    private JPanel HeaderPanel;
    private JPanel ButtonPanel;

    public PateronPage(Pateron p, JFrame frame, LibraryGUI libraryGUI) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        p.getCheckedOut()
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
        Books.setModel(listModel);
        Back.addActionListener(
                actionEvent -> {
                    frame.setContentPane(libraryGUI.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        ViewAll.addActionListener(
                actionEvent -> {
                    AllBooks allBooks = new AllBooks(p, this, frame);
                    frame.setContentPane(allBooks.getPanel());
                    frame.validate();
                    frame.repaint();
                });

        CheckIn.addActionListener(
                actionEvent -> {
                    int idx = Books.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, LocalDate> entry : p.getCheckedOut().entrySet()) {
                        Long key = entry.getKey();
                        Book b = Book.getLoadedBooks().get(key);
                        if (i == idx) {
                            float due = p.CheckIn(b);
                            System.out.println(due); // TODO: Tell them due on GUI.
                            listModel.remove(idx);
                            Books.setModel(listModel);
                            Books.revalidate();
                            Books.repaint();
                        }
                        i++;
                    }
                });
    }

    public JPanel getPanel() {
        return Panel;
    }
}
