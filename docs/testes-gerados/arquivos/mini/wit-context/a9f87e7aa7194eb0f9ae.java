/*
 * @test
 * @summary Test java.util.Scanner.next(String) method for normal and closed scanner states
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;
import java.util.NoSuchElementException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextWithValidPattern();
        testNextOnClosedScannerThrowsIllegalStateException();
    }

    private static void testNextWithValidPattern() {
        String input = "hello world";
        Scanner scanner = new Scanner(new StringReader(input));
        String token = scanner.next("\\w+"); // pattern matching word characters
        if (!"hello".equals(token)) {
            throw new AssertionError("Expected 'hello' but got '" + token + "'");
        }
        scanner.close();
    }

    private static void testNextOnClosedScannerThrowsIllegalStateException() {
        Scanner scanner = new Scanner(new StringReader("data"));
        scanner.close();
        try {
            scanner.next(".*");
            throw new AssertionError("Expected IllegalStateException when calling next() on closed scanner");
        } catch (IllegalStateException expected) {
            // expected exception
        }
    }
}
