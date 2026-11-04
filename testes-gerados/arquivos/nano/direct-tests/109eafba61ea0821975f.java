/*
 * @test
 * @summary Test Scanner constructor with InputStream and Charset
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInputStreamAndCharset();
        testConstructorWithNullInputStream();
        testConstructorWithNullCharset();
    }

    static void testConstructorWithValidInputStreamAndCharset() {
        String testString = "Sample input";
        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
        Charset charset = StandardCharsets.UTF_8;
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputStream, charset);
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                assert line.equals(testString) : "Expected input string, got: " + line;
            } else {
                throw new AssertionError("Scanner did not read input string") ;
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    static void testConstructorWithNullInputStream() {
        try {
            new Scanner((InputStream) null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullCharset() {
        InputStream inputStream = new ByteArrayInputStream("test".getBytes());
        try {
            new Scanner(inputStream, (Charset) null);
            throw new AssertionError("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
