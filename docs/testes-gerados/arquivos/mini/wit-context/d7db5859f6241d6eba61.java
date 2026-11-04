/*
 * @test
 * @summary Deterministic tests for java.util.Scanner.hasNextInt(int radix) method
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextIntValidRadix();
        testHasNextIntInvalidRadixLow();
        testHasNextIntInvalidRadixHigh();
    }

    private static void testHasNextIntValidRadix() {
        // Test with valid radix 10 and input "123"
        Scanner sc = new Scanner(new StringReader("123"));
        boolean hasInt = sc.hasNextInt(10);
        if (!hasInt) {
            throw new RuntimeException("Expected hasNextInt(10) to return true for input '123'");
        }
        int val = sc.nextInt(10);
        if (val != 123) {
            throw new RuntimeException("Expected nextInt(10) to return 123 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextIntInvalidRadixLow() {
        // Test with invalid radix less than 2, expect IllegalArgumentException
        Scanner sc = new Scanner(new StringReader("123"));
        try {
            sc.hasNextInt(1);
            throw new RuntimeException("Expected IllegalArgumentException for radix=1");
        } catch (IllegalArgumentException iae) {
            // Expected
        } finally {
            sc.close();
        }
    }

    private static void testHasNextIntInvalidRadixHigh() {
        // Test with invalid radix greater than 36, expect IllegalArgumentException
        Scanner sc = new Scanner(new StringReader("123"));
        try {
            sc.hasNextInt(37);
            throw new RuntimeException("Expected IllegalArgumentException for radix=37");
        } catch (IllegalArgumentException iae) {
            // Expected
        } finally {
            sc.close();
        }
    }
}
