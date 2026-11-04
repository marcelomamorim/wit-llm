/*
 * @test
 * @summary Test for Scanner constructor with InputStream and charsetName
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCharsetName();
        testConstructorWithValidCharsetName();
    }

    private static void testConstructorWithNullCharsetName() {
        InputStream input = new ByteArrayInputStream(new byte[] {65, 66, 67}); // "ABC"
        try {
            new Scanner(input, null);
            System.err.println("Expected NullPointerException for null charsetName, but none was thrown.");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null charsetName.");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithValidCharsetName() {
        InputStream input = new ByteArrayInputStream(new byte[] {65, 66, 67}); // "ABC"
        try {
            Scanner scanner = new Scanner(input, "UTF-8");
            if (scanner.hasNext()) {
                String token = scanner.next();
                if ("ABC".equals(token)) {
                    System.out.println("Successfully read token: " + token);
                } else {
                    System.err.println("Unexpected token: " + token);
                }
            } else {
                System.err.println("Scanner did not find any token.");
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
