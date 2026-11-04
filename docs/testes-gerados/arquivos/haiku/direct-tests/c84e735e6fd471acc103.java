/*
 * @test
 * @summary Witup-style tests for Vector.elementAt bounds and element retrieval
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testValidIndexes();
        testIndexEqualToSizeThrows();
        testNegativeIndexThrows();
        testSizeAfterRemovalIsRespected();
        testDefaultCapacityConstructorElementAt();
    }

    static void testValidIndexes() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        check("a".equals(v.elementAt(0)), "elementAt(0) should return first element");
        check("b".equals(v.elementAt(1)), "elementAt(1) should return second element");
        check("c".equals(v.elementAt(2)), "elementAt(2) should return last element");
    }

    static void testIndexEqualToSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.elementAt(2);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index == size");
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = e.getMessage();
            check("2 >= 2".equals(msg), "unexpected message: " + msg);
        }
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        expectAioobe(v, -1);
    }

    static void testSizeAfterRemovalIsRespected() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.remove(1);
        check("a".equals(v.elementAt(0)), "element 0 should remain after removal");
        expectAioobe(v, 1);
    }

    static void testDefaultCapacityConstructorElementAt() {
        Vector<Integer> v = new Vector<>(10);
        expectAioobe(v, 0);
        v.add(Integer.valueOf(42));
        check(v.elementAt(0).intValue() == 42, "elementAt(0) should return 42");
        expectAioobe(v, 1);
    }

    static void expectAioobe(Vector<?> v, int index) {
        try {
            v.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
