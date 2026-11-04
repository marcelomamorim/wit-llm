/*
 * @test
 * @summary Checks Scanner.hasNextByte(int radix) for invalid radix handling and for observable byte range and radix behavior
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testInvalidRadixThrows();
        testValidByteTokensInRadix10();
        testOutOfRangeAndNonNumericReturnFalse();
        testRadix16And36();
        testEmptyInputReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Scanner scannerFor(String input) {
        return new Scanner(new StringReader(input));
    }

    static void testInvalidRadixThrows() {
        int[] badRadixes = {1, 0, -1, 37, 100};
        for (int radix : badRadixes) {
            Scanner sc = scannerFor("12");
            boolean thrown = false;
            try {
                sc.hasNextByte(radix);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "Expected IllegalArgumentException for radix " + radix);
        }
    }

    static void testValidByteTokensInRadix10() {
        check(scannerFor("12").hasNextByte(10), "12 should be a valid byte in radix 10");
        check(scannerFor("-128").hasNextByte(10), "-128 should be a valid byte in radix 10");
        check(scannerFor("127").hasNextByte(10), "127 should be a valid byte in radix 10");
    }

    static void testOutOfRangeAndNonNumericReturnFalse() {
        check(!scannerFor("300").hasNextByte(10), "300 is out of byte range and should return false");
        check(!scannerFor("abc").hasNextByte(10), "abc is not a number in radix 10 and should return false");
    }

    static void testRadix16And36() {
        check(scannerFor("7f").hasNextByte(16), "7f should be a valid byte in radix 16");
        check(scannerFor("z").hasNextByte(36), "z should be a valid byte in radix 36");
    }

    static void testEmptyInputReturnsFalse() {
        check(!scannerFor("").hasNextByte(10), "Empty input should return false");
    }
}
