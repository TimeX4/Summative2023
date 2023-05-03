/* (C)2023 */
package org.library;

import org.file.*;

import java.util.ArrayList;

public abstract class Item {
    // Load from file
    private static long NEXT_ID = GetNextID();
    // private static long NEXT_ID = 0;

    public enum CSV_INDEX {
        ID,
        TITLE,
        COPIES,
        MAX_TIME,
        CHECKOUTS
    }

    protected String Title;
    protected final long ID;
    protected int Copies;
    protected ArrayList<Pateron> CheckOuts;
    protected int MaxCheckoutDays;
    protected boolean ReferenceOnly;

    Item(String name, int copies, int days, String out) {
        Title = name;
        Copies = copies;
        MaxCheckoutDays = days;
        CheckOuts = ParseCheckouts(out);

        ID = NEXT_ID++;
    }

    /**
     * Converts an Item object to a CSV string.
     *
     * @return A CSV string.
     */
    public abstract String ToCSV();

    /**
     * Parses checkout info from CSV into a list of paterons who have checked out the item.
     *
     * @param out The CSV to be parsed.
     * @return An array of Paterons who have checked out the given item.
     */
    private ArrayList<Pateron> ParseCheckouts(String out) {
        if (out.equals("") || out.equals("{}"))
            return new ArrayList<>(); // Checkouts list is empty.
        out = out.substring(1, out.length() - 1); // Remove the encapsulating {}.
        String[] s = out.split(":"); // Split the list into its values.
        ArrayList<Pateron> l = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            long id = Long.parseLong(s[i]);
            try {
                String item =
                        Pateron.file.GetFromID(
                                id); // Get the matching Pateron if one exists with the provided id.
                if (item.equals("")) continue;
                l.add(Pateron.FromCSV(item));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return l;
    }

    /**
     * Builds a CSV string from the given items Checkout list.
     *
     * @return A CSV string of Pateron ids.
     */
    public String CheckoutsToCSV() {
        if (CheckOuts.size() == 0) return "{}"; // Checkouts list is empty.
        StringBuilder str = new StringBuilder();
        str.append("{"); // Add our opening bracket.
        for (Pateron s : CheckOuts) {
            str.append(s.getID()).append(":"); // Add the id and a :
        }
        str.deleteCharAt(str.length()); // Remove the trailing :
        str.append("}"); // At the closing bracket.
        System.out.println(str);
        return str.toString();
    }

    /**
     * Gets the stored NEXT_ID. TO BE CALLED ONCE AT PROGRAM STARTUP. SEE {@link Item#NEXT_ID}.
     *
     * @return NEXT_ID as a long.
     */
    public static long GetNextID() {
        File f = new File("/library_files/next_id");
        return Long.parseLong(f.GetLine(0));
    }
    /** Stores NEXT_ID. TO BE CALLED ONCE AT PROGRAM CLEANUP. */
    public static void WriteNextID(File f) {
        f.Write(Long.toString(NEXT_ID), true);
    }

    /**
     * Getter for {@link Item#ID}.
     *
     * @return {@link Item#ID}.
     */
    public long getID() {
        return ID;
    }

    /**
     * Getter for {@link Item#Title}.
     *
     * @return {@link Item#Title}.
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Getter for {@link Item#Copies}.
     *
     * @return {@link Item#Copies}.
     */
    public int getCopies() {
        return Copies;
    }
}
