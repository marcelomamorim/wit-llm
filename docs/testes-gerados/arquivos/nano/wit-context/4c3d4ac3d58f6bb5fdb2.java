/* @test    @summary Test HashMap.next() method for exception paths    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextWithConcurrentModification();
        testNextWithNoSuchElement();
    }

    private static void testNextWithConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        HashMap.EntryIterator iterator = (HashMap.EntryIterator) map.entrySet().iterator();
        try {
            // Simulate concurrent modification
            map.put("c", "3");
            iterator.next();
            System.out.println("Expected ConcurrentModificationException was not thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testNextWithNoSuchElement() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        HashMap.EntryIterator iterator = (HashMap.EntryIterator) map.entrySet().iterator();
        try {
            // Exhaust iterator
            while (iterator.hasNext()) {
                iterator.next();
            }
            // Next call should throw NoSuchElementException
            iterator.next();
            System.out.println("Expected NoSuchElementException was not thrown.");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
