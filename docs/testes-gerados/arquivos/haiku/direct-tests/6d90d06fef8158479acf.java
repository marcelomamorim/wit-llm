/*
 * @test
 * @summary Checks observable retainAll behavior of Collections wrapper collections
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRetainAllRemovesNonMatchingElements() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> sync = Collections.synchronizedCollection(base);
        boolean changed = sync.retainAll(Arrays.asList("b", "c", "x"));
        check(changed, "retainAll should report a change");
        check(base.equals(Arrays.asList("b", "c")), "unexpected contents: " + base);
    }

    static void testRetainAllWithSupersetReturnsFalse() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> sync = Collections.synchronizedCollection(base);
        boolean changed = sync.retainAll(Arrays.asList("a", "b", "z"));
        check(!changed, "retainAll should report no change");
        check(base.equals(Arrays.asList("a", "b")), "contents must be unchanged: " + base);
    }

    static void testRetainAllWithEmptyArgumentClearsCollection() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> sync = Collections.synchronizedCollection(base);
        boolean changed = sync.retainAll(Collections.<String>emptyList());
        check(changed, "retainAll with empty collection should report a change");
        check(base.isEmpty(), "collection should be empty");
    }

    static void testUnmodifiableRetainAllThrows() {
        Collection<String> ro = Collections.unmodifiableCollection(
                new ArrayList<>(Arrays.asList("a")));
        boolean thrown = false;
        try {
            ro.retainAll(Arrays.asList("a"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "unmodifiable retainAll must throw UnsupportedOperationException");
    }

    public static void main(String[] args) {
        testRetainAllRemovesNonMatchingElements();
        testRetainAllWithSupersetReturnsFalse();
        testRetainAllWithEmptyArgumentClearsCollection();
        testUnmodifiableRetainAllThrows();
    }
}
