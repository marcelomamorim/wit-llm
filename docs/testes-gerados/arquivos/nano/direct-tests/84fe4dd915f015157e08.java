/*
 * @test
 * @summary Tests Scanner(Path, String) constructor for correct behavior and null handling
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidPathAndCharset();
        testConstructorWithNullPath();
        testConstructorWithNullCharsetName();
    }

    static void testConstructorWithValidPathAndCharset() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, Arrays.asList("line1", "line2"));
        try (Scanner scanner = new Scanner(tempFile, "UTF-8")) {
            if (!scanner.hasNextLine()) {
                throw new AssertionError("Expected at least one line");
            }
            String line = scanner.nextLine();
            if (!line.equals("line1")) {
                throw new AssertionError("Unexpected line content: " + line);
            }
        }
        Files.deleteIfExists(tempFile);
    }

    static void testConstructorWithNullPath() {
        try {
            new Scanner((Path) null, "UTF-8");
            throw new AssertionError("Expected NullPointerException for null Path");
        } catch (NullPointerException expected) {
        }
    }

    static void testConstructorWithNullCharsetName() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, Arrays.asList("line1"));
        try {
            new Scanner(tempFile, (String) null);
            throw new AssertionError("Expected NullPointerException for null charsetName");
        } catch (NullPointerException expected) {
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
