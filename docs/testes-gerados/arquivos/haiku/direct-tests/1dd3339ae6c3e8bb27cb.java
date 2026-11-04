/*
 * @test
 * @summary Functional tests for Vector.add(int index, E element) on valid and invalid indexes
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testInsertIntoEmptyVector();
        testInsertAtBeginningMiddleAndEnd();
        testInsertNullElement();
        testIndexGreaterThanSizeThrows();
        testNegativeIndexThrows();
    }

    static void testInsertIntoEmptyVector() {
        Vector<String> v = new Vector<>();
        v.add(0, "a");
        check(v.size() == 1, "size after insert into empty vector");
        check("a".equals(v.get(0)), "element after insert into empty vector");
    }

    static void testInsertAtBeginningMiddleAndEnd() {
        Vector<String> v = new Vector<>();
        v.add(0, "a");
        v.add(1, "c");
        v.add(1, "b");
        check(v.size() == 3, "size after three inserts");
        check("a".equals(v.get(0)), "index 0 after middle insert");
        check("b".equals(v.get(1)), "index 1 after middle insert");
        check("c".equals(v.get(2)), "index 2 after middle insert");

        v.add(0, "z");
        check(v.size() == 4, "size after insert at beginning");
        check("z".equals(v.get(0)), "new first element");
        check("a".equals(v.get(1)), "shifted element after beginning insert");

        v.add(v.size(), "end");
        check(v.size() == 5, "size after insert at end");
        check("end".equals(v.get(4)), "last element after insert at end");
    }

    static void testInsertNullElement() {
        Vector<String> v = new Vector<>();
        v.add(0, "x");
        v.add(0, null);
        check(v.size() == 2, "size after null insert");
        check(v.get(0) == null, "null stored at requested index");
        check("x".equals(v.get(1)), "existing element shifted after null insert");
    }

    static void testIndexGreaterThanSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add(0, "a");
        boolean thrown = false;
        try {
            v.add(v.size() + 1, "x");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "IndexOutOfBoundsException for index greater than size");
        check(v.size() == 1, "size unchanged after failed insert");
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add(0, "a");
        boolean thrown = false;
        try {
            v.add(-1, "x");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "IndexOutOfBoundsException for negative index");
        check(v.size() == 1, "size unchanged after negative index insert");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
