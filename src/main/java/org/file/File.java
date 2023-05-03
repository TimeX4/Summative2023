/* (C)2023 */
package org.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class File {
    private final String readPath;
    private final String writePath;

    public File(String path) {
        readPath = path;
        writePath = "src/main/resources" + path;
    }

    /**
     * Opens a file as an InputStream
     *
     * @return An InputStream of the file object
     */
    private InputStream OpenFile() {
        InputStream stream = null;
        try {
            stream = File.class.getResourceAsStream(readPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * Opens a file as an ArrayList of Strings containing each line.
     *
     * @return An ArrayList of Strings containing each line.
     */
    public ArrayList<String> AsStrings() {
        ArrayList<String> content = new ArrayList<>();
        InputStream inp = OpenFile();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
        String l;
        try {
            // Iterate over the file and add each line to an ArrayList of strings.
            while ((l = reader.readLine()) != null) {
                content.add(l);
            }
            inp.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Writes content to the file object.
     *
     * @param content The content to be written.
     * @param append Append to the end of the file or write over all contents.
     */
    public void Write(String content, boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(writePath, append));
            if (append && Files.size(Path.of(writePath)) != 0)
                writer.newLine(); // If we are appending we need a new line.
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits a specified line of the file based on its index (Starting from 0)
     *
     * @param idx The index of the line to edit.
     * @param newLine The content to replace it with.
     */
    public void EditLine(int idx, String newLine) {
        InputStream inp = OpenFile();
        BufferedReader file = new BufferedReader(new InputStreamReader(inp));
        StringBuilder inputBuffer = new StringBuilder();
        String line;
        int i = 0;
        try {
            // Loop over the file and add each line back to an inputBuffer BUT modify the one we
            // want.
            while ((line = file.readLine()) != null) {
                if (i == idx) {
                    line = newLine; // Make the changes we want on the line we want.
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
                i++;
            }
            inp.close();
            file.close();
            Write(inputBuffer.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a specified line of the file based on its index (Starting from 0)
     *
     * @param idx The index of the line to delete.
     */
    public void DeleteLine(int idx) {
        InputStream inp = OpenFile();
        BufferedReader file = new BufferedReader(new InputStreamReader(inp));
        StringBuilder inputBuffer = new StringBuilder();
        String line;
        int i = 0;
        try {
            // Loop over the file and add each line back to an inputBuffer BUT skip the one we want
            // to delete.
            while ((line = file.readLine()) != null) {
                if (i == idx) {
                    i++;
                    continue;
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
                i++;
            }
            // delete the extra newline from the line we omitted
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            inp.close();
            file.close();
            Write(inputBuffer.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EmptyContents() {
        try {
            BufferedWriter writer =
                    Files.newBufferedWriter(
                            Path.of(writePath),
                            StandardOpenOption
                                    .TRUNCATE_EXISTING); // Open the file with the TRUNCATE_EXISTING
            // flag emptying its content
            writer.close(); // close the file afterwards.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a line of the file given the index.
     *
     * @param idx The index of the line requested.
     * @return The line as a string.
     */
    public String GetLine(int idx) {
        InputStream inp = OpenFile();
        BufferedReader file = new BufferedReader(new InputStreamReader(inp));
        String line = "";
        int i = 0;
        try {
            // iterate over the file until we hit the line we want, store it and break (not return
            // to close handles)
            while (file.readLine() != null) {
                if (i == idx) {
                    line = file.readLine();
                    break;
                }
                i++;
            }
            inp.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }
}
