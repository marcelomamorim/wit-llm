/* @test    @summary Test removeAll method of CopyOnWriteArrayList for correct removal behavior    @run main CopyOnWriteArrayListWitupTest */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithExistingElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> toRemove = Arrays.asList(2, 4);
        boolean changed = list.removeAll(toRemove);
        assert changed : "Expected list to be changed";
        assert list.size() == 3 : "Expected size 3, but got " + list.size();
        assert list.containsAll(Arrays.asList(1, 3, 5)) : "List does not contain expected elements";
    }

    static void testRemoveAllWithNoMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = list.removeAll(toRemove);
        assert !changed : "Expected list to be unchanged";
        assert list.size() == 3 : "Expected size 3, but got " + list.size();
        assert list.containsAll(Arrays.asList("a", "b", "c")) : "List does not contain expected elements";
    }

    static void testRemoveAllWithEmptyCollection() {
        CopyOnWriteArrayList<Double> list = new CopyOnWriteArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        Collection<Double> toRemove = Arrays.asList();
        boolean changed = list.removeAll(toRemove);
        assert !changed : "Expected list to be unchanged";
        assert list.size() == 3 : "Expected size 3, but got " + list.size();
        assert list.containsAll(Arrays.asList(1.1, 2.2, 3.3)) : "List does not contain expected elements";
    }

    static void testRemoveAllWithNullCollection() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        try {
            list.removeAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
