/* (C)2023 */
package org.library;

import org.file.*;

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

    public Book(String name, String author, int copies, int days, String out) {
        super(name, copies, days, out);
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
    public static Book FromCSV(String item) {
        List<String> tokens = CSV.ParseCSV(item);
        String name = tokens.get(1);
        String author = tokens.get(2);
        int copies = Integer.parseInt(tokens.get(3));
        int time = Integer.parseInt(tokens.get(4));
        String out = tokens.get(5);
        return new Book(name, author, copies, time, out);
    }

    /**
     * Getter for {@link Book#Author}.
     *
     * @return {@link Book#Author}.
     */
    public String getAuthor() {
        return Author;
    }
}
