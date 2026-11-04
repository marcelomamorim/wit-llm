/* @test    @summary Test subSequence for AbstractStringBuilder with valid and invalid ranges    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testValidSubSequence();
        testSubSequenceWithInvalidRange();
    }

    private static void testValidSubSequence() {
        try {
            // Create a concrete subclass instance for testing
            AbstractStringBuilder builder = new ConcreteStringBuilder("HelloWorld");
            CharSequence cs = builder.subSequence(0, 5);
            if (!"Hello".contentEquals(cs)) {
                throw new AssertionError("Expected 'Hello', got: " + cs);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid subSequence", e);
        }
    }

    private static void testSubSequenceWithInvalidRange() {
        try {
            AbstractStringBuilder builder = new ConcreteStringBuilder("HelloWorld");
            // Triggering StringIndexOutOfBoundsException due to invalid range
            builder.subSequence(-1, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative start", e);
        }
        try {
            AbstractStringBuilder builder = new ConcreteStringBuilder("HelloWorld");
            // Triggering StringIndexOutOfBoundsException due to end > length
            builder.subSequence(0, 20);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for end > length", e);
        }
        try {
            AbstractStringBuilder builder = new ConcreteStringBuilder("HelloWorld");
            // Triggering StringIndexOutOfBoundsException due to start > end
            builder.subSequence(7, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for start > end", e);
        }
    }

    // Concrete subclass for testing purposes
    static class ConcreteStringBuilder extends AbstractStringBuilder {
        private String str;

        ConcreteStringBuilder(String s) {
            super(s.length());
            this.str = s;
            this.count = s.length();
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return str.substring(start, end);
        }
    }
}
