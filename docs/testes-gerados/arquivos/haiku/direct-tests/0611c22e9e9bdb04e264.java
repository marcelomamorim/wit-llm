/*
 * @test
 * @summary Verifies StringBuilder.insert(int, CharSequence) inserts content, returns this, handles null and rejects invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertInMiddleReturnsSameInstance();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertNullCharSequenceAppendsNullText();
        testInsertStringBuilderAsCharSequence();
        testInsertNegativeOffsetThrows();
        testInsertOffsetBeyondLengthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertInMiddleReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("start");
        CharSequence s = "le";
        StringBuilder result = sb.insert(4, s);
        check(result == sb, "insert must return this");
        check("starlet".equals(sb.toString()), "expected 'starlet' but was '" + sb + "'");
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        CharSequence s = "xy";
        sb.insert(0, s);
        check("xyabc".equals(sb.toString()), "expected 'xyabc' but was '" + sb + "'");
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        CharSequence s = "cd";
        sb.insert(2, s);
        check("abcd".equals(sb.toString()), "expected 'abcd' but was '" + sb + "'");
    }

    static void testInsertNullCharSequenceAppendsNullText() {
        StringBuilder sb = new StringBuilder("ab");
        CharSequence nothing = null;
        sb.insert(1, nothing);
        check("anullb".equals(sb.toString()), "expected 'anullb' but was '" + sb + "'");
    }

    static void testInsertStringBuilderAsCharSequence() {
        StringBuilder sb = new StringBuilder("ab");
        CharSequence src = new StringBuilder("XYZ");
        sb.insert(1, src);
        check("aXYZb".equals(sb.toString()), "expected 'aXYZb' but was '" + sb + "'");
    }

    static void testInsertNegativeOffsetThrows() {
        StringBuilder sb = new StringBuilder("ab");
        CharSequence s = "x";
        boolean thrown = false;
        try {
            sb.insert(-1, s);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative offset must throw StringIndexOutOfBoundsException");
        check("ab".equals(sb.toString()), "builder must be unchanged after failed insert");
    }

    static void testInsertOffsetBeyondLengthThrows() {
        StringBuilder sb = new StringBuilder("ab");
        CharSequence s = "x";
        boolean thrown = false;
        try {
            sb.insert(10, s);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset beyond length must throw StringIndexOutOfBoundsException");
        check("ab".equals(sb.toString()), "builder must be unchanged after failed insert");
    }
}
