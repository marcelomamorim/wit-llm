/*
 * @test
 * @summary Test WeakHashMap.HashIterator.remove() exceptional and normal behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.lang.IllegalStateException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalStateException();
        testRemoveWithConcurrentModificationThrowsConcurrentModificationException();
        testRemoveRemovesEntryAndResetsState();
    }

    private static void testRemoveWithoutNextThrowsIllegalStateException() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        map.put(new Object(), new Object());
        Iterator<Object> it = map.keySet().iterator();
        try {
            it.remove();
            throw new AssertionError("Expected IllegalStateException when remove() called before next()");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testRemoveWithConcurrentModificationThrowsConcurrentModificationException() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, new Object());
        Iterator<Object> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        it.next();
        // Modify map outside iterator to cause concurrent modification
        map.put(new Object(), new Object());
        try {
            it.remove();
            throw new AssertionError("Expected ConcurrentModificationException after concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testRemoveRemovesEntryAndResetsState() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "value");
        Iterator<Object> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        Object iterKey = it.next();
        if (iterKey == null) {
            throw new AssertionError("Iterator returned null key unexpectedly");
        }
        it.remove();
        if (map.containsKey(iterKey)) {
            throw new AssertionError("Key should have been removed by iterator.remove()");
        }
        // Calling remove again without next should throw IllegalStateException
        try {
            it.remove();
            throw new AssertionError("Expected IllegalStateException on second remove() without next()");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
