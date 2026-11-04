/*
 * @test
 * @summary Witup-style tests for Scanner.hasNextShort()
 * @run main ScannerWitupTest
 */
import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testDefaultRadixBasicValues();
        testShortRange();
        testNonNumericTokens();
        testEmptyInput();
        testCustomRadixAfterSetRadix();
        testInvalidRadixRejectedAndStateKept();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Scanner sc(String s) {
        return new Scanner(new StringReader(s));
    }

    static void testDefaultRadixBasicValues() {
        Scanner s = sc("123");
        check(s.hasNextShort(), "123 should be a short token in default radix");
        check("123".equals(s.next()), "hasNextShort must not consume the token");

        check(sc("-5").hasNextShort(), "-5 should be a short token");
        check(sc("0").hasNextShort(), "0 should be a short token");
    }

    static void testShortRange() {
        check(sc("32767").hasNextShort(), "Short.MAX_VALUE should be accepted");
        check(sc("-32768").hasNextShort(), "Short.MIN_VALUE should be accepted");
        check(!sc("32768").hasNextShort(), "value above Short.MAX_VALUE should be rejected");
        check(!sc("-32769").hasNextShort(), "value below Short.MIN_VALUE should be rejected");
    }

    static void testNonNumericTokens() {
        check(!sc("abc").hasNextShort(), "alphabetic token should not be a short");
        check(!sc("1.5").hasNextShort(), "decimal token should not be a short");
    }

    static void testEmptyInput() {
        check(!sc("").hasNextShort(), "empty input should return false");
        check(!sc("   ").hasNextShort(), "whitespace-only input should return false");
    }

    static void testCustomRadixAfterSetRadix() {
        Scanner s = sc("7fff");
        s.setRadix(16);
        check(s.hasNextShort(), "7fff should be a short in radix 16");

        s = sc("ff");
        s.setRadix(16);
        check(s.hasNextShort(), "ff should be a short in radix 16");

        s = sc("zz");
        s.setRadix(16);
        check(!s.hasNextShort(), "zz should not be a short in radix 16");

        s = sc("8000");
        s.setRadix(16);
        check(!s.hasNextShort(), "8000 exceeds Short.MAX_VALUE in radix 16");
    }

    static void testInvalidRadixRejectedAndStateKept() {
        Scanner s = sc("12");
        boolean thrown = false;
        try {
            s.setRadix(37);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "setRadix(37) should throw IllegalArgumentException");
        check(s.hasNextShort(), "radix should remain valid after rejected setRadix");

        thrown = false;
        try {
            s.setRadix(1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "setRadix(1) should throw IllegalArgumentException");
        check(s.hasNextShort(), "radix should remain valid after rejected setRadix(1)");
    }
}
