/*
 * @test
 * @summary Witup-generated checks for StringBuilder.append(char[], int, int)
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testEmptyAtEnd();
        testNegativeOffset();
        testNegativeLength();
        testRangeBeyondArray();
        testOverflowingRange();
        testReturnsSameBuilder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIndexOutOfBounds(char[] str, int offset, int len) {
        try {
            new StringBuilder().append(str, offset, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for offset=" + offset + " len=" + len);
    }

    static void testValidRange() {
        char[] data = {'a', 'b', 'c'};
        String result = new StringBuilder("x").append(data, 1, 2).toString();
        check(result.equals("xbc"), "unexpected result: " + result);
    }

    static void testEmptyAtEnd() {
        char[] data = {'a', 'b', 'c'};
        String result = new StringBuilder("x").append(data, 3, 0).toString();
        check(result.equals("x"), "unexpected result: " + result);
    }

    static void testNegativeOffset() {
        expectIndexOutOfBounds(new char[] {'a', 'b', 'c'}, -1, 1);
    }

    static void testNegativeLength() {
        expectIndexOutOfBounds(new char[] {'a', 'b', 'c'}, 0, -1);
    }

    static void testRangeBeyondArray() {
        expectIndexOutOfBounds(new char[] {'a', 'b', 'c'}, 2, 2);
    }

    static void testOverflowingRange() {
        expectIndexOutOfBounds(new char[] {'a', 'b', 'c'}, Integer.MAX_VALUE, 1);
    }

    static void testReturnsSameBuilder() {
        StringBuilder sb = new StringBuilder();
        StringBuilder returned = sb.append(new char[] {'a', 'b'}, 0, 2);
        check(returned == sb, "append(char[], int, int) must return this");
        check(sb.toString().equals("ab"), "unexpected content: " + sb);
    }
}
