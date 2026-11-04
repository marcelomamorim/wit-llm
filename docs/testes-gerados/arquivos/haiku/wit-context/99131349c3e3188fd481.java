/*
 * @test
 * @summary Witup-derived checks for StringBuilder.insert(int, String) boundary and exception behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertInMiddle();
        testInsertAtBoundaries();
        testNullStringInsertsNullLiteral();
        testNegativeOffsetThrows();
        testOffsetGreaterThanLengthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertInMiddle() {
        StringBuilder sb = new StringBuilder("start");
        StringBuilder result = sb.insert(4, "le");
        check(result == sb, "insert must return the same builder");
        check("starlet".equals(sb.toString()), "expected starlet but was " + sb);
    }

    static void testInsertAtBoundaries() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, "x");
        check("xab".equals(sb.toString()), "insert at 0 failed: " + sb);

        sb.insert(sb.length(), "y");
        check("xaby".equals(sb.toString()), "insert at length failed: " + sb);

        StringBuilder empty = new StringBuilder();
        empty.insert(0, "abc");
        check("abc".equals(empty.toString()), "insert into empty builder failed: " + empty);
    }

    static void testNullStringInsertsNullLiteral() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, (String) null);
        check("anullb".equals(sb.toString()), "null insert expected anullb but was " + sb);
    }

    static void testNegativeOffsetThrows() {
        boolean thrown = false;
        try {
            new StringBuilder("ab").insert(-1, "x");
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative offset must throw StringIndexOutOfBoundsException");
    }

    static void testOffsetGreaterThanLengthThrows() {
        boolean thrown = false;
        try {
            new StringBuilder("ab").insert(3, "x");
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset greater than length must throw StringIndexOutOfBoundsException");
    }
}
