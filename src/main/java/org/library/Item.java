/* (C)2023 */
package org.library;

import org.file.*;

import java.util.ArrayList;
import java.util.Map;

public abstract class Item {
    private static long NEXT_ID = GetNextID();

    public boolean getReferenceOnly() {
        return ReferenceOnly;
    }

    protected String Title;
    protected final long ID;
    protected int Copies;
    protected final ArrayList<Patron> CheckOuts;
    protected int MaxCheckoutDays;
    protected boolean ReferenceOnly;

    Item(String name, int copies, int days, boolean referenceOnly) {
        Title = name;
        Copies = copies;
        MaxCheckoutDays = days;
        CheckOuts = ParseCheckouts("");
        ReferenceOnly = referenceOnly;

        ID = NEXT_ID++;
    }

    Item(long id, String name, int copies, int days, String out, boolean ref) {
        Title = name;
        Copies = copies;
        MaxCheckoutDays = days;
        CheckOuts = ParseCheckouts(out);
        ReferenceOnly = ref;

        ID = id;
    }

    /**
     * Parses checkout info from CSV into a list of Patrons who have checked out the item.
     *
     * @param out The CSV to be parsed.
     * @return An array of Patrons who have checked out the given item.
     */
    private ArrayList<Patron> ParseCheckouts(String out) {
        if (out.equals("") || out.equals("{}"))
            return new ArrayList<>(); // Checkouts list is empty.
        out = out.substring(1, out.length() - 1); // Remove the encapsulating {}.
        String[] s = out.split(":"); // Split the list into its values.
        ArrayList<Patron> l = new ArrayList<>();
        for (String value : s) {
            long id = Long.parseLong(value);
            if (!Patron.getLoadedPatrons().containsKey(id)) continue;
            Patron p = Patron.getLoadedPatrons().get(id);
            l.add(p);
        }
        return l;
    }

    /**
     * Builds a CSV string from the given items Checkout list.
     *
     * @return A CSV string of Patron ids.
     */
    public String CheckoutsToCSV() {
        if (CheckOuts.size() == 0) return "{}"; // Checkouts list is empty.
        StringBuilder str = new StringBuilder();
        str.append("{"); // Add our opening bracket.
        for (Patron s : CheckOuts) {
            str.append(s.getID()).append(":"); // Add the id and a :
        }
        str.deleteCharAt(str.length() - 1); // Remove the trailing :
        str.append("}"); // At the closing bracket.
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
    /**
     * Stores NEXT_ID. TO BE CALLED ONCE AT PROGRAM CLEANUP.
     *
     * @param f The file to write the next id to.
     */
    public static void WriteNextID(File f) {
        f.Write(Long.toString(NEXT_ID), true);
    }

    /** Writes the loaded items to disk. TO BE CALLED ONCE AT PROGRAM CLEANUP. */
    public static void WriteDatabases() {
        Book.file.Write(Book.CSV_HEADER, false);
        for (Map.Entry<Long, Book> entry : Book.getLoadedBooks().entrySet()) {
            Book.file.Write(entry.getValue().ToCSV(), true);
        }
        Magazine.file.Write(Magazine.CSV_HEADER, false);
        for (Map.Entry<Long, Magazine> entry : Magazine.getLoadedMagazines().entrySet()) {
            Magazine.file.Write(entry.getValue().ToCSV(), true);
        }
        DVD.file.Write(DVD.CSV_HEADER, false);
        for (Map.Entry<Long, DVD> entry : DVD.getLoadedDVDs().entrySet()) {
            DVD.file.Write(entry.getValue().ToCSV(), true);
        }
    }

    /**
     * Returns the items ID as parsed from the FIRST value of a CSV string.
     *
     * @param tostring A CSV string in the form ID,...
     * @return The ID parsed as a long.
     */
    public static long getIDFromToString(String tostring) {
        return Long.parseLong(tostring.substring(0, tostring.indexOf(",")));
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

    /**
     * Getter for {@link Item#MaxCheckoutDays}.
     *
     * @return {@link Item#MaxCheckoutDays}.
     */
    public int getMaxCheckoutDays() {
        return MaxCheckoutDays;
    }

    /**
     * Getter for {@link Item#CheckOuts}.
     *
     * @return {@link Item#CheckOuts}.
     */
    public ArrayList<Patron> getCheckOuts() {
        return CheckOuts;
    }

    /** Setter for {@link Item#Title}. */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Setter for {@link Item#Copies}.
     *
     * @param copies
     */
    public void setCopies(int copies) {
        Copies = copies;
    }

    /**
     * Setter for {@link Item#MaxCheckoutDays}.
     *
     * @param maxCheckoutDays int, max days item can be checked out for.
     */
    public void setMaxCheckoutDays(int maxCheckoutDays) {
        MaxCheckoutDays = maxCheckoutDays;
    }

    /**
     * Setter for {@link Item#ReferenceOnly}
     *
     * @param referenceOnly boolean, is item reference only?
     */
    public void setReferenceOnly(boolean referenceOnly) {
        ReferenceOnly = referenceOnly;
    }

    /** Decrements copies by 1. */
    public void decrementCopies() {
        Copies--;
    }
}
