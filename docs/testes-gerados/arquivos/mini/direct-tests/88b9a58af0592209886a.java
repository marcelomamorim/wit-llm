/*
 * @test
 * @summary Test java.util.Hashtable next() method for ConcurrentModificationException and normal iteration
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ConcurrentModificationException;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNextNormalIteration();
        testNextConcurrentModificationException();
    }

    private static void testNextNormalIteration() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");

        Enumeration<String> keys = table.keys();

        // Iterate over all keys using next() and check values
        int count = 0;
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = table.get(key);
            if (value == null) {
                throw new AssertionError("Value for key " + key + " should not be null");
            }
            count++;
        }

        if (count != 2) {
            throw new AssertionError("Expected 2 elements, got " + count);
        }
    }

    private static void testNextConcurrentModificationException() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");

        Enumeration<String> keys = table.keys();

        // Modify the table after getting the enumeration
        table.put("key2", "value2");

        try {
            // The next call to nextElement() should throw ConcurrentModificationException
            keys.nextElement();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
