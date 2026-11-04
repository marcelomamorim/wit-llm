/*
 * @test
 * @summary Test java.util.Hashtable Enumerator nextElement method behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNextElementReturnsKeys();
        testNextElementReturnsValues();
        testNextElementThrowsWhenEmpty();
    }

    static void testNextElementReturnsKeys() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        Enumeration<String> keys = ht.keys();

        // Collect keys returned by nextElement
        String k1 = keys.nextElement();
        String k2 = keys.nextElement();

        // The keys returned must be one of the inserted keys
        if (!("key1".equals(k1) || "key2".equals(k1))) {
            throw new AssertionError("Unexpected key returned: " + k1);
        }
        if (!("key1".equals(k2) || "key2".equals(k2))) {
            throw new AssertionError("Unexpected key returned: " + k2);
        }

        // After two elements, no more elements should be present
        boolean threw = false;
        try {
            keys.nextElement();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException after all keys consumed");
        }
    }

    static void testNextElementReturnsValues() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        Enumeration<String> values = ht.elements();

        // Collect values returned by nextElement
        String v1 = values.nextElement();
        String v2 = values.nextElement();

        // The values returned must be one of the inserted values
        if (!("value1".equals(v1) || "value2".equals(v1))) {
            throw new AssertionError("Unexpected value returned: " + v1);
        }
        if (!("value1".equals(v2) || "value2".equals(v2))) {
            throw new AssertionError("Unexpected value returned: " + v2);
        }

        // After two elements, no more elements should be present
        boolean threw = false;
        try {
            values.nextElement();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException after all values consumed");
        }
    }

    static void testNextElementThrowsWhenEmpty() {
        Hashtable<String, String> ht = new Hashtable<>();
        Enumeration<String> keys = ht.keys();

        boolean threw = false;
        try {
            keys.nextElement();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling nextElement on empty hashtable");
        }
    }
}
