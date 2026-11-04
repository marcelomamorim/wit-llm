/*
 * @test
 * @summary Test java.util.Scanner.useRadix(int) for valid and invalid radix values
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testValidRadix();
        testInvalidRadixBelowMin();
        testInvalidRadixAboveMax();
    }

    private static void testValidRadix() {
        Scanner scanner = new Scanner("");
        // Test with valid radix values at boundaries
        int minRadix = Character.MIN_RADIX; // 2
        int maxRadix = Character.MAX_RADIX; // 36

        Scanner result1 = scanner.useRadix(minRadix);
        if (result1 != scanner) {
            throw new AssertionError("useRadix should return this when setting valid radix");
        }

        Scanner result2 = scanner.useRadix(maxRadix);
        if (result2 != scanner) {
            throw new AssertionError("useRadix should return this when setting valid radix");
        }

        // Test with a typical valid radix
        Scanner result3 = scanner.useRadix(10);
        if (result3 != scanner) {
            throw new AssertionError("useRadix should return this when setting valid radix");
        }
    }

    private static void testInvalidRadixBelowMin() {
        Scanner scanner = new Scanner("");
        int invalidRadix = Character.MIN_RADIX - 1; // 1
        try {
            scanner.useRadix(invalidRadix);
            throw new AssertionError("Expected IllegalArgumentException for radix below MIN_RADIX");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains(String.valueOf(invalidRadix))) {
                throw new AssertionError("Exception message should contain the invalid radix value");
            }
        }
    }

    private static void testInvalidRadixAboveMax() {
        Scanner scanner = new Scanner("");
        int invalidRadix = Character.MAX_RADIX + 1; // 37
        try {
            scanner.useRadix(invalidRadix);
            throw new AssertionError("Expected IllegalArgumentException for radix above MAX_RADIX");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains(String.valueOf(invalidRadix))) {
                throw new AssertionError("Exception message should contain the invalid radix value");
            }
        }
    }
}
