/* @test    @summary Test insert(int, boolean) for AbstractStringBuilder with boundary and invalid cases    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertWithValidOffset() {
        try {
            AbstractStringBuilder asb = new AbstractStringBuilder(10) {
                @Override
                public int length() { return 0; }
                @Override
                public CharSequence subSequence(int start, int end) { return null; }
                @Override
                public String toString() { return ""; }
            };
            int offset = 0;
            boolean value = true;
            AbstractStringBuilder result = asb.insert(offset, value);
            if (result == null) {
                throw new AssertionError("Result should not be null for valid offset");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testInsertWithValidOffset", e);
        }
    }

    private static void testInsertWithNegativeOffset() {
        try {
            AbstractStringBuilder asb = new AbstractStringBuilder(10) {
                @Override
                public int length() { return 0; }
                @Override
                public CharSequence subSequence(int start, int end) { return null; }
                @Override
                public String toString() { return ""; }
            };
            int offset = -1;
            boolean value = false;
            asb.insert(offset, value);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testInsertWithNegativeOffset", e);
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        try {
            AbstractStringBuilder asb = new AbstractStringBuilder(10) {
                @Override
                public int length() { return 0; }
                @Override
                public CharSequence subSequence(int start, int end) { return null; }
                @Override
                public String toString() { return ""; }
            };
            int offset = 1;
            boolean value = true;
            asb.insert(offset, value);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testInsertWithOffsetGreaterThanLength", e);
        }
    }
}
