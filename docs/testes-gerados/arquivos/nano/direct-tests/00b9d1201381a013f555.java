/* @test    @summary Test StringBuffer.insert(int, boolean) method for correctness and regressions    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertBooleanAtStart();
        testInsertBooleanAtMiddle();
        testInsertBooleanAtEnd();
        testInsertBooleanWithEmptyBuffer();
        testInsertBooleanWithNullOffset();
    }

    static void testInsertBooleanAtStart() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(0, true);
        assert sb.toString().equals("trueHello") : "Failed at insert boolean at start";
    }

    static void testInsertBooleanAtMiddle() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(2, false);
        assert sb.toString().equals("Hefalsello") : "Failed at insert boolean at middle";
    }

    static void testInsertBooleanAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(sb.length(), true);
        assert sb.toString().equals("Hellotrue") : "Failed at insert boolean at end";
    }

    static void testInsertBooleanWithEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, false);
        assert sb.toString().equals("false") : "Failed at insert boolean into empty buffer";
    }

    static void testInsertBooleanWithNullOffset() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.insert(-1, true);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.insert(100, false);
            assert false : "Expected IndexOutOfBoundsException for offset beyond length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
