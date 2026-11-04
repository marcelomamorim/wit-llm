/*
 * @test
 * @summary Test for Scanner.findInLine(String pattern) method, focusing on exception path when scanner is closed.
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindInLineWhenClosed();
    }

    private static void testFindInLineWhenClosed() {
        Scanner scanner = null;
        try {
            // Initialize scanner with some input
            String inputString = "test input";
            InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
            scanner = new Scanner(inputStream);
            // Close the scanner to set the 'closed' state
            scanner.close();
            // Attempt to call findInLine, expecting IllegalStateException
            try {
                scanner.findInLine("test");
                // If no exception, test fails
                System.err.println("Expected IllegalStateException was not thrown.");
                System.exit(1);
            } catch (IllegalStateException e) {
                // Expected exception caught, test passes
                System.out.println("Caught expected IllegalStateException.");
            }
        } catch (Exception e) {
            // Unexpected exception, test fails
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
