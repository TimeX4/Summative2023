/* (C)2023 */
package org.gui;

import javax.swing.*;

public class LibrarianPanel {
    private JButton viewPateronsButton;
    private JButton addPateronButton;
    private JButton addBookButton;
    private JButton viewBooksButton;
    private JPanel OptionsPanel;
    private JButton logoutButton;

    public LibrarianPanel(JFrame frame, LibraryGUI libraryGUI) {
        viewPateronsButton.addActionListener(
                actionEvent -> {
                    AllPaterons page = new AllPaterons(frame, libraryGUI, this);
                    frame.setContentPane(page.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        addPateronButton.addActionListener(actionEvent -> {});

        addBookButton.addActionListener(actionEvent -> {});

        viewBooksButton.addActionListener(actionEvent -> {});

        logoutButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(libraryGUI.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public JPanel getPanel() {
        return OptionsPanel;
    }
}
