/* @test    @summary Test StringBuilder.insert(int, char) method for correctness and boundary cases    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertWithEmptyBuilder();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    static void testInsertAtBeginning() {
        StringBuilder sb = new StringBuilder("world");
        sb.insert(0, 'H');
        assert "Hworld".equals(sb.toString()) : "Failed insert at beginning";
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(sb.length(), '!');
        assert "Hello!".equals(sb.toString()) : "Failed insert at end";
    }

    static void testInsertInMiddle() {
        StringBuilder sb = new StringBuilder("Helo");
        sb.insert(2, 'l');
        assert "Hello".equals(sb.toString()) : "Failed insert in middle";
    }

    static void testInsertWithEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 'A');
        assert "A".equals(sb.toString()) : "Failed insert into empty builder";
    }

    static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 'X');
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertWithOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(10, 'Y');
            assert false : "Expected IndexOutOfBoundsException for offset greater than length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
