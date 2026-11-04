/*
 * @test
 * @summary Test java.util.Hashtable.nextElement() for expected behavior and exception
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNextElementReturnsCorrectValues();
        testNextElementThrowsNoSuchElementException();
        System.out.println("All tests passed.");
    }

    private static void testNextElementReturnsCorrectValues() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        Enumeration<String> keys = ht.keys();
        boolean foundKey1 = false;
        boolean foundKey2 = false;

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if ("key1".equals(key)) {
                foundKey1 = true;
            } else if ("key2".equals(key)) {
                foundKey2 = true;
            } else {
                throw new AssertionError("Unexpected key: " + key);
            }
        }

        if (!foundKey1 || !foundKey2) {
            throw new AssertionError("Expected keys not found");
        }
    }

    private static void testNextElementThrowsNoSuchElementException() {
        Hashtable<String, String> ht = new Hashtable<>();
        Enumeration<String> keys = ht.keys();

        if (keys.hasMoreElements()) {
            throw new AssertionError("Expected no elements in empty hashtable");
        }

        try {
            keys.nextElement();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
