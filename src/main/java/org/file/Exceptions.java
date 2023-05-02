/* (C)2023 */
package org.file;

public class Exceptions {
    public static class RowNotFound extends Exception {
        public RowNotFound(String errorMessage) {
            super(errorMessage);
        }
    }
}
