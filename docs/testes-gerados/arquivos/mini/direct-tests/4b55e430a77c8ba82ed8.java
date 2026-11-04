/*
 * @test
 * @summary Test java.util.Scanner.hasNextInt() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextIntWithInteger();
        testHasNextIntWithNonInteger();
        testHasNextIntWithEmptyInput();
        testHasNextIntWithWhitespaceOnly();
        testHasNextIntWithNegativeInteger();
    }

    private static void testHasNextIntWithInteger() {
        Scanner sc = new Scanner("123");
        if (!sc.hasNextInt()) {
            throw new AssertionError("Expected hasNextInt() to be true for input '123'");
        }
        sc.close();
    }

    private static void testHasNextIntWithNonInteger() {
        Scanner sc = new Scanner("abc");
        if (sc.hasNextInt()) {
            throw new AssertionError("Expected hasNextInt() to be false for input 'abc'");
        }
        sc.close();
    }

    private static void testHasNextIntWithEmptyInput() {
        Scanner sc = new Scanner("");
        if (sc.hasNextInt()) {
            throw new AssertionError("Expected hasNextInt() to be false for empty input");
        }
        sc.close();
    }

    private static void testHasNextIntWithWhitespaceOnly() {
        Scanner sc = new Scanner("   \t  \n  ");
        if (sc.hasNextInt()) {
            throw new AssertionError("Expected hasNextInt() to be false for whitespace only input");
        }
        sc.close();
    }

    private static void testHasNextIntWithNegativeInteger() {
        Scanner sc = new Scanner("-42");
        if (!sc.hasNextInt()) {
            throw new AssertionError("Expected hasNextInt() to be true for input '-42'");
        }
        sc.close();
    }
}
