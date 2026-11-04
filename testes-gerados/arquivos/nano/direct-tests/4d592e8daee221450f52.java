/* @test    @summary Test Arrays.forEach method for correct iteration and consumer invocation    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.Consumer;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testForEachWithNonEmptyArray();
        testForEachWithEmptyArray();
        testForEachWithNullConsumer();
    }

    static void testForEachWithNonEmptyArray() {
        Integer[] array = {1, 2, 3};
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        Arrays.forEach(array, consumer);
        String result = sb.toString();
        if (!"1,2,3,".equals(result)) {
            throw new AssertionError("Expected '1,2,3,', but got: " + result);
        }
    }

    static void testForEachWithEmptyArray() {
        Integer[] array = {};
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        Arrays.forEach(array, consumer);
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty StringBuilder, but got length: " + sb.length());
        }
    }

    static void testForEachWithNullConsumer() {
        Integer[] array = {1, 2, 3};
        try {
            Arrays.forEach(array, null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
