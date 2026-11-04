/*
 * @test
 * @summary Behavioral tests for Collections removeAll through public wrapper APIs
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testUnmodifiableCollectionRemoveAllThrows();
        testSynchronizedCollectionRemoveAll();
        testSynchronizedCollectionRemoveAllAbsentReturnsFalse();
        testSetFromMapRemoveAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnmodifiableCollectionRemoveAllThrows() {
        Collection<Integer> c = Collections.unmodifiableCollection(
                new ArrayList<Integer>(Arrays.asList(1, 2)));
        boolean thrown = false;
        try {
            c.removeAll(Arrays.asList(1));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "removeAll on unmodifiable collection must throw UnsupportedOperationException");
        check(c.size() == 2, "unmodifiable collection must not change");
    }

    static void testSynchronizedCollectionRemoveAll() {
        List<Integer> backing = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        Collection<Integer> sync = Collections.synchronizedCollection(backing);
        boolean changed = sync.removeAll(Arrays.asList(2, 3));
        check(changed, "removeAll must return true when elements are removed");
        check(backing.size() == 1, "expected size 1 after removeAll");
        check(backing.contains(1), "element 1 must remain");
        check(!backing.contains(2) && !backing.contains(3), "removed elements must be gone");
    }

    static void testSynchronizedCollectionRemoveAllAbsentReturnsFalse() {
        List<Integer> backing = new ArrayList<Integer>(Arrays.asList(1, 2));
        Collection<Integer> sync = Collections.synchronizedCollection(backing);
        boolean changed = sync.removeAll(Arrays.asList(9));
        check(!changed, "removeAll with no matching elements must return false");
        check(backing.size() == 2, "collection must be unchanged");
    }

    static void testSetFromMapRemoveAll() {
        Set<String> set = Collections.newSetFromMap(new HashMap<String, Boolean>());
        set.add("a");
        set.add("b");
        set.add("c");
        boolean changed = set.removeAll(Arrays.asList("a", "z"));
        check(changed, "removeAll must return true when an element is removed");
        check(set.size() == 2, "expected size 2 after removeAll");
        check(!set.contains("a"), "'a' must be removed");
        check(set.contains("b") && set.contains("c"), "'b' and 'c' must remain");
    }
}
