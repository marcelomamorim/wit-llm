/*
 * @test
 * @summary Tests the forEach method of java.util.Vector for correct behavior.
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyVector();
        testForEachWithNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachWithElements() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        StringBuilder result = new StringBuilder();
        vector.forEach(i -> result.append(i));
        assert result.toString().equals("123") : "Expected '123', got '" + result + "'";
    }

    static void testForEachWithEmptyVector() {
        Vector<String> vector = new Vector<>();
        StringBuilder result = new StringBuilder();
        vector.forEach(s -> result.append(s));
        assert result.toString().isEmpty() : "Expected empty string";
    }

    static void testForEachWithNullAction() {
        Vector<Double> vector = new Vector<>();
        vector.add(1.1);
        boolean exceptionThrown = false;
        try {
            vector.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when action is null";
    }

    static void testForEachConcurrentModification() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean exceptionThrown = false;
        try {
            vector.forEach(i -> {
                if (i == 2) {
                    vector.add(4); // modify during iteration
                }
            });
        } catch (ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ConcurrentModificationException when vector is modified during forEach";
    }
}
