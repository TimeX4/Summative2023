/* (C)2023 */
package org.library;

import org.file.File;
import org.gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        // TODO: 2023-05-24 find out where notifiers are needed in the gui!
        /* BUGS
        Invalid type checking on fields, unexpected input, large values, small values, wrapper class for number conversion, escape characters
        empty strings,
        Edit on AllItemsUser when nothing is present or selected throws error
        Don't allow books with same name and author unless reference only is different
        Don't allow patrons with the same phone number <- add notifier, phone number already registed
        When editing passwords, if password field isn't changed, it double hashes
        All library items page, buttons doesn't have null (nothing selected) check
        Add button on patron and books needs to be validated before adding, currently it adds empty strings
        When patron gets deleted, ensure all of their books are checked back in first
         */
        LibraryGUI.draw();
    }

    public static void cleanup() {
        File f = new File("/library_files/next_id");
        f.EmptyContents();
        Item.WriteDatabases();
        Patron.WriteDatabases();
        Item.WriteNextID(f);
        Patron.WriteNextID(f);
    }
}
