/* (C)2023 */
package org.gui;

import org.library.Pateron;

import java.util.Map;

import javax.swing.*;

public class AllPaterons {
    private JPanel AllPaterons;
    private JList<String> list1;
    private JButton deletePateron;
    private JButton editPateron;
    private JButton selectPateron;
    private JButton backButton;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public AllPaterons(JFrame frame, LibraryGUI libraryGUI, LibrarianPanel librarianPanel) {

        for (Map.Entry<Long, Pateron> entry : Pateron.getLoadedPaterons().entrySet()) {
            Long key = entry.getKey();
            Pateron pateron = entry.getValue();
            if (pateron != null) {
                listModel.addElement(pateron.toString());
            }
        }
        list1.setModel(listModel);
        deletePateron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Pateron> entry : Pateron.getLoadedPaterons().entrySet()) {
                        if (i == idx) {
                            Long id = entry.getKey();
                            Pateron.DeletePateron(id);
                            listModel.remove(idx);
                            list1.setModel(listModel);
                            list1.revalidate();
                            list1.repaint();
                        }
                        i++;
                    }
                });
        editPateron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Pateron> entry : Pateron.getLoadedPaterons().entrySet()) {
                        if (i == idx) {
                            EditPateron form = new EditPateron(entry.getValue(), frame, this, idx);
                            frame.setContentPane(form.getPanel());
                            frame.validate();
                            frame.repaint();
                        }
                        i++;
                    }
                });
        selectPateron.addActionListener(
                actionEvent -> {
                    int idx = list1.getSelectedIndex();
                    int i = 0;
                    for (Map.Entry<Long, Pateron> entry : Pateron.getLoadedPaterons().entrySet()) {
                        if (i == idx) {
                            Long id = entry.getKey();
                            Pateron pateron = Pateron.getLoadedPaterons().get(id);
                            PateronPage page = new PateronPage(pateron, frame, libraryGUI);
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
        return AllPaterons;
    }

    public JList<String> getItemList() {
        return list1;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }
}
