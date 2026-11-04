/*
 * @test
 * @summary Verifies observable behavior of Vector.get(int) bounds checking and element retrieval
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.Vector;

public class VectorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectAioobe(Vector<String> v, int index) {
        try {
            Object o = v.get(index);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index " + index + " but got " + o);
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testGetReturnsElementsInOrder() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        check("a".equals(v.get(0)), "get(0) should return first element");
        check("b".equals(v.get(1)), "get(1) should return second element");
        check("c".equals(v.get(2)), "get(2) should return third element");
    }

    static void testGetAtSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        expectAioobe(v, 2);
    }

    static void testGetOnEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        expectAioobe(v, 0);
    }

    static void testGetNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        expectAioobe(v, -1);
    }

    static void testGetBeyondCapacityButNotSizeThrows() {
        Vector<String> v = new Vector<>(10);
        expectAioobe(v, 0);
        v.add("x");
        check("x".equals(v.get(0)), "get(0) should return added element");
        expectAioobe(v, 1);
    }

    static void testGetAfterRemoveUsesCurrentSize() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.remove("b");
        expectAioobe(v, 1);
        check("a".equals(v.get(0)), "get(0) should still return remaining element");
    }

    static void testGetFromCollectionConstructor() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3));
        check(v.get(0) == 1, "get(0) should be 1");
        check(v.get(2) == 3, "get(2) should be 3");
    }

    public static void main(String[] args) {
        testGetReturnsElementsInOrder();
        testGetAtSizeThrows();
        testGetOnEmptyVectorThrows();
        testGetNegativeIndexThrows();
        testGetBeyondCapacityButNotSizeThrows();
        testGetAfterRemoveUsesCurrentSize();
        testGetFromCollectionConstructor();
    }
}
