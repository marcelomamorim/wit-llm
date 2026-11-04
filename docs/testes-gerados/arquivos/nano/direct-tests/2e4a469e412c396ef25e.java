/* @test    @summary Test insert(int, int) method of AbstractStringBuilder for correct insertion behavior and boundary conditions    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    static void testInsertAtBeginning() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, 12345);
        assert sb.toString().equals("12345") : "Failed insert at beginning";
    }

    static void testInsertAtEnd() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, 6789);
        sb.insert(sb.length(), 9876);
        assert sb.toString().equals("67899876") : "Failed insert at end";
    }

    static void testInsertInMiddle() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.insert(0, 1111);
        sb.insert(2, 2222);
        assert sb.toString().equals("12221111") : "Failed insert in middle";
    }

    static void testInsertWithNegativeOffset() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        try {
            sb.insert(-1, 999);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertWithOffsetGreaterThanLength() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        try {
            sb.insert(1, 888);
            assert false : "Expected IndexOutOfBoundsException for offset greater than length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static class ConcreteStringBuilder extends AbstractStringBuilder {
        public ConcreteStringBuilder() {
            super();
        }
        @Override
        public String toString() {
            return new String(value, 0, count);
        }
    }
}
