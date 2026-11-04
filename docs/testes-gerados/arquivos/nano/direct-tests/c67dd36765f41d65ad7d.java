/* @test    @summary Test for Scanner.useRadix(int radix) method behavior and exception handling    @run main ScannerWitupTest */

import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testUseRadixValid();
        testUseRadixInvalidLow();
        testUseRadixInvalidHigh();
        testUseRadixSameValue();
    }

    private static void testUseRadixValid() {
        Scanner scanner = new Scanner("123");
        int validRadix = 10;
        Scanner result = scanner.useRadix(validRadix);
        assert result == scanner : "useRadix should return the same Scanner instance";
        System.out.println("testUseRadixValid passed");
    }

    private static void testUseRadixInvalidLow() {
        Scanner scanner = new Scanner("123");
        int invalidRadix = Character.MIN_RADIX - 1;
        try {
            scanner.useRadix(invalidRadix);
            assert false : "Expected IllegalArgumentException for radix below MIN_RADIX";
        } catch (IllegalArgumentException e) {
            System.out.println("testUseRadixInvalidLow passed");
        }
    }

    private static void testUseRadixInvalidHigh() {
        Scanner scanner = new Scanner("123");
        int invalidRadix = Character.MAX_RADIX + 1;
        try {
            scanner.useRadix(invalidRadix);
            assert false : "Expected IllegalArgumentException for radix above MAX_RADIX";
        } catch (IllegalArgumentException e) {
            System.out.println("testUseRadixInvalidHigh passed");
        }
    }

    private static void testUseRadixSameValue() {
        Scanner scanner = new Scanner("123");
        int currentRadix = scanner.useRadix(10).defaultRadix;
        Scanner result = scanner.useRadix(currentRadix);
        assert result == scanner : "useRadix should return the same Scanner instance when radix is unchanged";
        System.out.println("testUseRadixSameValue passed");
    }
}
