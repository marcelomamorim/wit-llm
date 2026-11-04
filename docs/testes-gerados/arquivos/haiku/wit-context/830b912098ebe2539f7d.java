/*
 * @test
 * @summary Verifies StringBuilder.insert(int, long) inserts the decimal form of the long at valid offsets and rejects invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertMinLong();
        testReturnsSameInstance();
        testNegativeOffsetThrows();
        testOffsetBeyondLengthThrows();
        testBuilderUnchangedAfterFailure();
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, 123L);
        check("123ab", sb.toString());
    }

    static void testInsertInMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, 123L);
        check("a123b", sb.toString());
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, 7L);
        check("ab7", sb.toString());
    }

    static void testInsertMinLong() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, Long.MIN_VALUE);
        check("-9223372036854775808", sb.toString());
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder result = sb.insert(1, 5L);
        if (result != sb) {
            throw new AssertionError("insert(int, long) must return this");
        }
        check("x5", sb.toString());
    }

    static void testNegativeOffsetThrows() {
        expectOffsetFailure(-1);
    }

    static void testOffsetBeyondLengthThrows() {
        expectOffsetFailure(3);
    }

    static void testBuilderUnchangedAfterFailure() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(5, 1L);
            throw new AssertionError("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        check("ab", sb.toString());
    }

    static void expectOffsetFailure(int offset) {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(offset, 1L);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("no StringIndexOutOfBoundsException for offset " + offset);
    }
}
