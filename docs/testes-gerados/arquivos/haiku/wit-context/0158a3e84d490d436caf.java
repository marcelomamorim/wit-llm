/*
 * @test
 * @summary Witup-derived tests for Scanner.nextLine behavior
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testLinesWithAndWithoutTerminator();
        testCrLfTerminator();
        testEmptyLine();
        testEmptyInputThrowsNoSuchElement();
        testExhaustedInputThrowsNoSuchElement();
        testClosedScannerThrowsIllegalState();
    }

    private static void testLinesWithAndWithoutTerminator() {
        Scanner sc = new Scanner(new StringReader("abc\ndef"));
        check("abc".equals(sc.nextLine()), "first line");
        check("def".equals(sc.nextLine()), "second line without terminator");
        expectNoSuchElement(sc);
        sc.close();
    }

    private static void testCrLfTerminator() {
        Scanner sc = new Scanner(new StringReader("a\r\nb"));
        check("a".equals(sc.nextLine()), "CRLF line content excludes terminator");
        check("b".equals(sc.nextLine()), "last line after CRLF");
        sc.close();
    }

    private static void testEmptyLine() {
        Scanner sc = new Scanner(new StringReader("\n"));
        check("".equals(sc.nextLine()), "empty line returns empty string");
        expectNoSuchElement(sc);
        sc.close();
    }

    private static void testEmptyInputThrowsNoSuchElement() {
        Scanner sc = new Scanner(new StringReader(""));
        expectNoSuchElement(sc);
        sc.close();
    }

    private static void testExhaustedInputThrowsNoSuchElement() {
        Scanner sc = new Scanner(new StringReader("x\n"));
        check("x".equals(sc.nextLine()), "consume only line");
        expectNoSuchElement(sc);
        expectNoSuchElement(sc);
        sc.close();
    }

    private static void testClosedScannerThrowsIllegalState() {
        Scanner sc = new Scanner(new StringReader("x\n"));
        sc.close();
        try {
            sc.nextLine();
            throw new RuntimeException("expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void expectNoSuchElement(Scanner sc) {
        try {
            sc.nextLine();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
