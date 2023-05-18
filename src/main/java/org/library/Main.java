/* (C)2023 */
package org.library;

import org.file.File;
import org.gui.LibraryGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LibraryGUI.draw();

        File f = new File("/library_files/next_id");
        f.EmptyContents();
        // TODO: 2023-05-18 Add search, rewrite PatronPage, Hash passwords, polish! 
        // TODO: 2023-05-18 WRITE ALL CHANGES TO DISK!!!! 
        Item.WriteNextID(f);
        Patron.WriteNextID(f);
    }
}
