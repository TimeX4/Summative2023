/* (C)2023 */
package org.library;

import org.file.CSV;

import java.util.HashMap;
import java.util.List;

public class DVD extends Item {
    private int Length;
    public static final CSV file = new CSV("/library_files/dvds.csv");
    public static final String CSV_HEADER =
            "Id,Name,Length,Copies,MaxCheckoutDays,Out,ReferenceOnly";
    private static final HashMap<Long, DVD> loadedDVDs = LoadDVDs();

    public DVD(String name, int length, int copies, int days, boolean reference) {
        super(name, copies, days, reference);
        Length = length;
    }

    public DVD(long id, String name, int length, int copies, int days, String out, boolean ref) {
        super(id, name, copies, days, out, ref);
        Length = length;
    }

    /**
     * Convert a DVD object to a CSV file that can be written to the file.
     *
     * @return A CSV String to be written to a file.
     */
    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Length + ",";
        s += Copies + ",";
        s += MaxCheckoutDays + ",";
        s += CheckoutsToCSV() + ",";
        s += ReferenceOnly;
        return s;
    }

    /**
     * Converts a CSV string to a DVD object.
     *
     * @param item The CSV string.
     * @return A new DVD object populated by the CSV string.
     */
    private static DVD FromCSV(String item) {
        if (item.equals("")) return null;
        List<String> tokens = CSV.ParseCSV(item);
        long id = Long.parseLong(tokens.get(0));
        String name = tokens.get(1);
        int length = Integer.parseInt(tokens.get(2));
        int copies = Integer.parseInt(tokens.get(3));
        int time = Integer.parseInt(tokens.get(4));
        String out = tokens.get(5);
        boolean ref = Boolean.parseBoolean(tokens.get(6));
        return new DVD(id, name, length, copies, time, out, ref);
    }

    /**
     * Loads all the books from the disk and stores them in {@link DVD#loadedDVDs}.
     *
     * @return A HashMap<Long, Book> containing the loaded books. (To be stored in {@link
     *     DVD#loadedDVDs}).
     */
    private static HashMap<Long, DVD> LoadDVDs() {
        int i = 0;
        HashMap<Long, DVD> loaded = new HashMap<>();
        for (String s : DVD.file.getStrings()) {
            if (i++ == 0) continue; // Skip the header line.
            DVD d = FromCSV(s);
            if (d == null) continue;
            loaded.put(d.getID(), d);
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
                + " | "
                + Length
                + " minutes"
                + " | Copies: "
                + Copies
                + " | Due: "
                + MaxCheckoutDays
                + " days after checkout."
                + (ReferenceOnly ? " Reference Only" : "");
    }

    /**
     * Getter for {@link DVD#Length}.
     *
     * @return {@link DVD#Length}.
     */
    public int getLength() {
        return Length;
    }

    /**
     * Getter for {@link DVD#loadedDVDs}.
     *
     * @return {@link DVD#loadedDVDs}.
     */
    public static HashMap<Long, DVD> getLoadedDVDs() {
        return loadedDVDs;
    }

    /** Setter for {@link DVD#Length}. */
    public void setLength(int length) {
        Length = length;
    }
}
