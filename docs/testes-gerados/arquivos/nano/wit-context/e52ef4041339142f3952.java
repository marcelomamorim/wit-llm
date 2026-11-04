/* @test    @summary Test for Hashtable.nextElement() method to verify exception handling and normal behavior    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextElementNormal();
        testNextElementThrows();
    }

    static void testNextElementNormal() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        // Create an enumerator
        java.util.Enumeration<String> e = table.keys();
        // Advance to first element
        if (e.hasMoreElements()) {
            String key = e.nextElement();
            assert key != null : "Expected non-null key";
        }
        // Advance to second element
        if (e.hasMoreElements()) {
            String key = e.nextElement();
            assert key != null : "Expected non-null key";
        }
    }

    static void testNextElementThrows() {
        Hashtable<String, String> table = new Hashtable<>();
        java.util.Enumeration<String> e = table.keys();
        // No elements, should throw NoSuchElementException
        boolean exceptionThrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException when calling nextElement() on empty enumeration";
    }
}
