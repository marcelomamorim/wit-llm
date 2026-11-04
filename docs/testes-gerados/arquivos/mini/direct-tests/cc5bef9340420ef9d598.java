/*
 * @test
 * @summary Test StringBuilder.insert(int offset, int i) method
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertAtStart();
        testInsertAtMiddle();
        testInsertAtEnd();
        testInsertNegativeOffset();
        testInsertOffsetGreaterThanLength();
    }

    private static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(0, 123);
        if (result != sb) throw new RuntimeException("insert should return this");
        if (!sb.toString().equals("123abc"))
            throw new RuntimeException("Expected '123abc' but got '" + sb.toString() + "'");
    }

    private static void testInsertAtMiddle() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, 456);
        if (!sb.toString().equals("a456bc"))
            throw new RuntimeException("Expected 'a456bc' but got '" + sb.toString() + "'");
    }

    private static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(sb.length(), 789);
        if (!sb.toString().equals("abc789"))
            throw new RuntimeException("Expected 'abc789' but got '" + sb.toString() + "'");
    }

    private static void testInsertNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, 10);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(sb.length() + 1, 10);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
