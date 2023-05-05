/* (C)2023 */
package org.gui;

import javax.swing.*;

public class PateronPage {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel Panel;
    private JList Books;
    private JScrollPane ScrollPane;

    public PateronPage() {}

    public static JPanel getPanel() {
        return new PateronPage().Panel;
    }

    private void createUIComponents() {
        ScrollPane = new JScrollPane();
        for (int i = 0; i < 10; i++) {
            ScrollPane.add(new JButton(String.valueOf(i)));
        }
        ScrollPane.validate();
        ScrollPane.repaint();
    }
}
