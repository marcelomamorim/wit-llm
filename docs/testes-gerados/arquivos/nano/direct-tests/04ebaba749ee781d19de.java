/* @test    @summary Test Vector.replaceAll method for replacing elements with a unary operator    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.UnaryOperator;

public class VectorWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithDouble();
        testReplaceAllWithNullOperator();
        testConcurrentModification();
    }

    static void testReplaceAllWithIncrement() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.replaceAll(x -> x + 1);
        assert vector.get(0) == 2 : "Expected 2, got " + vector.get(0);
        assert vector.get(1) == 3 : "Expected 3, got " + vector.get(1);
        assert vector.get(2) == 4 : "Expected 4, got " + vector.get(2);
    }

    static void testReplaceAllWithDouble() {
        Vector<Double> vector = new Vector<>();
        vector.add(1.0);
        vector.add(2.0);
        vector.add(3.0);
        vector.replaceAll(x -> x * 2);
        assert vector.get(0) == 2.0 : "Expected 2.0, got " + vector.get(0);
        assert vector.get(1) == 4.0 : "Expected 4.0, got " + vector.get(1);
        assert vector.get(2) == 6.0 : "Expected 6.0, got " + vector.get(2);
    }

    static void testReplaceAllWithNullOperator() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcurrentModification() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        final int expectedModCount = vector.modCount;
        try {
            for (int i = 0; i < vector.size(); i++) {
                if (i == 1) {
                    vector.add(4); // modify during iteration
                }
                vector.replaceAll(x -> x + 1); // call replaceAll during iteration
            }
            assert false : "Expected ConcurrentModificationException";
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
    }
}
