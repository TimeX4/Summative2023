/* (C)2023 */
package org.library;

import java.time.LocalDate;
import java.util.HashMap;

public class Pateron {
    // Load from file
    private static long NEXT_ID;
    private String Name;
    private String PhoneNumber;
    private final long ID;
    private HashMap<Item, LocalDate> CheckedOut;
    private float DueFees;

    Pateron() {
        ID = NEXT_ID++;
    }
}
