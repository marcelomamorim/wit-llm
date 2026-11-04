/*
 * @test
 * @summary Witup-derived tests for Scanner.findAll(Pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullPattern();
        testClosedScanner();
        testFindAllTokens();
        testNoMatches();
        testStreamCloseClosesScanner();
    }

    static void testNullPattern() {
        Scanner s = new Scanner("abc");
        try {
            s.findAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } finally {
            s.close();
        }
    }

    static void testClosedScanner() {
        Scanner s = new Scanner("1 2");
        s.close();
        try {
            s.findAll(Pattern.compile("[0-9]"));
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testFindAllTokens() {
        Scanner s = new Scanner("1 fish 2 fish 3");
        try {
            String joined = s.findAll(Pattern.compile("[0-9]+"))
                             .map(MatchResult::group)
                             .collect(Collectors.joining(","));
            if (!joined.equals("1,2,3")) {
                throw new RuntimeException("unexpected matches: " + joined);
            }
        } finally {
            s.close();
        }
    }

    static void testNoMatches() {
        Scanner s = new Scanner("abc");
        try {
            long n = s.findAll(Pattern.compile("[0-9]+")).count();
            if (n != 0) {
                throw new RuntimeException("expected 0 matches but was " + n);
            }
        } finally {
            s.close();
        }
    }

    static void testStreamCloseClosesScanner() {
        Scanner s = new Scanner("1 2");
        s.findAll(Pattern.compile("[0-9]")).close();
        try {
            s.hasNext();
            throw new RuntimeException("expected IllegalStateException after stream close");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
