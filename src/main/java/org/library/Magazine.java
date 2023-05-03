/* (C)2023 */
package org.library;

import org.file.CSV;

import java.util.List;

public class Magazine extends Item {
    private int RenewalDate;

    Magazine(String name, int copies, int days, String out, int renew) {
        super(name, copies, days, out);
        RenewalDate = renew;
    }

    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Copies + ",";
        s += MaxCheckoutDays + ",";
        s += CheckOuts + ",";
        s += RenewalDate;
        return s;
    }

    /**
     * Converts a CSV string to a Magazine object.
     *
     * @param item The CSV string.
     * @return A new Magazine object populated by the CSV string.
     */
    public static Magazine FromCSV(String item) {
        List<String> tokens = CSV.ParseCSV(item);
        String name = tokens.get(1);
        int copies = Integer.parseInt(tokens.get(2));
        int days = Integer.parseInt(tokens.get(3));
        String out = tokens.get(4);
        int renew = Integer.parseInt(tokens.get(5));
        return new Magazine(name, copies, days, out, renew);
    }

    /**
     * Getter for {@link Magazine#RenewalDate}.
     *
     * @return {@link Magazine#RenewalDate}.
     */
    public int getRenewalDate() {
        return RenewalDate;
    }
}
