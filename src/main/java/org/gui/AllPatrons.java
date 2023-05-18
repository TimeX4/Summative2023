/* (C)2023 */
package org.gui;

import org.library.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;

public class AllPatrons {
    private JPanel AllPatrons;
    private JList<String> list1;
    private JButton deletePatron;
    private JButton editPatron;
    private JButton selectPatron;
    private JButton backButton;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public AllPatrons(JFrame frame, LibraryGUI libraryGUI, LibrarianPanel librarianPanel) {
        for (Map.Entry<Long, Patron> entry : Patron.getLoadedPatrons().entrySet()) {
            Long key = entry.getKey();
            Patron Patron = entry.getValue();
            if (Patron != null) {
                listModel.addElement(Patron.toString());
            }
        }
        list1.setModel(listModel);
        deletePatron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Patron> entry : Patron.getLoadedPatrons().entrySet()) {
                        if (i == idx) {
                            Long id = entry.getKey();
                            Patron.DeletePatron(id);
                            listModel.remove(idx);
                            list1.setModel(listModel);
                            list1.revalidate();
                            list1.repaint();
                        }
                        i++;
                    }
                });
        editPatron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Patron> entry : Patron.getLoadedPatrons().entrySet()) {
                        if (i == idx) {
                            EditPatron form = new EditPatron(entry.getValue(), frame, this, idx);
                            frame.setContentPane(form.getPanel());
                            frame.validate();
                            frame.repaint();
                        }
                        i++;
                    }
                });
        selectPatron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Patron> entry : Patron.getLoadedPatrons().entrySet()) {
                        if (i == idx) {
                            Long id = entry.getKey();
                            Patron patron = Patron.getLoadedPatrons().get(id);
                            PatronPage page = new PatronPage(patron, frame, libraryGUI);
                            frame.setContentPane(page.getPanel());
                            frame.validate();
                            frame.repaint();
                        }
                        i++;
                    }
                });
        backButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(librarianPanel.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public JPanel getPanel() {
        return AllPatrons;
    }

    public JList<String> getItemList() {
        return list1;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }
}
