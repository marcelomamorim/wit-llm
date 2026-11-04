/*
 * @test
 * @summary Test for Vector.forEachRemaining to verify behavior with null Consumer.
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithEmptyVector();
    }

    static void testForEachRemainingWithNullConsumer() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        boolean exceptionThrown = false;
        try {
            vector.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        Vector<Integer> vector = new Vector<>();
        vector.add(10);
        vector.add(20);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i).append(",");
        vector.forEachRemaining(consumer);
        String result = sb.toString();
        if (!result.equals("10,20,")) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testForEachRemainingWithEmptyVector() {
        Vector<Integer> vector = new Vector<>();
        Consumer<Integer> consumer = (Integer i) -> { throw new AssertionError("Should not be called"); };
        vector.forEachRemaining(consumer); // should not throw or call consumer
    }
}
