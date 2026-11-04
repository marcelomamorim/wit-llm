/*
 * @test
 * @summary Verifies forEachRemaining of the unmodifiable collection iterator delegates to the backing iterator and rejects null actions
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemainingElementsAreVisitedInOrder();
        testEmptyIteratorVisitsNothing();
        testNullActionThrowsNullPointerException();
    }

    private static void testRemainingElementsAreVisitedInOrder() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        backing.add("c");
        Iterator<String> it = Collections.unmodifiableCollection(backing).iterator();
        it.next();
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        it.forEachRemaining(action);
        check(seen.equals(Arrays.asList("b", "c")), "expected remaining elements [b, c] but got " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    private static void testEmptyIteratorVisitsNothing() {
        List<String> backing = new ArrayList<>();
        Iterator<String> it = Collections.unmodifiableCollection(backing).iterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        it.forEachRemaining(action);
        check(seen.isEmpty(), "expected no elements visited but got " + seen);
    }

    private static void testNullActionThrowsNullPointerException() {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        Iterator<String> it = Collections.unmodifiableCollection(backing).iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
