/*
 * @test
 * @summary Behavioral tests for Scanner.findInLine(Pattern)
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNPE();
        testMatchOnCurrentLineAndAdvances();
        testNoMatchReturnsNull();
        testMatchDoesNotCrossLineBoundary();
        testEmptyRemainingLineReturnsNull();
        testInputStreamSource();
    }

    static void testNullPatternThrowsNPE() {
        Scanner s = new Scanner(new StringReader("abc"));
        boolean thrown = false;
        try {
            s.findInLine((Pattern) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null pattern must throw NullPointerException");
    }

    static void testMatchOnCurrentLineAndAdvances() {
        Scanner s = new Scanner(new StringReader("1 fish 2 fish\nred"));
        String first = s.findInLine(Pattern.compile("\\d+"));
        check("1".equals(first), "expected \"1\" but got " + first);
        String next = s.next();
        check("fish".equals(next), "expected \"fish\" after findInLine but got " + next);
    }

    static void testNoMatchReturnsNull() {
        Scanner s = new Scanner(new StringReader("abc\ndef"));
        String r = s.findInLine(Pattern.compile("xyz"));
        check(r == null, "expected null when no match on line but got " + r);
    }

    static void testMatchDoesNotCrossLineBoundary() {
        Scanner s = new Scanner(new StringReader("foo\nbar"));
        String r = s.findInLine(Pattern.compile("bar"));
        check(r == null, "match on next line must not be returned, got " + r);
    }

    static void testEmptyRemainingLineReturnsNull() {
        Scanner s = new Scanner(new StringReader("\nabc"));
        String r = s.findInLine(Pattern.compile("abc"));
        check(r == null, "empty current line must yield null, got " + r);
    }

    static void testInputStreamSource() {
        Scanner s = new Scanner(new ByteArrayInputStream("x42y".getBytes()));
        String r = s.findInLine(Pattern.compile("\\d+"));
        check("42".equals(r), "expected \"42\" but got " + r);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
