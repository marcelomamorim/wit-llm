/*
 * @test
 * @summary Test TreeMap.forEachRemaining(Consumer) method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingBasic();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingBasic() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        // Use the entrySet iterator's forEachRemaining indirectly by iterating values
        // but since we only have the TreeMap, we simulate the call by iterating values
        // and calling forEachRemaining on the iterator.
        // However, the method is on TreeMap.Entry iterator, not on TreeMap itself.
        // So we test the public TreeMap values().iterator().forEachRemaining.

        // This test is adapted to test the observable behavior of forEachRemaining on values iterator
        java.util.Iterator<String> it = map.values().iterator();
        it.forEachRemaining(action);

        String result = sb.toString();
        if (!result.equals("one,two,three,") && !result.equals("one,three,two,") && !result.equals("two,one,three,") && !result.equals("two,three,one,") && !result.equals("three,one,two,") && !result.equals("three,two,one,")) {
            throw new AssertionError("Unexpected iteration order: " + result);
        }
    }

    static void testForEachRemainingNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        java.util.Iterator<String> it = map.values().iterator();
        boolean npeThrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        java.util.Iterator<String> it = map.values().iterator();

        // Modify map structurally after iterator creation
        map.put(3, "three");

        boolean cmeThrown = false;
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException after structural modification");
        }
    }
}
