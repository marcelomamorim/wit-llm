/*
 * @test
 * @summary Witup-derived checks for StringBuffer.append(char[], int, int)
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidAppend();
        testZeroLengthAtEnd();
        testReturnsThis();
        testToStringCacheInvalidated();
        expectIOOBE(-1, 1);
        expectIOOBE(0, -1);
        expectIOOBE(2, 2);
        expectIOOBE(4, 0);
    }

    static void testValidAppend() {
        StringBuffer sb = new StringBuffer("x");
        sb.append(new char[]{'a', 'b', 'c'}, 1, 2);
        check(sb.toString().equals("xbc"), "expected xbc but was " + sb);
    }

    static void testZeroLengthAtEnd() {
        StringBuffer sb = new StringBuffer("base");
        sb.append(new char[]{'a', 'b', 'c'}, 3, 0);
        check(sb.toString().equals("base"), "zero-length append changed content: " + sb);
    }

    static void testReturnsThis() {
        StringBuffer sb = new StringBuffer("q");
        StringBuffer r = sb.append(new char[]{'z'}, 0, 1);
        check(r == sb, "append(char[],int,int) must return this");
    }

    static void testToStringCacheInvalidated() {
        StringBuffer sb = new StringBuffer("ab");
        String before = sb.toString();
        check(before.equals("ab"), "unexpected initial content");
        sb.append(new char[]{'x', 'y'}, 0, 2);
        check(sb.toString().equals("abxy"), "stale toString after append: " + sb);
    }

    static void expectIOOBE(int offset, int len) {
        StringBuffer sb = new StringBuffer("base");
        char[] src = {'a', 'b', 'c'};
        try {
            sb.append(src, offset, len);
        } catch (IndexOutOfBoundsException e) {
            check(sb.toString().equals("base"),
                  "buffer modified after failed append: " + sb);
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for offset="
                                   + offset + " len=" + len);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
