/*
 * @test
 * @summary Verifies Scanner.next(Pattern) returns matching tokens, consumes them in order, and rejects a null pattern
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testReturnsMatchingToken();
        testSequentialTokensConsumedInOrder();
        testNullPatternThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsMatchingToken() {
        Scanner s = new Scanner("abc 123");
        String token = s.next(Pattern.compile("[a-z]+"));
        check("abc".equals(token), "expected 'abc' but was '" + token + "'");
        s.close();
    }

    static void testSequentialTokensConsumedInOrder() {
        Scanner s = new Scanner("abc 123 def");
        check("abc".equals(s.next(Pattern.compile("[a-z]+"))), "first token mismatch");
        check("123".equals(s.next(Pattern.compile("\\d+"))), "second token mismatch");
        check("def".equals(s.next(Pattern.compile("[a-z]+"))), "third token mismatch");
        s.close();
    }

    static void testNullPatternThrowsNullPointerException() {
        Scanner s = new Scanner("abc");
        try {
            s.next((Pattern) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            s.close();
        }
    }
}
