/*
 * @test
 * @summary Test java.util.Scanner.hasNextInt() for valid radix and exception on invalid radix
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testHasNextIntValidRadix();
        testHasNextIntInvalidRadixLow();
        testHasNextIntInvalidRadixHigh();
    }

    private static void testHasNextIntValidRadix() {
        // Using default radix (10) which is valid
        Scanner scanner = new Scanner("123 456");
        boolean result = scanner.hasNextInt();
        if (!result) {
            throw new RuntimeException("Expected hasNextInt() to return true for valid int input");
        }
        scanner.close();
    }

    private static void testHasNextIntInvalidRadixLow() {
        // Create a subclass to access hasNextInt(int) with invalid radix < 2
        Scanner scanner = new Scanner(new StringReader("123")) {
            public boolean testHasNextIntWithRadix(int radix) {
                try {
                    return hasNextInt(radix);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        };
        boolean caught = !((Scanner)scanner).testHasNextIntWithRadix(1);
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException for radix < 2");
        }
        scanner.close();
    }

    private static void testHasNextIntInvalidRadixHigh() {
        // Create a subclass to access hasNextInt(int) with invalid radix > 36
        Scanner scanner = new Scanner(new StringReader("123")) {
            public boolean testHasNextIntWithRadix(int radix) {
                try {
                    return hasNextInt(radix);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        };
        boolean caught = !((Scanner)scanner).testHasNextIntWithRadix(37);
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException for radix > 36");
        }
        scanner.close();
    }
}
