/* @test    @summary Test HashMap.remove() method for exception paths and behavior    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testRemoveNullCurrent();
        testRemoveConcurrentModification();
    }

    private static void testRemoveNullCurrent() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String>.HashIterator iterator = map.new HashIterator();
        try {
            iterator.remove();
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        HashMap<String, String>.HashIterator iterator = map.new HashIterator();
        // Advance iterator to set expectedModCount
        iterator.next();
        // Modify map to cause modCount mismatch
        map.put("key2", "value2");
        try {
            iterator.remove();
            System.out.println("FAIL: Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("PASS: Caught expected ConcurrentModificationException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
