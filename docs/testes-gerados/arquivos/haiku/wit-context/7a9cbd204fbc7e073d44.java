/*
 * @test
 * @summary Witup-derived tests for StringBuilder.insert(int, Object)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertNullObject();
        testInsertIntegerInMiddle();
        testInsertAtEnd();
        testReturnsSameInstance();
        testOffsetNegative();
        testOffsetBeyondLength();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertNullObject() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, (Object) null);
        check(sb.toString().equals("nullab"),
              "insert(0, null) expected \"nullab\" but was \"" + sb + "\"");
    }

    static void testInsertIntegerInMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, (Object) Integer.valueOf(42));
        check(sb.toString().equals("a42b"),
              "insert(1, 42) expected \"a42b\" but was \"" + sb + "\"");
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, (Object) "cd");
        check(sb.toString().equals("abcd"),
              "insert(length, obj) expected \"abcd\" but was \"" + sb + "\"");
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder result = sb.insert(0, (Object) "y");
        check(result == sb, "insert(int, Object) must return this");
        check(sb.toString().equals("yx"),
              "insert(0, \"y\") expected \"yx\" but was \"" + sb + "\"");
    }

    static void testOffsetNegative() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(-1, (Object) "x");
        } catch (StringIndexOutOfBoundsException e) {
            check(sb.toString().equals("ab"), "builder must be unchanged after failed insert");
            return;
        }
        throw new RuntimeException("insert(-1, obj) expected StringIndexOutOfBoundsException");
    }

    static void testOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(3, (Object) "x");
        } catch (StringIndexOutOfBoundsException e) {
            check(sb.toString().equals("ab"), "builder must be unchanged after failed insert");
            return;
        }
        throw new RuntimeException("insert(length+1, obj) expected StringIndexOutOfBoundsException");
    }
}
