/*
 * @test
 * @summary Test Scanner constructor with InputStream and charsetName
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testConstructorWithInputStreamAndCharsetName();
    }

    public static void testConstructorWithInputStreamAndCharsetName() {
        String testString = "Sample input";
        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
        String charsetName = "UTF-8";
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputStream, charsetName);
            if (scanner.hasNext()) {
                String token = scanner.next();
                assert token.equals("Sample") : "Expected 'Sample', got: " + token;
            } else {
                throw new AssertionError("Scanner did not find any tokens.");
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
