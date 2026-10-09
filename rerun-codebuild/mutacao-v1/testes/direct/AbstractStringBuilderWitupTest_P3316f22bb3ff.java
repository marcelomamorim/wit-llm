/* @test    @summary Test offsetByCodePoints method of AbstractStringBuilder for valid and invalid inputs    @run main AbstractStringBuilderWitupTest_P3316f22bb3ff */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P3316f22bb3ff {

    public static void main(String[] args) {
        testValidOffset();
        testNegativeIndex();
        testIndexGreaterThanCount();
    }

    private static void testValidOffset() {
        try {
            // Create a dummy subclass to instantiate AbstractStringBuilder
            AbstractStringBuilder builder = new DummyAbstractStringBuilder(10);
            // Initialize with some characters
            builder.value = new byte[] {65, 66, 67, 68, 69, 70, 71, 72, 73, 74};
            builder.count = 10;
            int index = 2; // 'C'
            int codePointOffset = 3; // move forward 3 code points
            int result = builder.offsetByCodePoints(index, codePointOffset);
            // Expecting index + 3
            if (result != index + 3) {
                throw new AssertionError("Expected " + (index + 3) + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidOffset: " + e);
        }
    }

    private static void testNegativeIndex() {
        try {
            AbstractStringBuilder builder = new DummyAbstractStringBuilder(10);
            builder.value = new byte[] {65, 66, 67};
            builder.count = 3;
            builder.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNegativeIndex: " + e);
        }
    }

    private static void testIndexGreaterThanCount() {
        try {
            AbstractStringBuilder builder = new DummyAbstractStringBuilder(10);
            builder.value = new byte[] {65, 66, 67};
            builder.count = 3;
            builder.offsetByCodePoints(4, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > count");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testIndexGreaterThanCount: " + e);
        }
    }

    // Dummy subclass to instantiate AbstractStringBuilder
    static class DummyAbstractStringBuilder extends AbstractStringBuilder {
        DummyAbstractStringBuilder(int capacity) {
            super(capacity);
        }
        @Override
        public int length() {
            return count;
        }
        @Override
        public CharSequence subSequence(int start, int end) {
            return null;
        }
        @Override
        public String toString() {
            return new String(value, 0, count);
        }
    }
}
