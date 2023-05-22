/* (C)2023 */
package org.file;

import org.library.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV extends File {

    private ArrayList<String> strings = AsStrings();

    public CSV(String path) {
        super(path);
    }

    /**
     * Parses a line of CSV into an ArrayList of strings.
     *
     * @param content A line of CSV to be parsed
     * @return An ArrayList of strings containing all the parsed tokens (no commas)
     */
    public static List<String> ParseCSV(String content) {
        return Arrays.asList(content.split(","));
    }

    /**
     * Gets the contents of the CSV file as a list of strings. This list is loaded once at object
     * creation and cached. To convert a file to a list of strings see {@link File#AsStrings()}.
     *
     * @return An ArrayList of Strings.
     */
    public ArrayList<String> getStrings() {
        return strings;
    }
}
