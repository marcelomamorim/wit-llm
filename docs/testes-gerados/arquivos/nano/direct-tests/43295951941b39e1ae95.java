/* @test    @summary Test StringBuilder.insert(int, double) method for various double values.    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertDoublePositive();
        testInsertDoubleNegative();
        testInsertDoubleZero();
        testInsertDoubleLarge();
        testInsertDoubleSmall();
    }

    static void testInsertDoublePositive() {
        StringBuilder sb = new StringBuilder("abc");
        double value = 123.456;
        sb.insert(1, value);
        String result = sb.toString();
        if (!result.equals("a123.456bc")) {
            throw new AssertionError("Expected 'a123.456bc' but got: " + result);
        }
    }

    static void testInsertDoubleNegative() {
        StringBuilder sb = new StringBuilder("xyz");
        double value = -789.1011;
        sb.insert(2, value);
        String result = sb.toString();
        if (!result.equals("xy-789.1011z")) {
            throw new AssertionError("Expected 'xy-789.1011z' but got: " + result);
        }
    }

    static void testInsertDoubleZero() {
        StringBuilder sb = new StringBuilder("test");
        double value = 0.0;
        sb.insert(4, value);
        String result = sb.toString();
        if (!result.equals("test0.0")) {
            throw new AssertionError("Expected 'test0.0' but got: " + result);
        }
    }

    static void testInsertDoubleLarge() {
        StringBuilder sb = new StringBuilder("large");
        double value = 1.7976931348623157E308; // Max double
        sb.insert(5, value);
        String result = sb.toString();
        if (!result.startsWith("large")) {
            throw new AssertionError("Result does not start with 'large': " + result);
        }
    }

    static void testInsertDoubleSmall() {
        StringBuilder sb = new StringBuilder("small");
        double value = 4.9E-324; // Min positive subnormal double
        sb.insert(5, value);
        String result = sb.toString();
        if (!result.startsWith("small")) {
            throw new AssertionError("Result does not start with 'small': " + result);
        }
    }
}
