/*
 * @test
 * @summary Test for ArrayList.forEach method to verify behavior with null Consumer.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    private static void testForEachWithNullConsumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean exceptionThrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachWithValidConsumer() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        final int[] sum = {0};
        Consumer<Integer> consumer = (Integer i) -> { sum[0] += i; };
        list.forEach(consumer);
        if (sum[0] != 3) {
            throw new AssertionError("Sum should be 3, but was " + sum[0]);
        }
    }
}
