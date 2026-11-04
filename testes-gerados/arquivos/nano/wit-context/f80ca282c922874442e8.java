/* @test    @summary Test for WeakHashMap.next() method to observe behavior and exception handling    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.Map;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextMethodNormal();
        testNextMethodConcurrentModification();
        testNextMethodNoSuchElement();
    }

    private static void testNextMethodNormal() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        WeakHashMap.EntryIterator<String, String> iterator = getEntryIterator(map);
        if (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("Normal next(): " + entry);
        } else {
            System.out.println("Iterator has no next element in normal case.");
        }
    }

    private static void testNextMethodConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        WeakHashMap.EntryIterator<String, String> iterator = getEntryIterator(map);
        map.put("key2", "value2"); // modify during iteration
        try {
            iterator.next();
            System.out.println("No ConcurrentModificationException thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException.");
        }
    }

    private static void testNextMethodNoSuchElement() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        WeakHashMap.EntryIterator<String, String> iterator = getEntryIterator(map);
        try {
            while (iterator.hasNext()) {
                iterator.next();
            }
            // Now iterator is exhausted
            iterator.next(); // should throw NoSuchElementException
            System.out.println("No exception thrown on exhausted iterator.");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException.");
        }
    }

    // Helper method to access the package-private EntryIterator
    private static WeakHashMap.EntryIterator<String, String> getEntryIterator(WeakHashMap<String, String> map) {
        try {
            java.lang.reflect.Field field = WeakHashMap.class.getDeclaredField("entrySet");
            field.setAccessible(true);
            Object entrySet = field.get(map);
            java.lang.reflect.Method method = entrySet.getClass().getDeclaredMethod("iterator");
            method.setAccessible(true);
            Object iterator = method.invoke(entrySet);
            return (WeakHashMap.EntryIterator<String, String>) iterator;
        } catch (Exception e) {
            throw new RuntimeException("Failed to access EntryIterator", e);
        }
    }
}
