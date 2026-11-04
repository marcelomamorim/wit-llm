/*
 * @test
 * @summary Test BigDecimal(char[] in, int offset, int len) constructor for boundary and error cases
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testOffsetLenOutOfBounds();
    }

    private static void testValidConstruction() {
        char[] digits = new char[] {'1', '2', '3', '4', '5'};
        // Normal construction with offset 0 and len 5
        BigDecimal bd1 = new BigDecimal(digits, 0, 5);
        if (!"12345".equals(bd1.toString())) {
            throw new AssertionError("Expected 12345 but got " + bd1.toString());
        }
        // Construction with offset 1 and len 3 -> "234"
        BigDecimal bd2 = new BigDecimal(digits, 1, 3);
        if (!"234".equals(bd2.toString())) {
            throw new AssertionError("Expected 234 but got " + bd2.toString());
        }
        // Construction with offset 4 and len 1 -> "5"
        BigDecimal bd3 = new BigDecimal(digits, 4, 1);
        if (!"5".equals(bd3.toString())) {
            throw new AssertionError("Expected 5 but got " + bd3.toString());
        }
    }

    private static void testOffsetLenOutOfBounds() {
        char[] digits = new char[] {'1', '2', '3'};
        // offset + len > in.length should throw NumberFormatException
        try {
            new BigDecimal(digits, 1, 3);
            throw new AssertionError("Expected NumberFormatException for offset+len > in.length");
        } catch (NumberFormatException e) {
            // expected
        }
        // offset < 0 should throw NumberFormatException
        try {
            new BigDecimal(digits, -1, 1);
            throw new AssertionError("Expected NumberFormatException for offset < 0");
        } catch (NumberFormatException e) {
            // expected
        }
        // len < 0 should throw NumberFormatException (not in WIT but logically invalid)
        try {
            new BigDecimal(digits, 0, -1);
            throw new AssertionError("Expected NumberFormatException for len < 0");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
