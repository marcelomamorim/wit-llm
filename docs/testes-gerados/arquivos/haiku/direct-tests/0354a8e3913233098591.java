/*
 * @test
 * @summary Tests the public Formatter(String fileName) constructor: output written to the file, default FORMAT locale, and FileNotFoundException for a missing parent directory
 * @run main FormatterWitupTest
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("FormatterWitupTest", ".txt");
        try {
            testWritesFormattedOutput(tmp);
            testUsesDefaultFormatLocale(tmp);
            testMissingParentDirectoryThrows(tmp);
        } finally {
            tmp.delete();
        }
    }

    static void testWritesFormattedOutput(File f) throws IOException {
        Formatter fmt = new Formatter(f.getPath());
        fmt.format("x=%d", 42);
        fmt.close();
        check("x=42", readFirstLine(f));
    }

    static void testUsesDefaultFormatLocale(File f) throws IOException {
        Locale prev = Locale.getDefault(Locale.Category.FORMAT);
        Locale.setDefault(Locale.Category.FORMAT, Locale.GERMANY);
        try {
            Formatter fmt = new Formatter(f.getPath());
            fmt.format("%,.2f", 1234.5);
            fmt.close();
            check("1.234,50", readFirstLine(f));
        } finally {
            Locale.setDefault(Locale.Category.FORMAT, prev);
        }
    }

    static void testMissingParentDirectoryThrows(File f) {
        File missingParent = new File(f.getParentFile(), f.getName() + ".missingdir");
        File target = new File(missingParent, "child.txt");
        try {
            new Formatter(target.getPath());
            throw new RuntimeException("Expected FileNotFoundException was not thrown");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }

    static String readFirstLine(File f) throws IOException {
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), "UTF-8"))) {
            return r.readLine();
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }
}
