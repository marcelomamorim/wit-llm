/*
 * @test
 * @summary Test java.util.Scanner.hasNextLong(int radix) for valid and invalid radix values
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextLongValidRadix();
        testHasNextLongInvalidRadixLow();
        testHasNextLongInvalidRadixHigh();
    }

    private static void testHasNextLongValidRadix() {
        // Input string with a valid long number in radix 10
        Scanner scanner = new Scanner(new StringReader("12345"));
        boolean hasLong = scanner.hasNextLong(10);
        if (!hasLong) {
            throw new RuntimeException("Expected hasNextLong(10) to return true for input '12345'");
        }
        scanner.close();
    }

    private static void testHasNextLongInvalidRadixLow() {
        Scanner scanner = new Scanner(new StringReader("123"));
        try {
            scanner.hasNextLong(1); // radix < 2 should throw IllegalArgumentException
            throw new RuntimeException("Expected IllegalArgumentException for radix=1");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextLongInvalidRadixHigh() {
        Scanner scanner = new Scanner(new StringReader("123"));
        try {
            scanner.hasNextLong(37); // radix > 36 should throw IllegalArgumentException
            throw new RuntimeException("Expected IllegalArgumentException for radix=37");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }
}
