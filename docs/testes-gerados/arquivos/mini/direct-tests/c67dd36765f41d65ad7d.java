/*
 * @test
 * @summary Test java.util.Scanner.useRadix(int) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testUseRadixValid();
        testUseRadixSameValueReturnsSameInstance();
        testUseRadixInvalidLow();
        testUseRadixInvalidHigh();
    }

    private static void testUseRadixValid() {
        Scanner scanner = new Scanner("");
        Scanner returned = scanner.useRadix(10);
        if (returned != scanner) {
            throw new RuntimeException("useRadix should return this when radix changes from default");
        }
        // Changing to a different valid radix
        returned = scanner.useRadix(16);
        if (returned != scanner) {
            throw new RuntimeException("useRadix should return this when radix changes");
        }
    }

    private static void testUseRadixSameValueReturnsSameInstance() {
        Scanner scanner = new Scanner("");
        // default radix is 10
        Scanner first = scanner.useRadix(10);
        Scanner second = scanner.useRadix(10);
        if (first != second) {
            throw new RuntimeException("useRadix with same radix should return same instance");
        }
    }

    private static void testUseRadixInvalidLow() {
        Scanner scanner = new Scanner("");
        try {
            scanner.useRadix(Character.MIN_RADIX - 1);
            throw new RuntimeException("Expected IllegalArgumentException for radix below Character.MIN_RADIX");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testUseRadixInvalidHigh() {
        Scanner scanner = new Scanner("");
        try {
            scanner.useRadix(Character.MAX_RADIX + 1);
            throw new RuntimeException("Expected IllegalArgumentException for radix above Character.MAX_RADIX");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
