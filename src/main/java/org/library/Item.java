/* (C)2023 */
package org.library;

import org.file.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public abstract class Item {
    // Load from file
    private static long NEXT_ID = GetNextID();

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
    protected HashMap<String, LocalDate> CheckOuts;
    protected LocalTime MaxCheckoutTime;
    protected Boolean ReferenceOnly;

    Item(String name, int copies, LocalTime time) {
        Title = name;
        Copies = copies;
        MaxCheckoutTime = time;

        ID = NEXT_ID++;
    }

    /**
     * Converts an Item object to a CSV string.
     *
     * @return A CSV string.
     */
    public abstract String ToCSV();

    private static long GetNextID() {
        File f = new File("/library_files/next_id");
        return Long.parseLong(f.GetLine(0));
    }

    public static void SetNextID() {
        File f = new File("/library_files/next_id");
        f.Write(Long.toString(NEXT_ID), false);
    }

    public long getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public int getCopies() {
        return Copies;
    }
}
