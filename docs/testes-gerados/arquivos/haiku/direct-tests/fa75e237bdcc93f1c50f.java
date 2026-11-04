/*
 * @test
 * @summary Verifies public behavior of Scanner.hasNext(String pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchingDigitsToken();
        testNonMatchingToken();
        testPartialTokenDoesNotMatch();
        testEmptyInput();
        testWhitespaceOnlyInput();
        testHasNextDoesNotConsumeToken();
        testCustomDelimiter();
    }

    static void testMatchingDigitsToken() {
        Scanner sc = new Scanner("123 abc");
        check(sc.hasNext("\\d+"), "next token '123' should match \\d+");
    }

    static void testNonMatchingToken() {
        Scanner sc = new Scanner("123 abc");
        check(!sc.hasNext("[a-z]+"), "next token '123' should not match [a-z]+");
    }

    static void testPartialTokenDoesNotMatch() {
        Scanner sc = new Scanner("123abc");
        check(!sc.hasNext("\\d+"), "token '123abc' must not match \\d+ as a whole");
    }

    static void testEmptyInput() {
        Scanner sc = new Scanner("");
        check(!sc.hasNext("\\d+"), "empty input should have no matching token");
    }

    static void testWhitespaceOnlyInput() {
        Scanner sc = new Scanner("   ");
        check(!sc.hasNext("\\d+"), "whitespace-only input should have no matching token");
    }

    static void testHasNextDoesNotConsumeToken() {
        Scanner sc = new Scanner("42 x");
        check(sc.hasNext("\\d+"), "first hasNext should be true");
        check(sc.hasNext("\\d+"), "second hasNext should still be true");
        int v = sc.nextInt();
        check(v == 42, "nextInt should return 42, got " + v);
    }

    static void testCustomDelimiter() {
        Scanner sc = new Scanner("1,2,3").useDelimiter(",");
        check(sc.hasNext("\\d"), "token '1' should match \\d with custom delimiter");
        String tok = sc.next();
        check("1".equals(tok), "next token should be '1', got " + tok);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
