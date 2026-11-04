/*
 * @test
 * @summary Basic tests for java.util.Scanner.findInLine(String) method
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindInLineSimpleMatch();
        testFindInLineNoMatch();
        testFindInLineEmptyPattern();
        testFindInLineNullPattern();
    }

    private static void testFindInLineSimpleMatch() {
        Scanner scanner = new Scanner("hello world 123");
        String result = scanner.findInLine("world");
        if (!"world".equals(result)) {
            throw new AssertionError("Expected 'world' but got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineNoMatch() {
        Scanner scanner = new Scanner("hello world 123");
        String result = scanner.findInLine("nomatch");
        if (result != null) {
            throw new AssertionError("Expected null but got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineEmptyPattern() {
        Scanner scanner = new Scanner("abc");
        String result = scanner.findInLine("");
        if (result == null || !"".equals(result)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineNullPattern() {
        Scanner scanner = new Scanner("abc");
        try {
            scanner.findInLine(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }
}
