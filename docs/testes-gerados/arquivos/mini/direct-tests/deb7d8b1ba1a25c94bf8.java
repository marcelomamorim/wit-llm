/*
 * @test
 * @summary Test java.util.Scanner.next() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextReturnsToken();
        testNextThrowsWhenNoInput();
    }

    private static void testNextReturnsToken() {
        String input = "hello world";
        Scanner scanner = new Scanner(input);
        String token1 = scanner.next();
        if (!"hello".equals(token1)) {
            throw new AssertionError("Expected 'hello' but got '" + token1 + "'");
        }
        String token2 = scanner.next();
        if (!"world".equals(token2)) {
            throw new AssertionError("Expected 'world' but got '" + token2 + "'");
        }
        scanner.close();
    }

    private static void testNextThrowsWhenNoInput() {
        // Create a Scanner on empty input
        Scanner scanner = new Scanner("");
        boolean thrown = false;
        try {
            scanner.next();
        } catch (java.util.NoSuchElementException e) {
            thrown = true;
        } finally {
            scanner.close();
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty input");
        }
    }
}
