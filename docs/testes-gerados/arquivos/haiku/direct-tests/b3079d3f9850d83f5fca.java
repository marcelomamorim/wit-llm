/*
 * @test
 * @summary Behavioral tests for Integer.valueOf(String s, int radix)
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicRadixParsing() {
        check(Integer.valueOf("ff", 16).intValue() == 255, "ff radix 16 should be 255");
        check(Integer.valueOf("-7f", 16).intValue() == -127, "-7f radix 16 should be -127");
        check(Integer.valueOf("0", 2).intValue() == 0, "0 radix 2 should be 0");
        check(Integer.valueOf("101", 2).intValue() == 5, "101 radix 2 should be 5");
        check(Integer.valueOf("z", 36).intValue() == 35, "z radix 36 should be 35");
        check(Integer.valueOf("+10", 10).intValue() == 10, "+10 radix 10 should be 10");
    }

    static void testResultMatchesValueOfInt() {
        Integer a = Integer.valueOf("5", 10);
        check(a.equals(Integer.valueOf(5)), "valueOf(\"5\",10) should equal valueOf(5)");
        Integer b = Integer.valueOf("-128", 10);
        check(b == Integer.valueOf(-128), "cached value -128 should be the same instance");
    }

    static void testBoundaries() {
        check(Integer.valueOf("7fffffff", 16).intValue() == Integer.MAX_VALUE, "7fffffff radix 16 should be MAX_VALUE");
        check(Integer.valueOf("-80000000", 16).intValue() == Integer.MIN_VALUE, "-80000000 radix 16 should be MIN_VALUE");
    }

    static void testInvalidDigitThrows() {
        boolean thrown = false;
        try {
            Integer.valueOf("g", 16);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        check(thrown, "'g' in radix 16 should throw NumberFormatException");
    }

    public static void main(String[] args) {
        testBasicRadixParsing();
        testResultMatchesValueOfInt();
        testBoundaries();
        testInvalidDigitThrows();
    }
}
