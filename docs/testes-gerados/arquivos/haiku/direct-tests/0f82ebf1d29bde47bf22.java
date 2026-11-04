/*
 * @test
 * @summary Observable behavior of Vector.set(int, E): return value, replacement, size and out-of-bounds exceptions
 * @run main VectorWitupTest
 */

public class VectorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsOldValueAndReplaces() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        String old = v.set(1, "B");
        check("b".equals(old), "set should return previous element, got " + old);
        check("B".equals(v.get(1)), "set should store new element");
        check("a".equals(v.get(0)), "set must not change index 0");
        check("c".equals(v.get(2)), "set must not change index 2");
        check(v.size() == 3, "set must not change size");
    }

    static void testSetFirstAndLastIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);
        Integer first = v.set(0, 11);
        check(first == 10, "first old value expected 10");
        Integer last = v.set(1, 21);
        check(last == 20, "last old value expected 20");
        check(v.get(0) == 11 && v.get(1) == 21, "values not replaced correctly");
    }

    static void testSetNullElement() {
        Vector<String> v = new Vector<>();
        v.add("x");
        String old = v.set(0, null);
        check("x".equals(old), "old value expected x");
        check(v.get(0) == null, "null element should be stored");
    }

    static void testSetReturnsNullWhenOldWasNull() {
        Vector<String> v = new Vector<>();
        v.add(null);
        String old = v.set(0, "y");
        check(old == null, "old value expected null");
        check("y".equals(v.get(0)), "new value not stored");
    }

    static void testIndexEqualToSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        boolean thrown = false;
        try {
            v.set(2, "c");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(size, e) must throw ArrayIndexOutOfBoundsException");
        check(v.size() == 2, "failed set must not change size");
        check("a".equals(v.get(0)) && "b".equals(v.get(1)), "failed set must not change elements");
    }

    static void testEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        boolean thrown = false;
        try {
            v.set(0, "a");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set on empty vector must throw ArrayIndexOutOfBoundsException");
        check(v.isEmpty(), "vector must remain empty");
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        boolean thrown = false;
        try {
            v.set(-1, "z");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(-1, e) must throw ArrayIndexOutOfBoundsException");
        check("a".equals(v.get(0)), "failed set must not change elements");
    }

    public static void main(String[] args) {
        testReturnsOldValueAndReplaces();
        testSetFirstAndLastIndex();
        testSetNullElement();
        testSetReturnsNullWhenOldWasNull();
        testIndexEqualToSizeThrows();
        testEmptyVectorThrows();
        testNegativeIndexThrows();
    }
}
