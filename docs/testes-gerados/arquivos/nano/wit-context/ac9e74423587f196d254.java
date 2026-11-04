/*
 * @test
 * @summary Test for Vector.forEach method to verify behavior with null Consumer.
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    private static void testForEachWithNullConsumer() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        boolean exceptionThrown = false;
        try {
            vector.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachWithValidConsumer() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        vector.forEach(consumer);
        String result = sb.toString();
        if (!"ab".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
