/* (C)2023 */
package org.library;

import org.file.*;

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

    public void AddItem(CSV f, Item item) {
        String csv = item.ToCSV();
        csv = CSV.RemoveFirstElement(csv);
        ArrayList<String> found = f.FindItems(Item.CSV_INDEX.TITLE, item.getTitle());
        for (String s : found) {
            s = CSV.RemoveFirstElement(s);
            if (s.equals(csv)) return;
        }
        f.Write(item.ToCSV(), true);
    }

    public void RemoveItem(CSV f, long ID) {
        int idx = f.GetMatchingRow(Item.CSV_INDEX.ID, Long.toString(ID));
        if (idx == -1) return;
        f.DeleteLine(idx);
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
        int idx = f.GetMatchingRow(Item.CSV_INDEX.ID, Long.toString(ID));
        if (idx == -1) return;
        String line = f.GetLine(idx);
        List<String> l = CSV.ParseCSV(line);
        int in = l.indexOf(original);
        l.set(in, change);
        String cs = CSV.ToCSV(l);
        f.EditLine(idx, cs);
    }

    public ArrayList<Book> FindBooks(CSV f, Item.CSV_INDEX index, String search) {
        ArrayList<Book> found = new ArrayList<>();
        for (String s : f.AsStrings()) {
            if (CSV.Query(s, index).equals(search)) {
                found.add(Book.FromCSV(s));
            }
        }
        return found;
    }

    public ArrayList<Magazine> FindMagazines(CSV f, Item.CSV_INDEX index, String search) {
        ArrayList<Magazine> found = new ArrayList<>();
        for (String s : f.AsStrings()) {
            if (CSV.Query(s, index).equals(search)) {
                found.add(Magazine.FromCSV(s));
            }
        }
        return found;
    }
}
