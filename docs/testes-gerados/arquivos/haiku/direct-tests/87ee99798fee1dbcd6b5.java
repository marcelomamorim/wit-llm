/*
 * @test
 * @summary Behavioral checks for Arrays.copyOfRange(U[], int, int, Class<? extends T[]>)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    public static void main(String[] args) {
        testBasicCopyWithStringComponent();
        testPaddingWhenToExceedsOriginal();
        testObjectArrayClassUsesObjectArray();
        testEmptyRange();
        testNegativeLengthThrows();
        testFromGreaterThanOriginalLengthThrows();
        testComponentTypeFromObjectSource();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testBasicCopyWithStringComponent() {
        String[] src = {"a", "b", "c"};
        String[] r = Arrays.copyOfRange(src, 0, 2, String[].class);
        check(r.length == 2, "basic length");
        check("a".equals(r[0]) && "b".equals(r[1]), "basic content: " + Arrays.toString(r));
        check(r.getClass() == String[].class, "basic runtime class");
    }

    static void testPaddingWhenToExceedsOriginal() {
        String[] src = {"a", "b"};
        String[] r = Arrays.copyOfRange(src, 1, 4, String[].class);
        check(r.length == 3, "padding length");
        check("b".equals(r[0]), "padding first element");
        check(r[1] == null && r[2] == null, "padding nulls: " + Arrays.toString(r));
    }

    static void testObjectArrayClassUsesObjectArray() {
        Integer[] src = {1, 2};
        Object[] r = Arrays.copyOfRange(src, 0, 2, Object[].class);
        check(r.getClass() == Object[].class, "Object[] class result");
        check(r.length == 2 && r[0].equals(1) && r[1].equals(2), "Object[] content");
    }

    static void testEmptyRange() {
        String[] src = {"a", "b"};
        String[] r = Arrays.copyOfRange(src, 1, 1, String[].class);
        check(r.length == 0, "empty range length");
        check(r.getClass() == String[].class, "empty range class");
    }

    static void testNegativeLengthThrows() {
        boolean thrown = false;
        try {
            Arrays.copyOfRange(new String[]{"a", "b"}, 2, 1, String[].class);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "to < from must throw IllegalArgumentException");
    }

    static void testFromGreaterThanOriginalLengthThrows() {
        boolean thrown = false;
        try {
            Arrays.copyOfRange(new String[]{"a"}, 2, 3, String[].class);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "from beyond original length must throw IndexOutOfBoundsException");
    }

    static void testComponentTypeFromObjectSource() {
        Object[] src = {"x", "y"};
        String[] r = Arrays.copyOfRange(src, 0, 1, String[].class);
        check(r.getClass() == String[].class, "component type from Object source");
        check("x".equals(r[0]), "Object source content");
    }
}
