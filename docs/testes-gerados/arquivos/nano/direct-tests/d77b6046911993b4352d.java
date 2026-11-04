/* @test    @summary Test insert method with float argument in AbstractStringBuilder    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithPositiveFloat();
        testInsertWithNegativeFloat();
        testInsertWithZeroFloat();
        testInsertWithLargeFloat();
    }

    static void testInsertWithPositiveFloat() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, 3.14f);
        String result = sb.toString();
        if (!result.contains("3.14")) {
            throw new AssertionError("Expected '3.14' in result, but got: " + result);
        }
    }

    static void testInsertWithNegativeFloat() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, -2.718f);
        String result = sb.toString();
        if (!result.contains("-2.718")) {
            throw new AssertionError("Expected '-2.718' in result, but got: " + result);
        }
    }

    static void testInsertWithZeroFloat() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        sb.insert(0, 0.0f);
        String result = sb.toString();
        if (!result.contains("0.0")) {
            throw new AssertionError("Expected '0.0' in result, but got: " + result);
        }
    }

    static void testInsertWithLargeFloat() {
        AbstractStringBuilder sb = new AbstractStringBuilder(20) {
            @Override
            public String toString() {
                return new String(value, 0, count);
            }
        };
        float largeFloat = 1.23e10f;
        sb.insert(0, largeFloat);
        String result = sb.toString();
        if (!result.contains("1.23E10") && !result.contains("1.23e10")) {
            throw new AssertionError("Expected '1.23E10' or '1.23e10' in result, but got: " + result);
        }
    }
}
