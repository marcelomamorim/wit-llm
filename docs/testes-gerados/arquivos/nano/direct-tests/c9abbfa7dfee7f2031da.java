/* @test    @summary Test insert method with double argument in AbstractStringBuilder    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithPositiveDouble();
        testInsertWithNegativeDouble();
        testInsertWithZeroDouble();
        testInsertWithLargeDouble();
    }

    static void testInsertWithPositiveDouble() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, 123.456);
        String result = sb.toString();
        if (!result.contains("123.456")) {
            throw new AssertionError("Expected to contain '123.456', but got: " + result);
        }
    }

    static void testInsertWithNegativeDouble() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, -789.01);
        String result = sb.toString();
        if (!result.contains("-789.01")) {
            throw new AssertionError("Expected to contain '-789.01', but got: " + result);
        }
    }

    static void testInsertWithZeroDouble() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, 0.0);
        String result = sb.toString();
        if (!result.contains("0.0")) {
            throw new AssertionError("Expected to contain '0.0', but got: " + result);
        }
    }

    static void testInsertWithLargeDouble() {
        AbstractStringBuilder sb = new AbstractStringBuilder(20) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        double largeDouble = 1.7976931348623157E308; // Max double
        sb.insert(0, largeDouble);
        String result = sb.toString();
        if (!result.contains("1.7976931348623157E308")) {
            throw new AssertionError("Expected to contain '1.7976931348623157E308', but got: " + result);
        }
    }
}
