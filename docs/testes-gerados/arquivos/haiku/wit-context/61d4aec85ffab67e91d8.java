/*
 * @test
 * @summary Witup-generated checks for StringBuffer.append(char[])
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendEmptyArrayKeepsContent();
        testAppendReturnsSameInstance();
        testAppendToEmptyBuffer();
        testAppendToExistingContent();
        testAppendAfterToStringInvalidatesCache();
        testMultipleAppends();
        testAppendNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendEmptyArrayKeepsContent() {
        StringBuffer sb = new StringBuffer("x");
        sb.append(new char[0]);
        check("x".equals(sb.toString()), "appending empty array must not change content");
        check(sb.length() == 1, "length must remain 1 after appending empty array");
    }

    static void testAppendReturnsSameInstance() {
        StringBuffer sb = new StringBuffer();
        StringBuffer ret = sb.append("abc".toCharArray());
        check(ret == sb, "append(char[]) must return this");
    }

    static void testAppendToEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.append("abc".toCharArray());
        check("abc".equals(sb.toString()), "append to empty buffer must yield the array contents");
    }

    static void testAppendToExistingContent() {
        StringBuffer sb = new StringBuffer("ab");
        sb.append(new char[] {'c', 'd'});
        check("abcd".equals(sb.toString()), "append must add chars at the end of existing content");
    }

    static void testAppendAfterToStringInvalidatesCache() {
        StringBuffer sb = new StringBuffer("start");
        String before = sb.toString();
        check("start".equals(before), "initial toString mismatch");
        sb.append("le".toCharArray());
        check("startle".equals(sb.toString()), "toString must reflect append after a previous toString");
    }

    static void testMultipleAppends() {
        StringBuffer sb = new StringBuffer();
        sb.append("a".toCharArray()).append("b".toCharArray()).append(new char[0]).append("c".toCharArray());
        check("abc".equals(sb.toString()), "chained appends must concatenate in order");
    }

    static void testAppendNullThrowsNullPointerException() {
        StringBuffer sb = new StringBuffer("keep");
        boolean thrown = false;
        try {
            sb.append((char[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "append((char[]) null) must throw NullPointerException");
        check("keep".equals(sb.toString()), "failed append must not change content");
    }
}
