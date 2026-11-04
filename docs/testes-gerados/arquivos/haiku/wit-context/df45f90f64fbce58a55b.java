/*
 * @test
 * @summary Checks StringBuffer.insert(int, Object) for null, object conversion, insertion positions and toString cache invalidation
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {

    public static void main(String[] args) {
        testNullObjectInsertsNullText();
        testObjectToStringIsInserted();
        testInsertAtStartMiddleAndEnd();
        testInsertedValueIsVisibleAfterPreviousToString();
    }

    private static void testNullObjectInsertsNullText() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.insert(0, (Object) null);
        check(result == sb, "insert must return the same StringBuffer");
        check("nullabc".equals(sb.toString()), "expected nullabc but was " + sb);
    }

    private static void testObjectToStringIsInserted() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, (Object) Character.valueOf('x'));
        check("axbc".equals(sb.toString()), "expected axbc but was " + sb);

        StringBuffer sb2 = new StringBuffer("abc");
        Object custom = new Object() {
            @Override
            public String toString() {
                return "CUSTOM";
            }
        };
        sb2.insert(3, custom);
        check("abcCUSTOM".equals(sb2.toString()), "expected abcCUSTOM but was " + sb2);
    }

    private static void testInsertAtStartMiddleAndEnd() {
        StringBuffer start = new StringBuffer("bc");
        start.insert(0, (Object) Integer.valueOf(42));
        check("42bc".equals(start.toString()), "start insert failed: " + start);

        StringBuffer middle = new StringBuffer("ac");
        middle.insert(1, (Object) Integer.valueOf(42));
        check("a42c".equals(middle.toString()), "middle insert failed: " + middle);

        StringBuffer end = new StringBuffer("ab");
        end.insert(2, (Object) Integer.valueOf(42));
        check("ab42".equals(end.toString()), "end insert failed: " + end);
    }

    private static void testInsertedValueIsVisibleAfterPreviousToString() {
        StringBuffer sb = new StringBuffer("a");
        check("a".equals(sb.toString()), "initial toString failed");
        sb.insert(1, (Object) "b");
        check("ab".equals(sb.toString()), "toString cache not refreshed after insert: " + sb);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
