/*
 * @test
 * @summary Behavioral checks for StringBuffer.insert(int, Object)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullObjectInsertsNullText() {
        StringBuffer sb = new StringBuffer("ab");
        StringBuffer result = sb.insert(1, (Object) null);
        check("anullb".equals(sb.toString()), "null object should insert text 'null', got " + sb);
        check(result == sb, "insert should return the same StringBuffer instance");
    }

    static void testIntegerObjectUsesToString() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(0, (Object) Integer.valueOf(42));
        check("42ab".equals(sb.toString()), "expected '42ab', got " + sb);
    }

    static void testCustomToStringIsUsed() {
        StringBuffer sb = new StringBuffer("xy");
        Object o = new Object() {
            @Override
            public String toString() {
                return "MID";
            }
        };
        sb.insert(1, o);
        check("xMIDy".equals(sb.toString()), "expected 'xMIDy', got " + sb);
    }

    static void testInsertAtEndActsLikeAppend() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(sb.length(), (Object) "le");
        check("startle".equals(sb.toString()), "expected 'startle', got " + sb);
    }

    static void testCacheInvalidatedAfterInsert() {
        StringBuffer sb = new StringBuffer("abc");
        String before = sb.toString();
        check("abc".equals(before), "unexpected initial toString");
        sb.insert(0, (Object) Integer.valueOf(7));
        check("7abc".equals(sb.toString()), "toString must reflect insert, got " + sb);
    }

    static void testChainedInsertsAccumulate() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, (Object) "b").insert(0, (Object) "a").insert(2, (Object) Boolean.TRUE);
        check("abtrue".equals(sb.toString()), "expected 'abtrue', got " + sb);
    }

    public static void main(String[] args) {
        testNullObjectInsertsNullText();
        testIntegerObjectUsesToString();
        testCustomToStringIsUsed();
        testInsertAtEndActsLikeAppend();
        testCacheInvalidatedAfterInsert();
        testChainedInsertsAccumulate();
    }
}
