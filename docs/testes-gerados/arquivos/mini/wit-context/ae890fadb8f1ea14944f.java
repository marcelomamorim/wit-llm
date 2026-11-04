/*
 * @test
 * @summary Test java.util.Properties.remove(Object key, Object value) for expected behavior and NPE on null key
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        Object key = "key1";
        Object value = "value1";

        // Put a property to remove
        props.setProperty((String) key, (String) value);

        // Remove with correct key and value - should return true
        boolean removed = props.remove(key, value);
        if (!removed) {
            throw new AssertionError("Expected remove to return true when key and value match");
        }

        // Remove again - should return false as entry no longer exists
        boolean removedAgain = props.remove(key, value);
        if (removedAgain) {
            throw new AssertionError("Expected remove to return false when entry does not exist");
        }

        // Remove with null value but valid key - should return false (no match)
        props.setProperty((String) key, (String) value);
        boolean removedNullValue = props.remove(key, null);
        if (removedNullValue) {
            throw new AssertionError("Expected remove to return false when value does not match");
        }

        // Remove with null key - should throw NullPointerException
        boolean npeThrown = false;
        try {
            props.remove(null, value);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when key is null");
        }

        // Remove with null key and null value - should throw NullPointerException
        npeThrown = false;
        try {
            props.remove(null, null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when key is null even if value is null");
        }

        System.out.println("Properties.remove(Object,Object) tests passed.");
    }
}
