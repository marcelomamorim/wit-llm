/*
 * @test
 * @summary Test java.util.Scanner.hasNextByte() method for normal and boundary cases
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextByteNormal();
        testHasNextByteWithInvalidRadix();
    }

    private static void testHasNextByteNormal() {
        // Input string with a valid byte number
        String input = "127";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextByte();
        if (!result) {
            throw new RuntimeException("Expected hasNextByte() to return true for valid byte input");
        }
        // Consume the byte to check next token absence
        byte value = scanner.nextByte();
        if (value != 127) {
            throw new RuntimeException("Expected nextByte() to return 127, got " + value);
        }
        if (scanner.hasNextByte()) {
            throw new RuntimeException("Expected hasNextByte() to return false after consuming token");
        }
        scanner.close();
    }

    private static void testHasNextByteWithInvalidRadix() {
        // We cannot directly set defaultRadix as it is private,
        // but hasNextByte() calls hasNextByte(defaultRadix).
        // The only way to trigger IllegalArgumentException is if defaultRadix is out of [2..36].
        // Since we cannot set defaultRadix, we test that hasNextByte() does NOT throw.
        // This test documents that no exception is thrown in normal usage.

        String input = "10";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextByte();
            // result can be true or false depending on input
            // no exception expected
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unexpected IllegalArgumentException from hasNextByte() with normal radix", iae);
        } finally {
            scanner.close();
        }
    }
}
