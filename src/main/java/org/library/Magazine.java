/* (C)2023 */
package org.library;

import org.file.CSV;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Magazine extends Item {
    private LocalDate RenewalDate;

    Magazine(String name, int copies, LocalTime time, LocalDate renew) {
        super(name, copies, time);
        RenewalDate = renew;
    }

    public String ToCSV() {
        String s = "";
        s += ID + ",";
        s += Title + ",";
        s += Copies + ",";
        s += MaxCheckoutTime + ",";
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
        LocalTime time = LocalTime.parse(tokens.get(3));
        int out = Integer.parseInt(tokens.get(4));
        LocalDate renew = LocalDate.parse(tokens.get(5));
        return new Magazine(name, copies, time, renew);
    }
}