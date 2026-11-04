/*
 * @test
 * @summary Verifies Collections.unmodifiableCollection iterator forEachRemaining delegates to the backing iterator
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testVisitsRemainingElementsOnly();
        testVisitsAllElementsWhenNotStarted();
        testEmptyCollectionVisitsNothing();
        testRemoveStillUnsupportedAfterForEachRemaining();
    }

    static void testVisitsRemainingElementsOnly() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        backing.add("c");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        Iterator<String> it = view.iterator();
        Object first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("expected first element a but was " + first);
        }
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!seen.equals(List.of("b", "c"))) {
            throw new AssertionError("unexpected remaining elements: " + seen);
        }
        if (it.hasNext()) {
            throw new AssertionError("iterator should be exhausted");
        }
    }

    static void testVisitsAllElementsWhenNotStarted() {
        List<Integer> backing = new ArrayList<>();
        backing.add(1);
        backing.add(2);
        backing.add(3);
        Iterator<Integer> it = Collections.unmodifiableCollection(backing).iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!seen.equals(List.of(1, 2, 3))) {
            throw new AssertionError("unexpected elements: " + seen);
        }
    }

    static void testEmptyCollectionVisitsNothing() {
        Iterator<String> it = Collections.unmodifiableCollection(new ArrayList<String>()).iterator();
        int[] count = new int[1];
        it.forEachRemaining(s -> count[0]++);
        if (count[0] != 0) {
            throw new AssertionError("consumer invoked " + count[0] + " times for empty collection");
        }
    }

    static void testRemoveStillUnsupportedAfterForEachRemaining() {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        Iterator<String> it = Collections.unmodifiableCollection(backing).iterator();
        it.forEachRemaining(s -> { });
        try {
            it.remove();
            throw new AssertionError("remove should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
