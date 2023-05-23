/* (C)2023 */
package org.library;

import org.file.CSV;

import java.util.HashMap;
import java.util.List;

public class Magazine extends Item {
    private int RenewalDate;
    public static final CSV file = new CSV("/library_files/magazine.csv");
    public static final String CSV_HEADER =
            "Id,Name,Copies,MaxCheckoutDays,Out,RenewalDays,ReferenceOnly";
    private static final HashMap<Long, Magazine> loadedMagazines = LoadMagazines();

    public Magazine(String name, int copies, int days, int renew) {
        super(name, copies, days);
        RenewalDate = renew;
    }

    Magazine(long id, String name, int copies, int days, String out, int renew, boolean ref) {
        super(id, name, copies, days, out, ref);
        RenewalDate = renew;
    }

    /**
     * Creates a string from the Magazine object containing useful information for the Library.
     *
     * @return A string from the Magazine object containing useful information for the Library.
     */
    public String toString() {
        return ID
                + ", "
                + Title
                + " | Copies: "
                + Copies
                + " | Renewed for "
                + RenewalDate
                + " days"
                + " | Due: "
                + MaxCheckoutDays
                + " days after checkout."
                + (ReferenceOnly ? "Reference Only" : "");
    }

    /**
     * Convert a Magazine object to a CSV file that can be written to the file.
     *
     * @return A CSV String to be written to a file.
     */
    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Copies + ",";
        s += MaxCheckoutDays + ",";
        s += CheckoutsToCSV() + ",";
        s += RenewalDate + ",";
        s += ReferenceOnly;
        return s;
    }

    /**
     * Converts a CSV string to a Magazine object.
     *
     * @param item The CSV string.
     * @return A new Magazine object populated by the CSV string.
     */
    public static Magazine FromCSV(String item) {
        if (item.equals("")) return null;
        List<String> tokens = CSV.ParseCSV(item);
        long id = Long.parseLong(tokens.get(0));
        String name = tokens.get(1);
        int copies = Integer.parseInt(tokens.get(2));
        int days = Integer.parseInt(tokens.get(3));
        String out = tokens.get(4);
        int renew = Integer.parseInt(tokens.get(5));
        boolean ref = Boolean.parseBoolean(tokens.get(6));
        return new Magazine(id, name, copies, days, out, renew, ref);
    }

    /**
     * Loads all the books from the disk and stores them in {@link Magazine#loadedMagazines}.
     *
     * @return A HashMap<Long, Magazine> containing the loaded books. (To be stored in {@link
     *     Magazine#loadedMagazines}).
     */
    private static HashMap<Long, Magazine> LoadMagazines() {
        int i = 0;
        HashMap<Long, Magazine> loaded = new HashMap<>();
        for (String s : Magazine.file.getStrings()) {
            if (i++ == 0) continue;
            Magazine m = FromCSV(s);
            if (m == null) continue;
            loaded.put(m.getID(), m);
        }
        return loaded;
    }

    /**
     * Getter for {@link Magazine#RenewalDate}.
     *
     * @return {@link Magazine#RenewalDate}.
     */
    public int getRenewalDate() {
        return RenewalDate;
    }

    /**
     * Getter for {@link Magazine#loadedMagazines}.
     *
     * @return {@link Magazine#loadedMagazines}.
     */
    public static HashMap<Long, Magazine> getLoadedMagazines() {
        return loadedMagazines;
    }

    /** Setter for {@link Magazine#RenewalDate}. */
    public void setRenewalDate(int renewalDate) {
        RenewalDate = renewalDate;
    }
}
