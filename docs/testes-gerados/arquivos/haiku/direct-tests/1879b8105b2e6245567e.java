/*
 * @test
 * @summary StringBuffer.setCharAt updates the content, invalidates the cached toString and rejects invalid indexes
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {
    public static void main(String[] args) {
        testUpdatesCharacters();
        testToStringCacheInvalidated();
        testIndexOutOfBounds();
    }

    static void testUpdatesCharacters() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setCharAt(0, 'X');
        sb.setCharAt(2, 'Z');
        check("XbZ".equals(sb.toString()), "unexpected content: " + sb);
        check(sb.length() == 3, "length changed: " + sb.length());
    }

    static void testToStringCacheInvalidated() {
        StringBuffer sb = new StringBuffer("start");
        String before = sb.toString();
        sb.setCharAt(4, 'x');
        check("start".equals(before), "earlier snapshot changed: " + before);
        check("starx".equals(sb.toString()), "stale toString: " + sb.toString());
        sb.setCharAt(0, 'S');
        check("Starx".equals(sb.toString()), "stale toString after second update: " + sb.toString());
    }

    static void testIndexOutOfBounds() {
        StringBuffer sb = new StringBuffer("ab");
        expectIndexException(sb, -1, 'x');
        expectIndexException(sb, 2, 'x');
        expectIndexException(new StringBuffer(), 0, 'x');
        check("ab".equals(sb.toString()), "content modified after failed call: " + sb);
    }

    static void expectIndexException(StringBuffer sb, int index, char ch) {
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
