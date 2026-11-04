/*
 * @test
 * @summary Test java.util.Scanner.hasNext() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextWithNonEmptyInput();
        testHasNextWithEmptyInput();
        testHasNextWithWhitespaceOnly();
    }

    private static void testHasNextWithNonEmptyInput() {
        String input = "hello world";
        Scanner scanner = new Scanner(input);
        boolean hasNext = scanner.hasNext();
        if (!hasNext) {
            throw new AssertionError("Expected hasNext() to return true for non-empty input");
        }
        // next() should return the first token
        String token = scanner.next();
        if (!"hello".equals(token)) {
            throw new AssertionError("Expected first token to be 'hello', got: " + token);
        }
        scanner.close();
    }

    private static void testHasNextWithEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        boolean hasNext = scanner.hasNext();
        if (hasNext) {
            throw new AssertionError("Expected hasNext() to return false for empty input");
        }
        scanner.close();
    }

    private static void testHasNextWithWhitespaceOnly() {
        String input = "   \t  \n  ";
        Scanner scanner = new Scanner(input);
        boolean hasNext = scanner.hasNext();
        if (hasNext) {
            throw new AssertionError("Expected hasNext() to return false for input with only whitespace");
        }
        scanner.close();
    }
}
