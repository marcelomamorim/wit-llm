/*
 * @test
 * @summary Verify forEach behavior of Collections wrapper views (unmodifiable and synchronized)
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testUnmodifiableVisitsAllInOrder();
        testSynchronizedVisitsAllInOrder();
        testUnmodifiableNullActionThrowsNpe();
        testSynchronizedNullActionThrowsNpe();
        testEmptyCollectionInvokesNothing();
    }

    static void testUnmodifiableVisitsAllInOrder() {
        List<String> src = new ArrayList<>();
        src.add("a");
        src.add("b");
        src.add("c");
        Collection<String> view = Collections.unmodifiableCollection(src);
        List<String> seen = new ArrayList<>();
        view.forEach(x -> seen.add(x));
        if (!seen.equals(src)) {
            throw new RuntimeException("unmodifiable forEach visited " + seen);
        }
    }

    static void testSynchronizedVisitsAllInOrder() {
        List<String> src = new ArrayList<>();
        src.add("x");
        src.add("y");
        Collection<String> view = Collections.synchronizedCollection(src);
        List<String> seen = new ArrayList<>();
        view.forEach(x -> seen.add(x));
        if (!seen.equals(src)) {
            throw new RuntimeException("synchronized forEach visited " + seen);
        }
    }

    static void testUnmodifiableNullActionThrowsNpe() {
        Collection<String> view = Collections.unmodifiableCollection(new ArrayList<String>());
        Consumer<String> nothing = null;
        try {
            view.forEach(nothing);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testSynchronizedNullActionThrowsNpe() {
        List<String> src = new ArrayList<>();
        src.add("z");
        Collection<String> view = Collections.synchronizedCollection(src);
        Consumer<String> nothing = null;
        try {
            view.forEach(nothing);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testEmptyCollectionInvokesNothing() {
        Collection<String> view = Collections.unmodifiableCollection(new ArrayList<String>());
        int[] count = new int[1];
        view.forEach(x -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("action invoked " + count[0] + " times on empty collection");
        }
    }
}
