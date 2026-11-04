/*
 * @test
 * @summary Verifies Scanner.useRadix argument validation, identity return and effect on nextInt parsing
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testRadixBelowMinThrows();
        testRadixAboveMaxThrows();
        testValidRadixReturnsSameInstance();
        testDefaultRadixReturnsSameInstance();
        testHexRadixAffectsNextInt();
        testBinaryRadixAffectsNextInt();
        testDecimalAfterHexRadixChange();
    }

    static void expectIllegalArgument(int radix) {
        Scanner s = new Scanner(new StringReader("1"));
        try {
            s.useRadix(radix);
            throw new RuntimeException("expected IllegalArgumentException for radix " + radix);
        } catch (IllegalArgumentException e) {
            String expected = "radix:" + radix;
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testRadixBelowMinThrows() {
        expectIllegalArgument(1);
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
    }

    static void testRadixAboveMaxThrows() {
        expectIllegalArgument(37);
        expectIllegalArgument(Integer.MAX_VALUE);
    }

    static void testValidRadixReturnsSameInstance() {
        Scanner s = new Scanner(new StringReader("ff"));
        Scanner r = s.useRadix(16);
        if (r != s) {
            throw new RuntimeException("useRadix must return this scanner");
        }
    }

    static void testDefaultRadixReturnsSameInstance() {
        Scanner s = new Scanner(new StringReader("12"));
        Scanner r = s.useRadix(10);
        if (r != s) {
            throw new RuntimeException("useRadix(10) must return this scanner");
        }
        if (s.nextInt() != 12) {
            throw new RuntimeException("default radix parsing changed");
        }
    }

    static void testHexRadixAffectsNextInt() {
        Scanner s = new Scanner(new StringReader("ff"));
        s.useRadix(16);
        int v = s.nextInt();
        if (v != 255) {
            throw new RuntimeException("expected 255 but got " + v);
        }
    }

    static void testBinaryRadixAffectsNextInt() {
        Scanner s = new Scanner(new StringReader("101"));
        s.useRadix(2);
        int v = s.nextInt();
        if (v != 5) {
            throw new RuntimeException("expected 5 but got " + v);
        }
    }

    static void testDecimalAfterHexRadixChange() {
        Scanner s = new Scanner(new StringReader("10"));
        s.useRadix(16);
        int v = s.nextInt();
        if (v != 16) {
            throw new RuntimeException("expected 16 but got " + v);
        }
    }
}
