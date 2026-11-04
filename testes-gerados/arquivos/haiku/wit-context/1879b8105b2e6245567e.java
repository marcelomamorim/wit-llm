/*
 * @test
 * @summary Verifies StringBuffer.setCharAt updates content, invalidates the cached string and rejects out-of-range indexes
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidUpdate();
        testCacheInvalidation();
        testIndexEqualToLengthThrows();
        testNegativeIndexThrows();
        testEmptyBufferThrows();
    }

    static void testValidUpdate() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setCharAt(0, 'x');
        sb.setCharAt(2, 'z');
        check("xbz".equals(sb.toString()), "unexpected content: " + sb);
    }

    static void testCacheInvalidation() {
        StringBuffer sb = new StringBuffer("abc");
        String before = sb.toString();
        sb.setCharAt(1, 'Q');
        check("abc".equals(before), "previous string snapshot changed");
        check("aQc".equals(sb.toString()), "cached string not updated: " + sb);
    }

    static void testIndexEqualToLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        expectSIOOBE(sb, 3, 'x');
        check("abc".equals(sb.toString()), "buffer mutated after failed call");
    }

    static void testNegativeIndexThrows() {
        StringBuffer sb = new StringBuffer("abc");
        expectSIOOBE(sb, -1, 'x');
        check("abc".equals(sb.toString()), "buffer mutated after failed call");
    }

    static void testEmptyBufferThrows() {
        expectSIOOBE(new StringBuffer(), 0, 'x');
    }

    static void expectSIOOBE(StringBuffer sb, int index, char ch) {
        try {
            sb.setCharAt(index, ch);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
