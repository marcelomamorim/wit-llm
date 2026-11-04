/* @test    @summary Test for Collections.forEachRemaining method to ensure it executes the consumer on all remaining elements.    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingExecutesConsumer();
        testForEachRemainingWithEmptyIterator();
    }

    private static void testForEachRemainingExecutesConsumer() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        // Advance iterator to skip first element
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Use forEachRemaining to process remaining elements
        iterator.forEachRemaining(result::add);
        // Expect remaining elements: 2,3,4,5
        boolean condition = result.size() == 4 && result.contains(2) && result.contains(3) && result.contains(4) && result.contains(5);
        if (!condition) {
            throw new AssertionError("forEachRemaining did not process remaining elements correctly. Result: " + result);
        }
    }

    private static void testForEachRemainingWithEmptyIterator() {
        List<Integer> emptyList = new ArrayList<>();
        Iterator<Integer> emptyIterator = emptyList.iterator();
        List<Integer> result = new ArrayList<>();
        emptyIterator.forEachRemaining(result::add);
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty result for empty iterator, but got: " + result);
        }
    }
}
