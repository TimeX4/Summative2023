/* (C)2023 */
package org.library;

import org.file.CSV;
import org.file.File;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

public class Patron {
    private static long NEXT_ID = GetNextID();
    public static final CSV file = new CSV("/library_files/patrons.csv");
    private static HashMap<Long, Patron> loadedPatrons = LoadPatrons();

    private String Name;
    private String PhoneNumber;
    private final long ID;
    private HashMap<Long, LocalDate> CheckedOut;
    private float DueFees;
    private String Password;

    public enum CSV_INDEX {
        ID,
        NAME,
        PHONE_NUMBER,
        FEES,
        CHECKOUTS,
        PASSWORD
    }

    public Patron(String name, String number, String pword) {
        Name = name;
        PhoneNumber = number;
        DueFees = 0.0f;
        CheckedOut = ParseCheckedOut("");
        Password = pword;

        ID = NEXT_ID++;
    }

    private Patron(long id, String name, String number, float fees, String out, String pword) {
        Name = name;
        PhoneNumber = number;
        DueFees = fees;
        CheckedOut = ParseCheckedOut(out);
        Password = pword;

        ID = id;
    }

    public static void DeletePatron(long id) {
        loadedPatrons.remove(id);
    }

    /**
     * Converts a Patron to a CSV string.
     *
     * @return A CSV string.
     */
    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Name + ",";
        s += PhoneNumber + ",";
        s += DueFees + ",";
        s += CheckoutToCSV() + ",";
        s += Password;
        return s;
    }

    /**
     * Converts a CSV string into a Patron object.
     *
     * @param csv The CSV string.
     * @return A new Patron object.
     */
    public static Patron FromCSV(String csv) {
        if (csv.equals("")) return null;
        List<String> tokens = CSV.ParseCSV(csv);
        long id = Long.parseLong(tokens.get(0));
        String name = tokens.get(1);
        String number = tokens.get(2);
        float fees = Float.parseFloat(tokens.get(3));
        String out = tokens.get(4);
        String pword = tokens.get(5);
        return new Patron(id, name, number, fees, out, pword);
    }

    /**
     * Adds an item to the Patrons CheckedOut list with the checkout date as the current time.
     *
     * @param b The item to add.
     */
    public void CheckOut(Item b) {
        if (b.getReferenceOnly()) return;
        if (b.getCopies() <= 0) return; // TODO: Unable to checkout.
        if (CheckedOut.containsKey(b.getID())) return; // TODO: Tell them why can't checkedout twice.
        LocalDate date = LocalDate.now();
        CheckedOut.put(b.getID(), date);
        b.decrementCopies();
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
        LocalDate now = LocalDate.now(); // Get the current time.
        LocalDate then = CheckedOut.get(b.getID()); // Get the time when the item was checked out.
        LocalDate max =
                then.plus(
                        b.MaxCheckoutDays,
                        ChronoUnit.DAYS); // Calculate the max date that the book can be out for.
        b.Copies++; // Add the stock back
        CheckedOut.remove(b.ID); // Remove the book from the users checked out list.
        if (max.isAfter(now)) {
            return 0.0f; // No fee the book was on time
        } else {
            Period p = max.until(now);
            return p.getDays() * 5.0f; // days overdue * 5 is the fee for being late 😈
        }
    }

    /** Stores NEXT_ID. TO BE CALLED ONCE AT PROGRAM CLEANUP. */
    public static void WriteNextID(File f) {
        f.Write(Long.toString(NEXT_ID), true);
    }

    /**
     * Converts a Patrons checked out list to CSV of the format {id:date|id:date}
     *
     * @return CSV of the Patron object.
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

    public String toString() {
        return ID + ", " + Name + ", " + PhoneNumber;
    }

    /**
     * Parses a CSV string into a hashmap containing keys of Patron ids and values of checkout
     * dates.
     *
     * @param out The CSV to be parsed.
     * @return A hashmap filled with the parsed date HashMap<long, LocalDate>
     */
    public HashMap<Long, LocalDate> ParseCheckedOut(String out) {
        if (out.equals("") || out.equals("{}")) return new HashMap<>(); // The list is empty.
        HashMap<Long, LocalDate> hash = new HashMap<>();
        out = out.substring(1, out.length() - 1); // Remove the {}
        String[] keys = out.split("\\|"); // Split at each | which are the keys id:date
        for (String k : keys) {
            String[] v = k.split(":"); // split this into its components
            long id = Long.parseLong(v[0]); // id
            LocalDate d = LocalDate.parse(v[1]); // date
            hash.put(id, d); // put them in the hashmap
        }
        return hash;
    }

    private static HashMap<Long, Patron> LoadPatrons() {
        HashMap<Long, Patron> loaded = new HashMap<>();
        int i = 0;
        for (String s : Patron.file.getStrings()) {
            if (i++ == 0) continue;
            Patron b = FromCSV(s);
            if (b == null) continue;
            loaded.put(b.getID(), b);
        }
        return loaded;
    }

    /**
     * Getter for {@link Patron#ID}.
     *
     * @return {@link Patron#ID}.
     */
    public long getID() {
        return ID;
    }
    /**
     * Gets the stored NEXT_ID. TO BE CALLED ONCE AT PROGRAM STARTUP. SEE {@link Patron#NEXT_ID}.
     *
     * @return NEXT_ID as a long.
     */
    public static long GetNextID() {
        File f = new File("/library_files/next_id");
        return Long.parseLong(f.GetLine(0));
    }

    /**
     * Getter for {@link Patron#Name}.
     *
     * @return {@link Patron#Name}.
     */
    public String getName() {
        return Name;
    }

    /**
     * Getter for {@link Patron#CheckedOut}.
     *
     * @return {@link Patron#CheckedOut}.
     */
    public HashMap<Long, LocalDate> getCheckedOut() {
        return CheckedOut;
    }

    /**
     * Sets the value of {@link Patron#CheckedOut} at a given key.
     *
     * @param key The key to modify (or insert).
     * @param value The value to be stored at that key.
     */
    public void setCheckedOut(long key, LocalDate value) {
        CheckedOut.put(key, value);
    }

    public static HashMap<Long, Patron> getLoadedPatrons() {
        return loadedPatrons;
    }

    public static void setLoadedPatrons(HashMap<Long, Patron> loadedPatrons) {
        Patron.loadedPatrons = loadedPatrons;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public float getDueFees() {
        return DueFees;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setDueFees(float dueFees) {
        DueFees = dueFees;
    }
}
