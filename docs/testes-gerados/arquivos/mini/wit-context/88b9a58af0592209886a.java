/*
 * @test
 * @summary Test java.util.Hashtable.Enumerator.next() for ConcurrentModificationException and normal behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ConcurrentModificationException;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNextThrowsConcurrentModificationException();
        testNextReturnsNextElement();
    }

    static void testNextThrowsConcurrentModificationException() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        Enumeration<String> e = table.keys();

        // Modify the table to change modCount after creating the Enumeration
        table.put("key2", "value2");

        try {
            e.nextElement(); // Should throw ConcurrentModificationException because modCount changed
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException ex) {
            // Expected exception
        }
    }

    static void testNextReturnsNextElement() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("a", "1");
        table.put("b", "2");

        Enumeration<String> e = table.keys();

        // Iterate without modification, should not throw
        int count = 0;
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            if (!table.containsKey(key)) {
                throw new AssertionError("Returned key not in hashtable: " + key);
            }
            count++;
        }

        if (count != 2) {
            throw new AssertionError("Expected 2 elements, got " + count);
        }
    }
}
