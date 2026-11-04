/*
 * @test
 * @summary Deterministic tests for java.util.Scanner.hasNextShort(int radix)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testHasNextShortValidRadix();
        testHasNextShortInvalidRadixLow();
        testHasNextShortInvalidRadixHigh();
    }

    private static void testHasNextShortValidRadix() {
        // Test with valid radix and input that can be parsed as short
        Scanner sc = new Scanner(new StringReader("7B")); // 7B hex = 123 decimal
        boolean hasNext = sc.hasNextShort(16);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextShort to return true for valid short input with radix 16");
        }
        sc.close();

        // Test with valid radix and input that cannot be parsed as short
        sc = new Scanner(new StringReader("8000")); // 8000 hex = 32768 decimal (out of short range)
        hasNext = sc.hasNextShort(16);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextShort to return false for input out of short range");
        }
        sc.close();
    }

    private static void testHasNextShortInvalidRadixLow() {
        // Test with radix less than 2, expect IllegalArgumentException
        Scanner sc = new Scanner(new StringReader("123"));
        try {
            sc.hasNextShort(1);
            throw new RuntimeException("Expected IllegalArgumentException for radix < 2");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            sc.close();
        }
    }

    private static void testHasNextShortInvalidRadixHigh() {
        // Test with radix greater than 36, expect IllegalArgumentException
        Scanner sc = new Scanner(new StringReader("123"));
        try {
            sc.hasNextShort(37);
            throw new RuntimeException("Expected IllegalArgumentException for radix > 36");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            sc.close();
        }
    }
}
