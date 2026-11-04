/*
 * @test
 * @summary Test TreeMap.remove() method for correct exception and behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithLastReturned();
    }

    static void testRemoveWithoutLastReturned() {
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

    static void testRemoveWithLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<java.util.Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            iterator.next();
            try {
                // Accessing remove() method of TreeMap directly, which is not part of iterator
                // To invoke remove() on TreeMap, we need to call map.remove() after lastReturned is set
                // But in this test, we simulate calling remove() without lastReturned set
                // So, we call remove() directly, which should throw IllegalStateException
                map.remove();
                System.out.println("FAIL: Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                System.out.println("PASS: Caught expected IllegalStateException");
            } catch (Exception e) {
                System.out.println("FAIL: Caught unexpected exception: " + e);
            }
        } else {
            System.out.println("FAIL: Iterator has no elements to test");
        }
    }
}
