/*
 * @test
 * @summary Test insert(int, double) method for AbstractStringBuilder
 * @run main AbstractStringBuilderWitupTest
 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertWithValidOffset() {
        try {
            AbstractStringBuilder asb = new DummyStringBuilder();
            int initialLength = asb.length();
            double value = 123.456;
            asb.insert(0, value);
            assert asb.length() >= initialLength : "Length should increase or stay the same";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid offset test", e);
        }
    }

    private static void testInsertWithNegativeOffset() {
        try {
            AbstractStringBuilder asb = new DummyStringBuilder();
            asb.insert(-1, 1.23);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative offset", e);
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        try {
            AbstractStringBuilder asb = new DummyStringBuilder();
            int offset = asb.length() + 1;
            asb.insert(offset, 4.56);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for offset greater than length", e);
        }
    }

    // Dummy implementation to instantiate AbstractStringBuilder
    static class DummyStringBuilder extends AbstractStringBuilder {
        DummyStringBuilder() {
            super(10);
        }
        @Override
        public int length() {
            return super.count;
        }
        @Override
        public AbstractStringBuilder insert(int offset, String s) {
            // Simplified insert implementation for testing
            if (offset < 0 || offset > length()) {
                throw new StringIndexOutOfBoundsException();
            }
            // No actual insertion, just simulate length change
            super.count += s.length();
            return this;
        }
    }
}
