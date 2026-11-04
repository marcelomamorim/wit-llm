/* @test    @summary Test StringBuilder.insert(int, int) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithPositiveIndex();
        testInsertWithZeroIndex();
        testInsertWithIndexEqualToLength();
        testInsertWithNegativeIndex();
        testInsertWithIndexGreaterThanLength();
        testInsertWithIndexLessThanZero();
    }

    private static void testInsertWithPositiveIndex() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, 123);
        String result = sb.toString();
        if (!"a123bc".equals(result)) {
            throw new AssertionError("Expected 'a123bc' but got: " + result);
        }
    }

    private static void testInsertWithZeroIndex() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.insert(0, 789);
        String result = sb.toString();
        if (!"789xyz".equals(result)) {
            throw new AssertionError("Expected '789xyz' but got: " + result);
        }
    }

    private static void testInsertWithIndexEqualToLength() {
        StringBuilder sb = new StringBuilder("hello");
        sb.insert(sb.length(), 456);
        String result = sb.toString();
        if (!"hello456".equals(result)) {
            throw new AssertionError("Expected 'hello456' but got: " + result);
        }
    }

    private static void testInsertWithNegativeIndex() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 999);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithIndexGreaterThanLength() {
        StringBuilder sb = new StringBuilder("data");
        try {
            sb.insert(10, 111);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithIndexLessThanZero() {
        StringBuilder sb = new StringBuilder("info");
        try {
            sb.insert(-5, 222);
            throw new AssertionError("Expected IndexOutOfBoundsException for index less than zero");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
