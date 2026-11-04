/*
 * @test
 * @summary Test Scanner(InputStream, String) constructor with valid and invalid charset names
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testValidCharset();
        testInvalidCharset();
    }

    private static void testValidCharset() throws Exception {
        String input = "hello world";
        InputStream is = new ByteArrayInputStream(input.getBytes("UTF-8"));
        Scanner scanner = new Scanner(is, "UTF-8");
        String token = scanner.next();
        if (!"hello".equals(token)) {
            throw new RuntimeException("Expected 'hello' but got '" + token + "'");
        }
        scanner.close();
    }

    private static void testInvalidCharset() throws Exception {
        String input = "hello world";
        InputStream is = new ByteArrayInputStream(input.getBytes("UTF-8"));
        try {
            new Scanner(is, "INVALID-CHARSET");
            throw new RuntimeException("Expected IllegalArgumentException for invalid charset name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
