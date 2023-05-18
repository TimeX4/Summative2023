/* (C)2023 */
package org.gui;

import javax.swing.*;

public class LibrarianPanel {
    private JButton viewPatronsButton;
    private JButton addPatronButton;
    private JButton addBookButton;
    private JButton viewBooksButton;
    private JPanel OptionsPanel;
    private JButton logoutButton;

    public LibrarianPanel(JFrame frame, LibraryGUI libraryGUI) {
        viewPatronsButton.addActionListener(
                actionEvent -> {
                    AllPatrons page = new AllPatrons(frame, libraryGUI, this);
                    frame.setContentPane(page.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        addPatronButton.addActionListener(actionEvent -> {
            AddPatron page = new AddPatron(frame,this);
            frame.setContentPane(page.getPanel());
            frame.validate();
            frame.repaint();
        });
        addBookButton.addActionListener(actionEvent -> {
            AddItem page = new AddItem(frame,this);
            frame.setContentPane(page.getPanel());
            frame.validate();
            frame.repaint();
        });
        viewBooksButton.addActionListener(actionEvent -> {
            AllItems page = new AllItems(frame, this);
            frame.setContentPane(page.getPanel());
            frame.validate();
            frame.repaint();
        });

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
