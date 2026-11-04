/*
 * @test
 * @summary Behavioral tests for Vector.insertElementAt(E, int) using only public API
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testInsertIntoEmptyAtZero();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertTriggersGrowth();
        testInsertBeyondSizeThrows();
        testInsertNegativeIndexThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertIntoEmptyAtZero() {
        Vector<String> v = new Vector<>();
        v.insertElementAt("a", 0);
        check(v.size() == 1, "size should be 1");
        check("a".equals(v.get(0)), "element 0 should be a");
    }

    static void testInsertAtStart() {
        Vector<String> v = new Vector<>();
        v.add("b");
        v.add("c");
        v.insertElementAt("a", 0);
        check(v.size() == 3, "size should be 3");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("b".equals(v.get(1)), "index 1 should be b");
        check("c".equals(v.get(2)), "index 2 should be c");
    }

    static void testInsertAtEnd() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.insertElementAt("c", 2);
        check(v.size() == 3, "size should be 3");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("b".equals(v.get(1)), "index 1 should be b");
        check("c".equals(v.get(2)), "index 2 should be c");
    }

    static void testInsertInMiddle() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("c");
        v.insertElementAt("b", 1);
        check(v.size() == 3, "size should be 3");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("b".equals(v.get(1)), "index 1 should be b");
        check("c".equals(v.get(2)), "index 2 should be c");
    }

    static void testInsertTriggersGrowth() {
        Vector<String> v = new Vector<>(1, 1);
        v.add("b");
        v.insertElementAt("a", 0);
        check(v.size() == 2, "size should be 2");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("b".equals(v.get(1)), "index 1 should be b");
        v.insertElementAt("c", 2);
        check(v.size() == 3, "size should be 3 after second insert");
        check("c".equals(v.get(2)), "index 2 should be c");
    }

    static void testInsertBeyondSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        boolean thrown = false;
        try {
            v.insertElementAt("x", 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for index > size");
        check(v.size() == 1, "size should remain 1 after failed insert");
        check("a".equals(v.get(0)), "element 0 should remain a");
    }

    static void testInsertNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        boolean thrown = false;
        try {
            v.insertElementAt("x", -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for negative index");
        check(v.size() == 1, "size should remain 1 after failed insert");
        check("a".equals(v.get(0)), "element 0 should remain a");
    }
}
