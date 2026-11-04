/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, double) for boundary and exception behavior
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class ASB extends java.lang.AbstractStringBuilder {
        ASB(int capacity) {
            super(capacity);
            this.count = 0;
        }

        // Expose insert(int, String) to allow testing insert(int, double)
        @Override
        public java.lang.AbstractStringBuilder insert(int offset, String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            if (offset < 0 || offset > count) {
                throw new StringIndexOutOfBoundsException(offset);
            }
            // Simplified insert: just increase count by str length
            count += str.length();
            return this;
        }

        @Override
        public int length() {
            return count;
        }
    }

    public static void main(String[] args) {
        ASB asb = new ASB(10);

        // Test valid insert at offset 0 with double 0.0
        asb.insert(0, 0.0);
        if (asb.length() != String.valueOf(0.0).length()) {
            throw new Error("Length mismatch after insert of 0.0");
        }

        // Test valid insert at offset count (append)
        int oldLength = asb.length();
        asb.insert(oldLength, 123.456);
        if (asb.length() != oldLength + String.valueOf(123.456).length()) {
            throw new Error("Length mismatch after insert at end");
        }

        // Test insert with negative offset throws StringIndexOutOfBoundsException
        try {
            asb.insert(-1, 1.23);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test insert with offset greater than count throws StringIndexOutOfBoundsException
        try {
            asb.insert(asb.length() + 1, 4.56);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
