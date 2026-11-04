/* @test    @summary Tests for Scanner constructor with InputStream and Charset.    @run main ScannerWitupTest */

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullSource();
        testNullCharset();
        testValidInput();
    }

    public static void testNullSource() {
        try {
            new Scanner((InputStream) null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null source");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testNullCharset() {
        InputStream input = new ByteArrayInputStream(new byte[] {65, 66, 67});
        try {
            new Scanner(input, (Charset) null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testValidInput() {
        String data = "abc 123";
        InputStream input = new ByteArrayInputStream(data.getBytes());
        Scanner scanner = new Scanner(input, StandardCharsets.UTF_8);
        try {
            if (!scanner.hasNext()) {
                throw new AssertionError("Scanner should have tokens");
            }
            String token1 = scanner.next();
            if (!"abc".equals(token1)) {
                throw new AssertionError("Expected 'abc', got: " + token1);
            }
            if (!scanner.hasNextInt()) {
                throw new AssertionError("Scanner should have int token");
            }
            int number = scanner.nextInt();
            if (number != 123) {
                throw new AssertionError("Expected 123, got: " + number);
            }
        } finally {
            scanner.close();
        }
    }
}
