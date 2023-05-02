/* (C)2023 */
package org.file;

import org.library.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV extends File {

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
     * Removes the first element of a CSV string (This will generally be the ID). This function is
     * called internally.
     *
     * @param content The CSV string to be trimmed.
     * @return The trimmed CSV string.
     */
    public static String RemoveFirstElement(String content) {
        int idx = content.indexOf(',');
        if (idx == -1) return content;
        content = content.substring(idx);
        return content;
    }

    /**
     * Takes a list of tokens and creates a CSV string.
     *
     * @param l A list of strings.
     * @return A CSV String containing these tokens.
     */
    public static String ToCSV(List<String> l) {
        StringBuilder str = new StringBuilder();
        for (String s : l) {
            str.append(s).append(",");
        }
        str = new StringBuilder(str.substring(0, str.length() - 1));
        return str.toString();
    }

    /**
     * Finds a specific requested value of a CSV string.
     *
     * @param content The CSV string to search.
     * @param idx The index of the item to search for (An enumeration)
     * @return The token at the requested index
     */
    public static String Query(String content, Enum idx) {
        int index;
        for (int i = 0; i < idx.ordinal() && i < content.length(); i++) {
            index = content.contains(",") ? content.indexOf(",") : 0;
            content = content.substring(index + 1);
        }
        index = content.contains(",") ? content.indexOf(",") : 0;
        content = content.substring(0, index);
        return content;
    }

    /**
     * Finds all matching items across the entire file of a given search query.
     *
     * @param idx The search index (An enumeration).
     * @param query The search query.
     * @return An ArrayList of all found items.
     */
    public ArrayList<String> FindItems(Enum idx, String query) {
        ArrayList<String> found = new ArrayList<>();
        for (String s : AsStrings()) {
            if (query.equals(CSV.Query(s, idx))) {
                found.add(s);
            }
        }
        return found;
    }

    /**
     * Searches the CSV file for a given query and returns the index of the line of the first match.
     *
     * @throws Exceptions.RowNotFound when the row cannot be found in the file.
     * @param idx The search index (An enumeration).
     * @param content The search query.
     * @return The index of the found item or -1 if not found.
     */
    public int GetMatchingRow(Enum idx, String content) throws Exceptions.RowNotFound {
        ArrayList<String> s = AsStrings();
        if (s.size() == 1)
            throw new Exceptions.RowNotFound(
                    "Row not found at index " + idx.ordinal() + ". File is empty."); // Empty file
        int i = 1;
        String search;
        do {
            String line = s.get(i);
            search = Query(line, idx);
            if (search.equals(content)) return i;
            i++;
        } while (i < s.size());
        throw new Exceptions.RowNotFound("Row not found at index " + idx.ordinal() + ".");
    }

    /**
     * Gets a row based on the items ID.
     *
     * @param id The items ID.
     * @return The CSV row of the ID. Or "" if not found.
     */
    public String GetFromID(long id) {
        try {
            int idx =
                    GetMatchingRow(
                            Item.CSV_INDEX.ID,
                            Long.toString(
                                    id)); // maybe make CSV_INDEX a param in the future to expand
            // functionality.
            return GetLine(idx);
        } catch (Exceptions.RowNotFound e) {
            e.printStackTrace();
            return "";
        }
    }
}
