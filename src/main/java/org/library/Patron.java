/* (C)2023 */
package org.library;

import org.file.CSV;
import org.file.Encryptor;
import org.file.File;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patron {
    private static long NEXT_ID = GetNextID();
    public static final CSV file = new CSV("/library_files/patrons.csv");
    public static final String CSV_HEADER = "Id,Name,Number,Fees,Out,Password";

    private static final HashMap<Long, Patron> loadedPatrons = LoadPatrons();

    private String Name;
    private String PhoneNumber;
    private final long ID;
    private final HashMap<Long, LocalDate> CheckedOut;
    private float DueFees;
    private String Password;

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

    /**
     * Deletes a Patron from the loaded patrons list.
     *
     * @param id The id of the patron to delete.
     */
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
        if (csv.isBlank()) return null;
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
     * Determines if there is a duplicate patron loaded into memory by comparing phone numbers.
     * @param phoneNumber The phone number to check.
     * @return true if the patron is a duplicate and false otherwise.
     */
    public static boolean isDuplicate(String phoneNumber) {
        for (var entry : loadedPatrons.entrySet()) {
            Patron p = entry.getValue();
            if (p.getPhoneNumber().equals(phoneNumber)) return true;
        }
        return false;
    }

    /**
     * Adds an item to the Patrons CheckedOut list with the checkout date as the current time.
     *
     * @param b The item to add.
     * @return An integer status code.
     *     <table>
     *     <tr>
     *      <th>Code</th>
     *      <th>Definition</th>
     *     </tr>
     *     <tr>
     *         <td>0</td>
     *         <td>Checked out successfully.</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>Item is reference only.</td>
     *     </tr>
     *     <tr>
     *         <td>2</td>
     *         <td>Item is out of stock.</td>
     *     </tr>
     *     <tr>
     *         <td>3</td>
     *         <td>Item already checked out.</td>
     *     </tr>
     * </table>
     */
    public int CheckOut(Item b) {
        if (b.getReferenceOnly()) return 1;
        if (b.getCopies() <= 0) return 2;
        if (CheckedOut.containsKey(b.getID())) return 3;
        b.getCheckOuts().add(this);
        LocalDate date = LocalDate.now();
        CheckedOut.put(b.getID(), date);
        b.decrementCopies();
        return 0;
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

    /**
     * Stores NEXT_ID. TO BE CALLED ONCE AT PROGRAM CLEANUP.
     *
     * @param f The file to write the NextID to.
     */
    public static void WriteNextID(File f) {
        f.Write(Long.toString(NEXT_ID), true);
    }

    /** Writes the loaded patrons to disk. TO BE CALLED ONCE AT PROGRAM CLEANUP. */
    public static void WriteDatabases() {
        Patron.file.Write(Patron.CSV_HEADER, false);
        for (Map.Entry<Long, Patron> entry : Patron.getLoadedPatrons().entrySet()) {
            Patron.file.Write(entry.getValue().ToCSV(), true);
        }
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

    /**
     * Creates a string from the Patron object containing useful information for the Library.
     *
     * @return A string from the Patron object containing useful information for the Library.
     */
    public String toString() {
        return ID + ", " + Name + ", " + PhoneNumber;
    }

    /**
     * Parses a CSV string into a hashmap containing keys of Patron ids and values of checkout
     * dates.
     *
     * @param out The CSV to be parsed.
     * @return A hashmap filled with the parsed date HashMap
     */
    public HashMap<Long, LocalDate> ParseCheckedOut(String out) {
        if (out.isBlank() || out.equals("{}")) return new HashMap<>(); // The list is empty.
        HashMap<Long, LocalDate> hash = new HashMap<>();
        out = out.substring(1, out.length() - 1); // Remove the {}
        String[] keys = out.split("\\|"); // Split at each | which are the key's id:date
        for (String k : keys) {
            String[] v = k.split(":"); // split this into its components
            long id = Long.parseLong(v[0]); // id
            LocalDate d = LocalDate.parse(v[1]); // date
            hash.put(id, d); // put them in the hashmap
        }
        return hash;
    }

    /**
     * Loads all the books from the disk and stores them in {@link Patron#loadedPatrons}.
     *
     * @return A HashMap<Long, Book> containing the loaded books. (To be stored in {@link
     *     Patron#loadedPatrons}
     */
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
        return Long.parseLong(f.GetLine(1));
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
     * Getter for {@link Patron#loadedPatrons}.
     *
     * @return {@link Patron#loadedPatrons}.
     */
    public static HashMap<Long, Patron> getLoadedPatrons() {
        return loadedPatrons;
    }

    /**
     * Getter for {@link Patron#Password}.
     *
     * @return {@link Patron#Password}.
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Getter for {@link Patron#PhoneNumber}.
     *
     * @return {@link Patron#PhoneNumber}.
     */
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    /**
     * Getter for {@link Patron#DueFees}.
     *
     * @return {@link Patron#DueFees}.
     */
    public float getDueFees() {
        return DueFees;
    }

    /**
     * Setter for {@link Patron#Name}.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Setter for {@link Patron#PhoneNumber}.
     *
     * @param phoneNumber The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    /**
     * Setter for {@link Patron#DueFees}.
     *
     * @param dueFees The due fees to set.
     */
    public void setDueFees(float dueFees) {
        DueFees = dueFees;
    }

    /**
     * Setter for {@link Patron#Password}. Encrypts with SHA-512.
     *
     * @param password The new password (Plaintext).
     */
    public void setPassword(String password) {
        Password = Encryptor.SHA512(password);
    }
}
