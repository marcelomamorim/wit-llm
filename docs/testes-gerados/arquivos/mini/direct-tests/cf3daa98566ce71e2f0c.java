/*
 * @test
 * @summary Test BigDecimal(char[] in, int offset, int len) constructor
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testEmptyInput();
        testOffsetAndLength();
        testInvalidOffsetLength();
    }

    private static void testValidConstruction() {
        char[] chars = "123.45".toCharArray();
        BigDecimal bd = new BigDecimal(chars, 0, chars.length);
        if (!"123.45".equals(bd.toString())) {
            throw new RuntimeException("Failed to construct BigDecimal from full char array");
        }
    }

    private static void testEmptyInput() {
        char[] chars = new char[0];
        try {
            new BigDecimal(chars, 0, 0);
            throw new RuntimeException("Expected NumberFormatException for empty input");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testOffsetAndLength() {
        char[] chars = "abc123.45xyz".toCharArray();
        BigDecimal bd = new BigDecimal(chars, 3, 6); // "123.45"
        if (!"123.45".equals(bd.toString())) {
            throw new RuntimeException("Failed to construct BigDecimal with offset and length");
        }
    }

    private static void testInvalidOffsetLength() {
        char[] chars = "12345".toCharArray();
        try {
            new BigDecimal(chars, -1, 3);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            new BigDecimal(chars, 1, 10);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for length too large");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
