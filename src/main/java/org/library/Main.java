/* (C)2023 */
package org.library;

import org.file.*;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        CSV books = new CSV("/library_files/books.csv");
        // ArrayList<String> content = books.AsStrings();
        // ArrayList<Item> found = Item.FindItems(Item.TYPES.BOOK, Item.CSV_INDEX.TITLE, "Bob The
        // Joe", content);
        // Book b = new Book("Rizz", "Mark Anthony", 3,  LocalTime.parse("12:00:00"));
        Book b2 = new Book("ASDASD", "Mark Twain", 3, LocalTime.parse("12:00:00"));
        Interface i = new Interface();
        i.RemoveItem(books, 36);
        i.AddItem(books, b2);
        // i.EditItem(books, b2.getID(), "KYS", b2.getTitle());
        // Frame f = new Frame();

        Item.SetNextID();
    }
}
