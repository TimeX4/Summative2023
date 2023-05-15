/* (C)2023 */
package org.library;

import org.file.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interface {
    boolean Librarian = false;

    public void Login() {
        System.out.println("Login: ");
        System.out.println("1) Librarian");
        System.out.println("2) User");
        Scanner sc = new Scanner(System.in);
        if (sc.nextInt() == 1) {
            LibrarianInterface();
        } else {
            UserInterface();
        }
    }

    private void LibrarianInterface() {
        Librarian = true;
        System.out.println("Librarian: ");
        System.out.println("1) Add Item");
        System.out.println("2) Modify Existing Item");
        System.out.println("3) Find Item");
        System.out.println("4) Check In Book");
        System.out.println("5) Check Out Book");
        System.out.println("6) Renew Magazine");
        System.out.println("7) Delete Item");
        System.out.println("8) Add Patron");
        System.out.println("9) Delete Patron");
        System.out.println("0) Edit Patron");
        Scanner sc = new Scanner(System.in);
    }

    private void UserInterface() {
        System.out.println("User: ");
        System.out.println("1) Search by ID");
        System.out.println("2) Search by Name");
        System.out.println("3) Search by Author");
    }

    /**
     * Adds an item object to a CSV file.
     *
     * @param f The file to add the item to. Ensure this is the correct type. (books.csv,
     *     magazines.csv) etc.
     * @param item The item to be added to the file.
     */
    public void AddItem(CSV f, Item item) {
        String csv = item.ToCSV();
        csv = CSV.RemoveFirstElement(csv); // Drop the ID in case they differ for some reason.
        ArrayList<String> found =
                f.FindItems(
                        Item.CSV_INDEX.TITLE,
                        item.getTitle()); // Find items with matching titles (any element would
        // work we just need possible matches)
        for (String s : found) {
            s = CSV.RemoveFirstElement(s);
            if (s.equals(csv)) return; // Our item is already in the file.
        }
        f.Write(item.ToCSV(), true);
    }

    /**
     * Removes an item from a file based on its ID.
     *
     * @param f The file to remove from.
     * @param ID The items ID.
     */
    public static void RemoveItem(CSV f, long ID) {
        try {
            int idx =
                    f.GetMatchingRow(
                            Item.CSV_INDEX.ID,
                            Long.toString(ID)); // Find the matching line based on the items ID.
            f.DeleteLine(idx);
        } catch (Exceptions.RowNotFound e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits a property of an item directly in the CSV file. Searched based by Item ID.
     *
     * @param f The file to edit.
     * @param ID The ID of the item.
     * @param change The new string.
     * @param original The original string.
     */
    public void EditItem(CSV f, long ID, String change, String original) {
        try {
            int idx =
                    f.GetMatchingRow(
                            Item.CSV_INDEX.ID,
                            Long.toString(ID)); // Get the line of our original object.
            String line = f.GetLine(idx); // Get the original line. From its ID, note we do not call
            // f.GetFromID() because we need to use the id later.
            List<String> l = CSV.ParseCSV(line); // Load the line into a List to make editing easy.
            int in = l.indexOf(original); // Find the index of the line to edit.
            l.set(in, change); // Make the changes.
            String cs = CSV.ToCSV(l); // Convert the list back into CSV.
            f.EditLine(idx, cs); // Write the CSV at the original index.
        } catch (Exceptions.RowNotFound e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a pateron object to a CSV file.
     *
     * @param f The file to add the item to.
     * @param p The pateron to be added to the file.
     */
    public void AddPateron(CSV f, Pateron p) {
        String csv = p.ToCSV();
        csv = CSV.RemoveFirstElement(csv); // Drop the ID in case they differ for some reason.
        ArrayList<String> found =
                f.FindItems(
                        Pateron.CSV_INDEX.NAME,
                        p.getName()); // Find items with matching titles (any element would
        // work we just need possible matches)
        for (String s : found) {
            s = CSV.RemoveFirstElement(s);
            if (s.equals(csv)) return; // Our item is already in the file.
        }
        f.Write(p.ToCSV(), true);
    }

    /**
     * Finds all books matching a given query in a file.
     *
     * @param f The file to check.
     * @param index The CSV_INDEX of the search.
     * @param search The string to search for.
     * @return An ArrayList of books that match the search.
     */
    public ArrayList<Book> FindBooks(CSV f, Item.CSV_INDEX index, String search) {
        ArrayList<Book> found = new ArrayList<>();
        for (String s : f.getStrings()) {
            if (CSV.Query(s, index).equals(search)) {
                // found.add(Book.FromCSV(s));
            }
        }
        return found;
    }

    /**
     * Finds all magazines matching a given query in a file.
     *
     * @param f The file to check.
     * @param index The CSV_INDEX of the search.
     * @param search The string to search for.
     * @return An ArrayList of magszines that match the search.
     */
    public ArrayList<Magazine> FindMagazines(CSV f, Item.CSV_INDEX index, String search) {
        ArrayList<Magazine> found = new ArrayList<>();
        for (String s : f.getStrings()) {
            if (CSV.Query(s, index).equals(search)) {
                found.add(Magazine.FromCSV(s));
            }
        }
        return found;
    }

    /**
     * Renews a magazine. Calculates and outstanding late fees ($5.00/day). If the magazine is
     * renewed before it is due the new renewal date will be rounded to when it was supposed to be
     * due + the renewal time. For example, a magazine taken out on the 10th with a seven-day
     * renewal time that is renewed on the fifteenth will have its next renewal time set to the 24th
     * not the 22nd.
     *
     * @param m The magazine to renew.
     * @param p The pateron renewing the magazine.
     * @return The fees due as a float.
     */
    public float RenewMagazine(Magazine m, Pateron p) {
        float fee = 0.0f;
        var checkedOut = p.getCheckedOut();
        if (!checkedOut.containsKey(m.getID())) return fee; // pateron doesn't have this book out
        LocalDate renewalDate = LocalDate.now(); // get the current time to calculate days remaining
        Period periodUntilDue =
                renewalDate.until(
                        checkedOut
                                .get(m.getID())
                                .plusDays(m.getRenewalDate())); // calculate time until book is due
        int daysUntilDue = periodUntilDue.getDays();
        if (daysUntilDue
                >= 0) { // if the magazine has been returned on time we will round to the days and
            // renew
            renewalDate =
                    renewalDate.plusDays(
                            daysUntilDue); // renew the book in the future, clamps to the max
            // allowed time out
        } else {
            fee = daysUntilDue * -5.0f; // days until due is negative when overdue so calculate fee
            // accordingly
        }
        p.setCheckedOut(
                m.getID(),
                renewalDate); // set the checked-out time to the current date or the adjusted date
        // for early returners
        return fee;
    }
}
