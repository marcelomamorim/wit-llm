/*
 * @test
 * @summary Witup-derived checks for Scanner.findAll(String): null pattern NPE and closed-scanner ISE
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.regex.MatchResult;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNPE();
        testClosedScannerWithPatternThrowsISE();
        testClosedScannerWithNullPatternThrowsNPE();
        testFindAllReturnsMatches();
    }

    static void expect(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPatternThrowsNPE() {
        Scanner sc = new Scanner(new StringReader("a1 a2"));
        boolean thrown = false;
        try {
            sc.findAll((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        sc.close();
        expect(thrown, "expected NullPointerException for null pattern");
    }

    static void testClosedScannerWithPatternThrowsISE() {
        Scanner sc = new Scanner(new StringReader("a1 a2"));
        sc.close();
        boolean thrown = false;
        try {
            sc.findAll("a[0-9]");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        expect(thrown, "expected IllegalStateException on closed scanner");
    }

    static void testClosedScannerWithNullPatternThrowsNPE() {
        Scanner sc = new Scanner(new StringReader("a1 a2"));
        sc.close();
        boolean npe = false;
        try {
            sc.findAll((String) null);
        } catch (NullPointerException e) {
            npe = true;
        } catch (IllegalStateException e) {
            npe = false;
        }
        expect(npe, "expected NullPointerException checked before closed state");
    }

    static void testFindAllReturnsMatches() {
        Scanner sc = new Scanner(new StringReader("a1 a2 b3"));
        Stream<MatchResult> results = sc.findAll("a[0-9]");
        long count = results.count();
        sc.close();
        expect(count == 2L, "expected 2 matches but was " + count);
    }
}
