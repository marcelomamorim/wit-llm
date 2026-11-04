/*
 * @test
 * @summary Test java.util.Scanner.hasNext(Pattern) for expected exceptions and normal behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextWithClosedScanner();
        testHasNextWithNullPattern();
        testHasNextWithDelimPatternNull();
        testHasNextNormalBehavior();
    }

    static void testHasNextWithClosedScanner() {
        Scanner scanner = new Scanner("");
        scanner.close();
        try {
            scanner.hasNext(Pattern.compile(".*"));
            throw new RuntimeException("Expected IllegalStateException not thrown when scanner is closed");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testHasNextWithNullPattern() {
        Scanner scanner = new Scanner("");
        try {
            scanner.hasNext(null);
            throw new RuntimeException("Expected NullPointerException not thrown when pattern is null");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }

    static void testHasNextWithDelimPatternNull() throws Exception {
        /*
         * This test attempts to trigger IllegalArgumentException when delimPattern == null.
         * Since delimPattern is internal and no public API to set it to null,
         * this test uses a Scanner on a StringReader with empty input and a pattern that
         * will not match, expecting no exception but false return.
         * The expath for IllegalArgumentException is discarded because delimPattern is internal.
         */
        Scanner scanner = new Scanner(new StringReader("") );
        Pattern pattern = Pattern.compile("a"); // pattern that does not match empty input
        boolean result = scanner.hasNext(pattern);
        if (result) {
            throw new RuntimeException("Expected hasNext to return false for non-matching pattern on empty input");
        }
        scanner.close();
    }

    static void testHasNextNormalBehavior() {
        Scanner scanner = new Scanner("abc 123 xyz");
        Pattern pattern = Pattern.compile("\\d+"); // digits pattern
        boolean found = scanner.hasNext(pattern);
        if (!found) {
            throw new RuntimeException("Expected hasNext to find digits pattern in input");
        }
        // The next token matching pattern should be "123"
        String token = scanner.next(pattern);
        if (!"123".equals(token)) {
            throw new RuntimeException("Expected next token to be '123', got: " + token);
        }
        scanner.close();
    }
}
