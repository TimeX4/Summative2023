/* (C)2023 */
package org.library;

import org.file.*;

import java.util.HashMap;
import java.util.List;

public class Book extends Item {
    public enum CSV_INDEX {
        ID,
        TITLE,
        AUTHOR,
        COPIES,
        MAX_TIME,
        CHECKOUTS
    }

    private String Author;
    public static final CSV file = new CSV("/library_files/books.csv");
    private static HashMap<Long, Book> loadedBooks = LoadBooks();

    public Book(String name, String author, int copies, int days, String out) {
        super(name, copies, days, out);
        Author = author;
    }

    public Book(long id, String name, String author, int copies, int days, String out) {
        super(id, name, copies, days, out);
        Author = author;
    }

    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Author + ",";
        s += Copies + ",";
        s += MaxCheckoutDays + ",";
        s += CheckoutsToCSV();
        return s;
    }

    /**
     * Converts a CSV string to a Book object.
     *
     * @param item The CSV string.
     * @return A new Book object populated by the CSV string.
     */
    private static Book FromCSV(String item) {
        if (item.equals("")) return null;
        List<String> tokens = CSV.ParseCSV(item);
        long id = Long.parseLong(tokens.get(0));
        String name = tokens.get(1);
        String author = tokens.get(2);
        int copies = Integer.parseInt(tokens.get(3));
        int time = Integer.parseInt(tokens.get(4));
        String out = tokens.get(5);
        return new Book(id, name, author, copies, time, out);
    }

    private static HashMap<Long, Book> LoadBooks() {
        HashMap<Long, Book> loaded = new HashMap<>();
        for (String s : Book.file.getStrings()) {
            Book b = FromCSV(s);
            if (b == null) continue;
            loaded.put(b.getID(), b);
        }
        return loaded;
    }

    public String toString() {
        return Title
                + ", "
                + Author
                + " | Copies: "
                + Copies
                + " | Due: "
                + MaxCheckoutDays
                + " days after checkout.";
    }

    /**
     * Getter for {@link Book#Author}.
     *
     * @return {@link Book#Author}.
     */
    public String getAuthor() {
        return Author;
    }

    public static HashMap<Long, Book> getLoadedBooks() {
        return loadedBooks;
    }

    public static void setLoadedBooks(HashMap<Long, Book> loadedBooks) {
        Book.loadedBooks = loadedBooks;
    }
}
