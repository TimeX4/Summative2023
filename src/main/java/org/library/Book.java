/* (C)2023 */
package org.library;

import org.file.*;

import java.util.HashMap;
import java.util.List;

public class Book extends Item {
    private String Author;
    public static final CSV file = new CSV("/library_files/books.csv");
    public static final String CSV_HEADER =
            "Id,Name,Author,Copies,MaxCheckoutDays,Out,ReferenceOnly";
    private static final HashMap<Long, Book> loadedBooks = LoadBooks();

    public Book(String name, String author, int copies, int days, boolean ref) {
        super(name, copies, days, ref);
        Author = author;
    }

    public Book(
            long id, String name, String author, int copies, int days, String out, boolean ref) {
        super(id, name, copies, days, out, ref);
        Author = author;
    }

    /**
     * Convert a Book object to a CSV file that can be written to the file.
     *
     * @return A CSV String to be written to a file.
     */
    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Author + ",";
        s += Copies + ",";
        s += MaxCheckoutDays + ",";
        s += CheckoutsToCSV() + ",";
        s += ReferenceOnly;
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
        boolean ref = Boolean.parseBoolean(tokens.get(6));
        return new Book(id, name, author, copies, time, out, ref);
    }

    /**
     * Loads all the books from the disk and stores them in {@link Book#loadedBooks}.
     *
     * @return A HashMap<Long, Book> containing the loaded books. (To be stored in {@link
     *     Book#loadedBooks}).
     */
    private static HashMap<Long, Book> LoadBooks() {
        int i = 0;
        HashMap<Long, Book> loaded = new HashMap<>();
        for (String s : Book.file.getStrings()) {
            if (i++ == 0) continue; // Skip the header line.
            Book b = FromCSV(s);
            if (b == null) continue;
            loaded.put(b.getID(), b);
        }
        return loaded;
    }

    /**
     * Creates a string from the Book object containing useful information for the Library.
     *
     * @return A string from the Book object containing useful information for the Library.
     */
    public String toString() {
        return ID
                + ", "
                + Title
                + ", "
                + Author
                + " | Copies: "
                + Copies
                + " | Due: "
                + MaxCheckoutDays
                + " days after checkout."
                + (ReferenceOnly ? " Reference Only" : "");
    }

    /**
     * Getter for {@link Book#Author}.
     *
     * @return {@link Book#Author}.
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * Getter for {@link Book#loadedBooks}.
     *
     * @return {@link Book#loadedBooks}.
     */
    public static HashMap<Long, Book> getLoadedBooks() {
        return loadedBooks;
    }

    /** Setter for {@link Book#Author}. */
    public void setAuthor(String author) {
        Author = author;
    }
}
