/* @test    @summary Test for Collections.forEachRemaining method to ensure it applies the consumer to all remaining elements.    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingWithEmptyIterator();
    }

    public static void testForEachRemainingAppliesConsumer() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        iterator.next(); // advance iterator to skip first element
        Consumer<Integer> consumer = result::add;
        java.util.Collections.forEachRemaining(iterator, consumer);
        // Expect remaining elements: 2, 3, 4, 5
        if (result.size() != 4) {
            throw new AssertionError("Expected 4 elements, got " + result.size());
        }
        if (!result.equals(Arrays.asList(2, 3, 4, 5))) {
            throw new AssertionError("Unexpected elements: " + result);
        }
    }

    public static void testForEachRemainingWithEmptyIterator() {
        List<Integer> list = Arrays.asList();
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        Consumer<Integer> consumer = result::add;
        java.util.Collections.forEachRemaining(iterator, consumer);
        // Expect no elements
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty result, got: " + result);
        }
    }
}
