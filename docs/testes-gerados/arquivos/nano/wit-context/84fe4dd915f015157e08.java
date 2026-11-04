/*
 * @test
 * @summary Test for Scanner(Path, String) constructor handling null charsetName
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCharsetName();
        testConstructorWithValidParameters();
    }

    private static void testConstructorWithNullCharsetName() {
        Path path = null;
        try {
            new Scanner(path, null);
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null charsetName");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected for null charsetName");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithValidParameters() {
        try {
            String content = "sample text";
            Path tempFile = Files.createTempFile("testfile", ".txt");
            Files.write(tempFile, content.getBytes());
            Scanner scanner = new Scanner(tempFile, "UTF-8");
            if (scanner.hasNext()) {
                String token = scanner.next();
                if ("sample".equals(token)) {
                    System.out.println("Passed: Scanner successfully read token from file");
                } else {
                    System.err.println("Failed: Unexpected token read: " + token);
                }
            } else {
                System.err.println("Failed: Scanner did not find any token");
            }
            scanner.close();
            Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            System.err.println("Failed: Exception during valid constructor test: " + e);
        }
    }
}
