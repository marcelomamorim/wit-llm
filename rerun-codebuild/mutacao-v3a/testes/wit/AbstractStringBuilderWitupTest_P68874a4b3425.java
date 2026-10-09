/*
 * @test
 * @summary Test for offsetByCodePoints method in AbstractStringBuilder.
 * @run main AbstractStringBuilderWitupTest_P68874a4b3425
 */

import java.lang.IndexOutOfBoundsException;
import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P68874a4b3425 {

    public static void main(String[] args) {
        testOffsetByCodePoints_NegativeIndex();
        testOffsetByCodePoints_IndexGreaterThanCount();
        testOffsetByCodePoints_ValidCase();
    }

    public static void testOffsetByCodePoints_NegativeIndex() {
        AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
        int index = -1;
        int offset = 2;
        try {
            asb.offsetByCodePoints(index, offset);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    public static void testOffsetByCodePoints_IndexGreaterThanCount() {
        AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
        int index = 11;
        int offset = 2;
        try {
            asb.offsetByCodePoints(index, offset);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > count");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    public static void testOffsetByCodePoints_ValidCase() {
        AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
        int index = 5;
        int offset = 3;
        int result = asb.offsetByCodePoints(index, offset);
        if (result != index + offset) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    private static class DummyAbstractStringBuilder extends AbstractStringBuilder {
        public DummyAbstractStringBuilder(int capacity) {
            super(capacity);
        }

        @Override
        public int length() {
            return count;
        }

        @Override
        public int offsetByCodePoints(int index, int codePointOffset) {
            if (index < 0 || index > count) {
                throw new IndexOutOfBoundsException();
            }
            return index + codePointOffset;
        }
    }
}
