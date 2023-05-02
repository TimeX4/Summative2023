/* (C)2023 */
package org.library;

import org.file.CSV;
import org.file.File;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

public class Pateron {
    // Load from file
    private static long NEXT_ID = GetNextID();
    // private static long NEXT_ID = 0;
    public static final CSV file = new CSV("/library_files/paterons.csv");
    private String Name;
    private String PhoneNumber;
    private final long ID;
    private HashMap<Long, LocalDate> CheckedOut;
    private float DueFees;

    Pateron(String name, String number, float fees, String out) {
        Name = name;
        PhoneNumber = number;
        DueFees = fees;
        CheckedOut = new HashMap<>();
        // out = ParseCheckedOut(out);

        ID = NEXT_ID++;
    }

    /**
     * Converts a Pateron to a CSV string.
     *
     * @return A CSV string.
     */
    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Name + ",";
        s += PhoneNumber + ",";
        s += DueFees + ",";
        s += CheckoutToCSV();
        return s;
    }

    /**
     * Converts a CSV string into a Pateron object.
     *
     * @param csv The CSV string.
     * @return A new Pateron object.
     */
    public static Pateron FromCSV(String csv) {
        List<String> tokens = CSV.ParseCSV(csv);
        String name = tokens.get(1);
        String number = tokens.get(2);
        float fees = Float.parseFloat(tokens.get(3));
        String out = tokens.get(4);
        return new Pateron(name, number, fees, out);
    }

    /**
     * Adds an item to the paterons CheckedOut list with the checkout date as the current time.
     *
     * @param b The item to add.
     */
    public void CheckOut(Item b) {
        LocalDate date = LocalDate.now();
        CheckedOut.put(b.getID(), date);
    }

    /**
     * Checked in an item if it is found in the users Checkout list. Calculates any overdue fees at
     * a rate of $5.0 / day overdue. Removes the item from the users Checkout list, increments the
     * copies of the item checked in.
     *
     * @param b The item to be checked in.
     * @return The amount of overdue fees as a float.
     */
    public float CheckIn(Item b) {
        if (!CheckedOut.containsKey(b.getID())) return 0.0f; // not checked out
        LocalDate now = LocalDate.now();
        LocalDate then = CheckedOut.get(b.getID());
        LocalDate max = then.plus(b.MaxCheckoutDays, ChronoUnit.DAYS);
        if (max.isAfter(now)) {
            b.Copies++;
            CheckedOut.remove(b.ID);
            return 0.0f;
        } else {
            Period p = max.until(now);
            return p.getDays() * 5.0f;
        }
    }

    /**
     * Getter for {@link Pateron#ID}.
     *
     * @return {@link Pateron#ID}.
     */
    public long getID() {
        return ID;
    }
    /**
     * Gets the stored NEXT_ID. TO BE CALLED ONCE AT PROGRAM STARTUP. SEE {@link Pateron#NEXT_ID}.
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
     * Converts a paterons checked out list to CSV. {}
     *
     * @return
     */
    public String CheckoutToCSV() {
        if (CheckedOut.size() == 0) return "{}";
        StringBuilder str = new StringBuilder();
        str.append("{");
        CheckedOut.forEach(
                (key, value) -> {
                    str.append(key);
                    str.append(":");
                    str.append(value);
                    str.append("|");
                });
        str.deleteCharAt(str.length() - 1);
        str.append("}");
        return str.toString();
    }

    public HashMap<Long, LocalDate> ParseCheckedOut(String out) {
        HashMap<Long, LocalDate> hash = new HashMap<>();

        return hash;
    }
}
