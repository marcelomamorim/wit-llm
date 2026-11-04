/*
 * @test
 * @summary WITUP-derived behavioral tests for Vector.get(int) bounds checking
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyVectorGetThrows();
        testValidIndicesReturnElements();
        testIndexEqualToSizeThrows();
        testNegativeIndexThrows();
        testNullElementIsReturned();
        testCollectionConstructorBounds();
        testCapacityConstructorsDoNotAddElements();
        testGetAfterRemovalThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkThrowsAioobe(Vector<String> v, int index) {
        try {
            v.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }

    static void testEmptyVectorGetThrows() {
        Vector<String> v = new Vector<>();
        checkThrowsAioobe(v, 0);
    }

    static void testValidIndicesReturnElements() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        check("a".equals(v.get(0)), "get(0) should return first element");
        check("b".equals(v.get(1)), "get(1) should return second element");
    }

    static void testIndexEqualToSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        checkThrowsAioobe(v, 2);
        checkThrowsAioobe(v, 3);
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        checkThrowsAioobe(v, -1);
    }

    static void testNullElementIsReturned() {
        Vector<String> v = new Vector<>();
        v.add(null);
        check(v.get(0) == null, "get(0) should return null element");
    }

    static void testCollectionConstructorBounds() {
        Vector<String> v = new Vector<>(Arrays.asList("x", "y", "z"));
        check("z".equals(v.get(2)), "get(2) should return last element of copied collection");
        checkThrowsAioobe(v, 3);
    }

    static void testCapacityConstructorsDoNotAddElements() {
        Vector<String> v1 = new Vector<>(10);
        checkThrowsAioobe(v1, 0);
        Vector<String> v2 = new Vector<>(4, 2);
        checkThrowsAioobe(v2, 0);
    }

    static void testGetAfterRemovalThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.remove(1);
        check("a".equals(v.get(0)), "get(0) should still return first element");
        checkThrowsAioobe(v, 1);
    }
}
