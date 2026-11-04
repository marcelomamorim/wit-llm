/*
 * @test
 * @summary Witup-style tests for StringBuffer.append(char[], int, int)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendSubrange();
        testAppendZeroLength();
        testAppendReturnsSameInstance();
        testAppendInvalidatesToStringCache();
        testAppendFullArray();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendSubrange() {
        char[] src = {'a', 'b', 'c', 'd'};
        StringBuffer sb = new StringBuffer("x");
        sb.append(src, 1, 2);
        check(sb.toString().equals("xbc"),
              "expected xbc but was " + sb.toString());
    }

    static void testAppendZeroLength() {
        char[] src = {'a', 'b', 'c', 'd'};
        StringBuffer sb = new StringBuffer("x");
        sb.append(src, 0, 0);
        check(sb.toString().equals("x"),
              "expected x but was " + sb.toString());
        check(sb.length() == 1, "expected length 1 but was " + sb.length());
    }

    static void testAppendReturnsSameInstance() {
        char[] src = {'a', 'b'};
        StringBuffer sb = new StringBuffer();
        StringBuffer result = sb.append(src, 0, 1);
        check(result == sb, "append(char[],int,int) must return this");
        check(sb.toString().equals("a"),
              "expected a but was " + sb.toString());
    }

    static void testAppendInvalidatesToStringCache() {
        char[] src = {'x', 'y'};
        StringBuffer sb = new StringBuffer("ab");
        String before = sb.toString();
        check(before.equals("ab"), "expected ab before append");
        sb.append(src, 0, 2);
        check(sb.toString().equals("abxy"),
              "expected abxy after append but was " + sb.toString());
    }

    static void testAppendFullArray() {
        char[] src = {'a', 'b', 'c', 'd'};
        StringBuffer sb = new StringBuffer();
        sb.append(src, 0, src.length);
        check(sb.toString().equals("abcd"),
              "expected abcd but was " + sb.toString());
        check(sb.length() == 4, "expected length 4 but was " + sb.length());
    }
}
