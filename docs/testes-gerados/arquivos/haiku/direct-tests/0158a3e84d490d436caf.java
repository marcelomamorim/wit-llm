/*
 * @test
 * @summary Basic observable behavior of Scanner.nextLine()
 * @run main ScannerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testLfSeparatedLines();
        testCrLfSeparator();
        testLastLineWithoutSeparator();
        testEmptyLineAtStart();
        testEmptyInputThrows();
        testExhaustedScannerThrows();
    }

    static void testLfSeparatedLines() {
        Scanner s = new Scanner("abc\ndef");
        check(s.nextLine(), "abc");
        check(s.nextLine(), "def");
        s.close();
    }

    static void testCrLfSeparator() {
        Scanner s = new Scanner("abc\r\ndef");
        check(s.nextLine(), "abc");
        check(s.nextLine(), "def");
        s.close();
    }

    static void testLastLineWithoutSeparator() {
        Scanner s = new Scanner("abc");
        check(s.nextLine(), "abc");
        s.close();
    }

    static void testEmptyLineAtStart() {
        Scanner s = new Scanner("\n");
        check(s.nextLine(), "");
        s.close();
    }

    static void testEmptyInputThrows() {
        Scanner s = new Scanner("");
        boolean thrown = false;
        try {
            s.nextLine();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NoSuchElementException expected for empty input");
        }
        s.close();
    }

    static void testExhaustedScannerThrows() {
        Scanner s = new Scanner("x\n");
        check(s.nextLine(), "x");
        boolean thrown = false;
        try {
            s.nextLine();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NoSuchElementException expected after input is exhausted");
        }
        s.close();
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }
}
