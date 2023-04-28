/* (C)2023 */
package org.file;

import java.io.*;
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
            if (append) writer.newLine();
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
            while ((line = file.readLine()) != null) {
                if (i == idx) {
                    line = newLine;
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
            while ((line = file.readLine()) != null) {
                if (i == idx) break;
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
