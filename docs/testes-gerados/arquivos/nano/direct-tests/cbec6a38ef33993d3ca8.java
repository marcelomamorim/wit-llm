/* @test    @summary Test for Collections.forEachRemaining method to ensure it applies the consumer to each remaining element.    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingNullConsumer();
    }

    private static void testForEachRemainingAppliesConsumer() {
        List<String> list = Arrays.asList("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = list.iterator();
        Consumer<String> consumer = s -> sb.append(s);
        // Advance iterator to skip first element
        iterator.next();
        // Call forEachRemaining, should apply consumer to remaining elements
        Collections.forEachRemaining(iterator, consumer);
        // Remaining elements are "b" and "c"
        if (!"bc".equals(sb.toString())) {
            throw new AssertionError("Expected 'bc', but got: " + sb.toString());
        }
    }

    private static void testForEachRemainingNullConsumer() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<Integer> iterator = list.iterator();
        boolean exceptionThrown = false;
        try {
            // Passing null should throw NullPointerException
            Collections.forEachRemaining(iterator, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        }
    }
}
