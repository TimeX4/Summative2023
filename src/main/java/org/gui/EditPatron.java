/* (C)2023 */
package org.gui;

import org.library.Patron;

import javax.swing.*;

public class EditPatron {
    private JButton editButton;
    private JButton backButton;
    private JPanel NamePanel;
    private JTextField nameText;
    private JTextField numberText;
    private JTextField feesText;
    private JPanel EditPanel;

    public EditPatron(Patron Patron, JFrame frame, AllPatrons allPatrons, int idx) {

        nameText.setText(Patron.getName());
        numberText.setText(Patron.getPhoneNumber());
        feesText.setText(String.valueOf(Patron.getDueFees()));

        backButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(allPatrons.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        editButton.addActionListener(
                actionEvent -> {
                    Patron.setName(nameText.getText());
                    Patron.setPhoneNumber(numberText.getText());
                    Patron.setDueFees(
                            Float.parseFloat(feesText.getText())); // TODO: Type validation
                    allPatrons.getListModel().remove(idx);
                    allPatrons.getListModel().insertElementAt(Patron.toString(), idx);
                    allPatrons.getItemList().setModel(allPatrons.getListModel());
                    allPatrons.getItemList().revalidate();
                    allPatrons.getItemList().repaint();
                    frame.setContentPane(allPatrons.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public JPanel getPanel() {
        return EditPanel;
    }
}
