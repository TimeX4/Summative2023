/* (C)2023 */
package org.gui;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) screenSize.getWidth();
    public static int height = (int) screenSize.getHeight();

    public Frame() {
        super("Library");
        setSize(width, height);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LoginScreen();
        setVisible(true);
    }

    public void LoginScreen() {
        Button librarian = new Button("Liberian");
        librarian.setBounds((width / 2 - 200), (height / 2 - 90), 400, 80);
        Button user = new Button("User");
        user.setBounds((width / 2 - 200), (height / 2 + 10), 400, 80);
        librarian.addActionListener(
                e -> {
                    remove(librarian);
                    remove(user);
                    revalidate();
                    repaint();
                    LibrarianMenu();
                });
        user.addActionListener(
                e -> {
                    remove(librarian);
                    remove(user);
                    revalidate();
                    repaint();
                    UserMenu();
                });
        add(librarian);
        add(user);
    }

    public void LibrarianMenu() {}

    public void UserMenu() {
        Button byTitle = new Button("Search by Title");
        Button byAuthor = new Button("Search by Author");
        Button byID = new Button("Search by ID");
    }
}
