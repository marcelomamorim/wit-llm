/* @test    @summary Test insert method of AbstractStringBuilder for valid and invalid offsets    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertOffsetNegative();
        testInsertOffsetGreaterThanCount();
    }

    private static void testInsertValidOffset() {
        try {
            AbstractStringBuilder sb = new ConcreteStringBuilder(10);
            char[] chars = {'a', 'b', 'c'};
            sb.insert(0, chars);
            sb.insert(1, new char[]{'x'});
            if (sb.length() != 4) {
                throw new AssertionError("Unexpected length after valid inserts");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid insert", e);
        }
    }

    private static void testInsertOffsetNegative() {
        try {
            AbstractStringBuilder sb = new ConcreteStringBuilder(10);
            char[] chars = {'a', 'b'};
            sb.insert(-1, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative offset", e);
        }
    }

    private static void testInsertOffsetGreaterThanCount() {
        try {
            AbstractStringBuilder sb = new ConcreteStringBuilder(10);
            char[] chars = {'a', 'b'};
            sb.insert(1, chars); // count is 0, offset 1 is invalid
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for offset > count", e);
        }
    }

    // Concrete subclass for testing since AbstractStringBuilder is abstract
    static class ConcreteStringBuilder extends AbstractStringBuilder {
        public ConcreteStringBuilder(int capacity) {
            super(capacity);
        }
        @Override
        public String toString() {
            return new String(value, 0, count);
        }
    }
}
