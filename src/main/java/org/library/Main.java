/* (C)2023 */
package org.library;

import org.file.File;
import org.gui.LibraryGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // ArrayList<String> content = Pateron.file.AsStrings();
        // ArrayList<Item> found = Item.FindItems(Item.TYPES.BOOK, Item.CSV_INDEX.TITLE, "Bob The
        // Joe", content);
        // Book b = new Book("Rizz", "Mark Anthony", 3,  LocalTime.parse("12:00:00"));
        // Book b2 = new Book("Ivanova", "Bob", 3, 5, "{2:3}");
        // Interface i = new Interface();
        // i.AddItem(Book.file, b2);

        // i.RemoveItem(Book.file, 36);
        // i.AddItem(Book.file, b2);
        // Pateron p = new Pateron("Bob", "6472223333", 0.0f, "");
        // p.CheckOut(b2);
        // System.out.println(p.CheckIn(b2));

        // p.CheckOut(b2);
        // p.CheckoutToCSV();
        // i.AddPateron(Pateron.file, p);
        // i.EditItem(books, b2.getID(), "KYS", b2.getTitle());
        // Frame f = new Frame();

        LibraryGUI.draw();

        File f = new File("/library_files/next_id");
        f.EmptyContents();
        Item.WriteNextID(f);
        Pateron.WriteNextID(f);
    }
}
