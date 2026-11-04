/*
 * @test
 * @summary Verifies that the iterator returned by Collections.unmodifiableCollection delegates forEachRemaining to the backing iterator and preserves unmodifiable behavior
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingVisitsRemainingElements();
        testForEachRemainingOnExhaustedIterator();
        testForEachRemainingOnFreshIterator();
        testRemoveStillUnsupported();
    }

    static void testForEachRemainingVisitsRemainingElements() {
        List<String> src = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        check(it.hasNext(), "iterator should have elements");
        check("a".equals(it.next()), "first element should be a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c")),
              "forEachRemaining should visit remaining elements in order, got " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testForEachRemainingOnExhaustedIterator() {
        List<String> src = new ArrayList<>(Arrays.asList("x"));
        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        it.next();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "no elements should be visited when exhausted, got " + seen);
    }

    static void testForEachRemainingOnFreshIterator() {
        List<String> src = new ArrayList<>(Arrays.asList("p", "q"));
        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("p", "q")),
              "forEachRemaining on fresh iterator should visit all elements, got " + seen);
    }

    static void testRemoveStillUnsupported() {
        List<String> src = new ArrayList<>(Arrays.asList("m"));
        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        it.next();
        boolean threw = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        check(threw, "remove should throw UnsupportedOperationException");
        check(src.size() == 1, "source collection should be unchanged");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
