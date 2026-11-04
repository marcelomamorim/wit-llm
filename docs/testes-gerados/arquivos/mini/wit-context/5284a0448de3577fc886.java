/*
 * @test
 * @summary Test Collections.UnmodifiableNavigableSet.pollFirst throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a NavigableSet and wrap it with unmodifiableNavigableSet
        NavigableSet<String> set = new TreeSet<>();
        set.add("a");
        set.add("b");
        NavigableSet<String> unmodSet = Collections.unmodifiableNavigableSet(set);

        // Test pollFirst throws UnsupportedOperationException
        try {
            unmodSet.pollFirst();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Additional sanity check: pollFirst on modifiable set returns first element
        String first = set.pollFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("Unexpected pollFirst result on modifiable set");
        }

        // Confirm the element was removed
        if (set.contains("a")) {
            throw new RuntimeException("Element not removed by pollFirst");
        }
    }
}
