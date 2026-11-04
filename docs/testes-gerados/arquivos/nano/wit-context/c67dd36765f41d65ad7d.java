/*
 * @test
 * @summary Test for Scanner.useRadix(int radix) method.
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testUseRadixValid();
        testUseRadixBelowMin();
        testUseRadixAboveMax();
        testUseRadixSameAsDefault();
    }

    private static void testUseRadixValid() {
        Scanner scanner = new Scanner("1234");
        int validRadix = 10;
        Scanner result = scanner.useRadix(validRadix);
        assert result == scanner : "useRadix should return the same Scanner instance";
        System.out.println("testUseRadixValid passed");
    }

    private static void testUseRadixBelowMin() {
        Scanner scanner = new Scanner("1234");
        int invalidRadix = Character.MIN_RADIX - 1;
        try {
            scanner.useRadix(invalidRadix);
            assert false : "Expected IllegalArgumentException for radix below MIN_RADIX";
        } catch (IllegalArgumentException e) {
            System.out.println("testUseRadixBelowMin passed");
        }
    }

    private static void testUseRadixAboveMax() {
        Scanner scanner = new Scanner("1234");
        int invalidRadix = Character.MAX_RADIX + 1;
        try {
            scanner.useRadix(invalidRadix);
            assert false : "Expected IllegalArgumentException for radix above MAX_RADIX";
        } catch (IllegalArgumentException e) {
            System.out.println("testUseRadixAboveMax passed");
        }
    }

    private static void testUseRadixSameAsDefault() {
        Scanner scanner = new Scanner("1234");
        int defaultRadix = 10;
        scanner.useRadix(defaultRadix);
        Scanner result = scanner.useRadix(defaultRadix);
        assert result == scanner : "useRadix with same radix should return the same Scanner instance";
        System.out.println("testUseRadixSameAsDefault passed");
    }
}
