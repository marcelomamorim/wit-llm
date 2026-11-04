/*
 * @test
 * @summary Test java.lang.Integer.valueOf(String s, int radix) method
 * @run main IntegerWitupTest
 */
public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidDecimal();
        testValidHex();
        testValidBinary();
        testInvalidNumberFormat();
        testRadixOutOfRange();
        testNegativeNumber();
    }

    static void testValidDecimal() {
        Integer i = Integer.valueOf("123", 10);
        if (i.intValue() != 123) {
            throw new Error("Failed decimal parse: expected 123 but got " + i);
        }
    }

    static void testValidHex() {
        Integer i = Integer.valueOf("7b", 16);
        if (i.intValue() != 123) {
            throw new Error("Failed hex parse: expected 123 but got " + i);
        }
    }

    static void testValidBinary() {
        Integer i = Integer.valueOf("1111011", 2);
        if (i.intValue() != 123) {
            throw new Error("Failed binary parse: expected 123 but got " + i);
        }
    }

    static void testInvalidNumberFormat() {
        try {
            Integer.valueOf("xyz", 10);
            throw new Error("Expected NumberFormatException for input 'xyz'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testRadixOutOfRange() {
        // radix < Character.MIN_RADIX (2) or > Character.MAX_RADIX (36) should default to 10
        Integer i1 = Integer.valueOf("123", 1); // invalid radix, should parse as decimal
        if (i1.intValue() != 123) {
            throw new Error("Failed radix < MIN_RADIX fallback: expected 123 but got " + i1);
        }
        Integer i2 = Integer.valueOf("123", 37); // invalid radix, should parse as decimal
        if (i2.intValue() != 123) {
            throw new Error("Failed radix > MAX_RADIX fallback: expected 123 but got " + i2);
        }
    }

    static void testNegativeNumber() {
        Integer i = Integer.valueOf("-123", 10);
        if (i.intValue() != -123) {
            throw new Error("Failed negative number parse: expected -123 but got " + i);
        }
    }
}
