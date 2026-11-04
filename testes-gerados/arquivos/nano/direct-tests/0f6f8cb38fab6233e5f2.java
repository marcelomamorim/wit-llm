/* @test    @summary Test TreeMap.remove() method for correct exception and behavior    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithLastReturned();
    }

    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.remove();
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveWithLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        // Obtain iterator and move to first element
        java.util.Iterator<java.util.Map.Entry<Integer, String>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            it.next(); // move to first
            // Use reflection to access lastReturned field (not possible directly, so simulate)
            // Instead, we simulate by calling remove() after moving iterator
            try {
                it.remove();
                if (map.containsKey(1)) {
                    System.out.println("FAIL: Entry not removed after iterator.remove()");
                } else {
                    System.out.println("PASS: Entry removed successfully via iterator.remove()");
                }
            } catch (UnsupportedOperationException e) {
                System.out.println("FAIL: remove() not supported on iterator");
            } catch (IllegalStateException e) {
                System.out.println("FAIL: IllegalStateException thrown unexpectedly");
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception: " + e);
            }
        } else {
            System.out.println("FAIL: Iterator has no elements");
        }
    }
}
