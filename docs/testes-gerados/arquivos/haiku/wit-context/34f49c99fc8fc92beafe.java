/*
 * @test
 * @summary Observable behavior of removeIf through public Collections wrappers (synchronized and unmodifiable)
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSynchronizedRemoveIfRemovesMatches();
        testSynchronizedRemoveIfNullFilterThrowsNpe();
        testUnmodifiableRemoveIfThrowsUnsupported();
    }

    static void testSynchronizedRemoveIfRemovesMatches() {
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Collection<Integer> c = Collections.synchronizedCollection(base);
        boolean changed = c.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should report a change");
        check(base.equals(Arrays.asList(1, 3, 5)), "unexpected contents: " + base);
    }

    static void testSynchronizedRemoveIfNullFilterThrowsNpe() {
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Collections.synchronizedCollection(base);
        boolean thrown = false;
        try {
            c.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null filter must throw NullPointerException");
        check(base.equals(Arrays.asList(1, 2, 3)), "collection must not change on null filter");
    }

    static void testUnmodifiableRemoveIfThrowsUnsupported() {
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Collections.unmodifiableCollection(base);
        boolean thrown = false;
        try {
            c.removeIf(x -> true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "unmodifiable removeIf must throw UnsupportedOperationException");
        check(base.equals(Arrays.asList(1, 2, 3)), "backing list must not change");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
