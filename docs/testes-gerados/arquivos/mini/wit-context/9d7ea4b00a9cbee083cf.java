/*
 * @test
 * @summary Test java.util.Scanner.hasNextByte(int radix) for valid and invalid radix values
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testHasNextByteValidRadix();
        testHasNextByteInvalidRadixLow();
        testHasNextByteInvalidRadixHigh();
    }

    private static void testHasNextByteValidRadix() {
        // Input string contains a valid byte number in radix 10
        Scanner scanner = new Scanner(new StringReader("127"));
        boolean hasNext = scanner.hasNextByte(10);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextByte(10) to return true for input '127'");
        }
        scanner.close();
    }

    private static void testHasNextByteInvalidRadixLow() {
        Scanner scanner = new Scanner(new StringReader("0"));
        try {
            scanner.hasNextByte(1); // radix < 2 should throw IllegalArgumentException
            throw new RuntimeException("Expected IllegalArgumentException for radix=1");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextByteInvalidRadixHigh() {
        Scanner scanner = new Scanner(new StringReader("0"));
        try {
            scanner.hasNextByte(37); // radix > 36 should throw IllegalArgumentException
            throw new RuntimeException("Expected IllegalArgumentException for radix=37");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }
}
