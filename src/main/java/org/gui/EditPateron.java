/* (C)2023 */
package org.gui;

import org.library.Pateron;

import javax.swing.*;

public class EditPateron {
    private JButton editButton;
    private JButton backButton;
    private JPanel NamePanel;
    private JTextField nameText;
    private JTextField numberText;
    private JTextField feesText;
    private JPanel EditPanel;

    public EditPateron(Pateron pateron, JFrame frame, AllPaterons allPaterons, int idx) {

        nameText.setText(pateron.getName());
        numberText.setText(pateron.getPhoneNumber());
        feesText.setText(String.valueOf(pateron.getDueFees()));

        backButton.addActionListener(
                actionEvent -> {
                    frame.setContentPane(allPaterons.getPanel());
                    frame.validate();
                    frame.repaint();
                });
        editButton.addActionListener(
                actionEvent -> {
                    pateron.setName(nameText.getText());
                    pateron.setPhoneNumber(numberText.getText());
                    pateron.setDueFees(
                            Float.parseFloat(feesText.getText())); // TODO: Type validation
                    allPaterons.getListModel().remove(idx);
                    allPaterons.getListModel().insertElementAt(pateron.toString(), idx);
                    allPaterons.getItemList().setModel(allPaterons.getListModel());
                    allPaterons.getItemList().revalidate();
                    allPaterons.getItemList().repaint();
                    frame.setContentPane(allPaterons.getPanel());
                    frame.validate();
                    frame.repaint();
                });
    }

    public JPanel getPanel() {
        return EditPanel;
    }
}
