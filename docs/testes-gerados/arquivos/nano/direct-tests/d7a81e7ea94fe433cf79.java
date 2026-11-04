/* @test    @summary Test StringBuilder.insert(int, boolean) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertBooleanAtStart();
        testInsertBooleanAtMiddle();
        testInsertBooleanAtEnd();
        testInsertBooleanWithNegativeOffset();
        testInsertBooleanWithOffsetBeyondLength();
    }

    private static void testInsertBooleanAtStart() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(0, true);
        assert "trueHello".equals(sb.toString()) : "Failed at start insertion";
    }

    private static void testInsertBooleanAtMiddle() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(2, false);
        assert "Hefalsello".equals(sb.toString()) : "Failed at middle insertion";
    }

    private static void testInsertBooleanAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(sb.length(), true);
        assert "Hellotrue".equals(sb.toString()) : "Failed at end insertion";
    }

    private static void testInsertBooleanWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(-1, true);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertBooleanWithOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(10, false);
            assert false : "Expected IndexOutOfBoundsException for offset beyond length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
