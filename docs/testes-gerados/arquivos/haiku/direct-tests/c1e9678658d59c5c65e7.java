/*
 * @test
 * @summary Verifies that forEachRemaining on a Collections wrapper iterator visits exactly the remaining elements
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testVisitsOnlyRemainingElements();
        testEmptyCollectionVisitsNothing();
        testExhaustedIteratorVisitsNothing();
    }

    static void testVisitsOnlyRemainingElements() {
        List<String> src = new ArrayList<>();
        src.add("a");
        src.add("b");
        src.add("c");
        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        check(it.next().equals("a"), "first element should be a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 2, "expected 2 remaining elements but got " + seen.size());
        check(seen.get(0).equals("b"), "first remaining should be b");
        check(seen.get(1).equals("c"), "second remaining should be c");
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testEmptyCollectionVisitsNothing() {
        Iterator<String> it = Collections.unmodifiableCollection(new ArrayList<String>()).iterator();
        int[] count = new int[1];
        it.forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "consumer should not be invoked for empty collection");
    }

    static void testExhaustedIteratorVisitsNothing() {
        List<Integer> src = new ArrayList<>();
        src.add(1);
        Iterator<Integer> it = Collections.unmodifiableCollection(src).iterator();
        it.next();
        int[] count = new int[1];
        it.forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "consumer should not be invoked when no elements remain");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
