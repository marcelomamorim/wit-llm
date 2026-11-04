/*
 * @test
 * @summary Test java.util.Scanner.tokens() method behavior including IllegalStateException when closed
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.stream.Stream;
import java.io.StringReader;
import java.lang.IllegalStateException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testTokensNormal();
        testTokensClosed();
    }

    private static void testTokensNormal() {
        Scanner scanner = new Scanner(new StringReader("one two three"));
        Stream<String> tokens = scanner.tokens();
        StringBuilder sb = new StringBuilder();
        tokens.forEach(s -> sb.append(s).append(" "));
        scanner.close();
        String result = sb.toString().trim();
        if (!"one two three".equals(result)) {
            throw new AssertionError("Expected tokens 'one two three' but got: " + result);
        }
    }

    private static void testTokensClosed() {
        Scanner scanner = new Scanner(new StringReader("ignored"));
        scanner.close();
        try {
            scanner.tokens();
            throw new AssertionError("Expected IllegalStateException when calling tokens() on closed Scanner");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
