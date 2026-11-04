/* @test    @summary Test for Scanner.hasNextByte(int radix) method    @run main ScannerWitupTest */
/* @modules java.base */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextByteWithValidByte();
        testHasNextByteWithInvalidByte();
        testHasNextByteWithDifferentRadix();
    }

    private static void testHasNextByteWithValidByte() {
        String input = "127"; // Max value for byte
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextByte(10);
        if (!result) {
            throw new AssertionError("Expected hasNextByte to return true for valid byte value");
        }
        scanner.close();
    }

    private static void testHasNextByteWithInvalidByte() {
        String input = "128"; // Out of byte range for base 10
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextByte(10);
        if (result) {
            throw new AssertionError("Expected hasNextByte to return false for invalid byte value");
        }
        scanner.close();
    }

    private static void testHasNextByteWithDifferentRadix() {
        String input = "7F"; // Hexadecimal for 127
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextByte(16);
        if (!result) {
            throw new AssertionError("Expected hasNextByte to return true for valid hex byte value");
        }
        scanner.close();
    }
}
