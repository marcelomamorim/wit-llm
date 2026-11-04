/* @test    @summary Test insert method with boolean argument in AbstractStringBuilder    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertBooleanAtStart();
        testInsertBooleanAtMiddle();
        testInsertBooleanAtEnd();
        testInsertBooleanWithNegativeOffset();
        testInsertBooleanWithOffsetBeyondLength();
    }

    static void testInsertBooleanAtStart() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, true);
        assert sb.toString().equals("true") : "Failed at insert boolean at start";
    }

    static void testInsertBooleanAtMiddle() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, false);
        sb.insert(4, true);
        assert sb.toString().equals("falsetrue") : "Failed at insert boolean at middle";
    }

    static void testInsertBooleanAtEnd() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, false);
        sb.insert(sb.length(), true);
        assert sb.toString().equals("falsetrue") : "Failed at insert boolean at end";
    }

    static void testInsertBooleanWithNegativeOffset() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        try {
            sb.insert(-1, true);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertBooleanWithOffsetBeyondLength() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        try {
            sb.insert(1, true);
            assert false : "Expected IndexOutOfBoundsException for offset beyond length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static class ConcreteStringBuilder extends AbstractStringBuilder {
        public ConcreteStringBuilder() {
            super(10);
        }
        @Override
        public String toString() {
            return new String(value, 0, count);
        }
    }
}
