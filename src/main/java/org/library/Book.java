/* (C)2023 */
package org.library;

import org.file.*;

import java.time.LocalTime;
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

    public Book(String name, String author, int copies, LocalTime time) {
        super(name, copies, time);
        Author = author;
    }

    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Author + ",";
        s += Copies + ",";
        s += MaxCheckoutTime + ",";
        s += CheckOuts;
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
        LocalTime time = LocalTime.parse(tokens.get(4));
        int out = Integer.parseInt(tokens.get(5));
        return new Book(name, author, copies, time);
    }

    public String getAuthor() {
        return Author;
    }
}
