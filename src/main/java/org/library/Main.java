/* (C)2023 */
package org.library;

import org.file.File;
import org.gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        // TODO: 2023-05-24 BUG TESTING: Invalid type checking on fields, unexpected input, large
        // values, small values, wrapper class for number conversion
        // TODO: 2023-05-24 Reference only in GUI, DVD Length -> Length (Minutes), find out where
        // notifiers are needed in the gui!
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
