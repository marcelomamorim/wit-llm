/*
 * @test
 * @summary Witup-derived checks for StringBuffer.insert(int, String) offset validation and null handling
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidInsertPositions();
        testNullStringInsertsNull();
        testOutOfBoundsOffsetsThrow();
        testFailedInsertLeavesBufferUnchanged();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidInsertPositions() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(0, "x");
        check("xstart".equals(sb.toString()), "insert at offset 0 failed: " + sb);

        sb = new StringBuffer("start");
        sb.insert(sb.length(), "y");
        check("starty".equals(sb.toString()), "insert at length failed: " + sb);

        sb = new StringBuffer("start");
        sb.insert(4, "le");
        check("starlet".equals(sb.toString()), "insert in middle failed: " + sb);

        sb = new StringBuffer("abc");
        StringBuffer returned = sb.insert(1, "Z");
        check(returned == sb, "insert must return this");
        check("aZbc".equals(sb.toString()), "unexpected content: " + sb);
    }

    static void testNullStringInsertsNull() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(1, (String) null);
        check("anullb".equals(sb.toString()), "null str must insert four characters null: " + sb);
    }

    static void testOutOfBoundsOffsetsThrow() {
        expectSIOOBE(new StringBuffer("abc"), -1, "x");
        expectSIOOBE(new StringBuffer("abc"), 4, "x");
        expectSIOOBE(new StringBuffer(""), 1, "x");
        expectSIOOBE(new StringBuffer(""), -1, "x");
    }

    static void testFailedInsertLeavesBufferUnchanged() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(5, "x");
        } catch (StringIndexOutOfBoundsException e) {
            check("abc".equals(sb.toString()), "buffer modified by failed insert: " + sb);
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException");
    }

    static void expectSIOOBE(StringBuffer sb, int offset, String str) {
        try {
            sb.insert(offset, str);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
