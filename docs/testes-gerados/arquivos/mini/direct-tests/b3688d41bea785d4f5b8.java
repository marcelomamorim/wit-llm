/*
 * @test
 * @summary Test TreeMap remove() method calls removeDescending() without error
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        // Create a TreeMap instance
        TreeMap<Integer, String> map = new TreeMap<>();
        // Put some entries
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // Create an instance of the inner class that has remove() method
        // Since the method is from the excerpt and no class name given, assume it is from an iterator or similar
        // We cannot instantiate inner classes without context, so test remove() indirectly if possible

        // Since the method remove() calls removeDescending(), and no exception or return,
        // we test that remove() can be called without exception on a descendingKeySet iterator

        java.util.Iterator<Integer> descendingIterator = map.descendingKeySet().iterator();

        // The remove() method is from Iterator interface, so call remove() after next()
        if (descendingIterator.hasNext()) {
            descendingIterator.next();
            descendingIterator.remove();
        }

        // Verify the size decreased by one
        if (map.size() != 2) {
            throw new AssertionError("Expected size 2 after remove, got " + map.size());
        }

        // Call remove() again without next() should throw IllegalStateException
        boolean threw = false;
        try {
            descendingIterator.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on remove() without next()");
        }
    }
}
