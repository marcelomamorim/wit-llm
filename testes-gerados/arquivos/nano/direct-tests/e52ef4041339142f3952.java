/* @test    @summary Test for Hashtable.nextElement() method behavior and exception handling    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextElementReturnsCorrectType();
        testNextElementThrowsWhenNoMoreElements();
    }

    static void testNextElementReturnsCorrectType() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        java.util.Enumeration<?> enumeration = table.elements();
        Object element1 = enumeration.nextElement();
        Object element2 = enumeration.nextElement();
        boolean condition = (element1.equals("value1") && element2.equals("value2")) ||
                            (element1.equals("value2") && element2.equals("value1"));
        if (!condition) {
            throw new AssertionError("Unexpected elements returned by nextElement()");
        }
    }

    static void testNextElementThrowsWhenNoMoreElements() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        java.util.Enumeration<?> enumeration = table.elements();
        enumeration.nextElement();
        try {
            enumeration.nextElement();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
