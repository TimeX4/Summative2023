/* (C)2023 */
package org.library;

import org.file.File;
import org.gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        LibraryGUI.draw();
    }

    public static void cleanup() {
        File f = new File("/library_files/next_id");
        f.EmptyContents();
        // TODO: 2023-05-18 Add search, Hash passwords, polish!
        Item.WriteDatabases();
        Patron.WriteDatabases();
        Item.WriteNextID(f);
        Patron.WriteNextID(f);
    }
}
