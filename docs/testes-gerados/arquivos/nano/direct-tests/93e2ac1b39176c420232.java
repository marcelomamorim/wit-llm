/*
 * @test
 * @summary Test the replace method of java.util.Properties for correct behavior.
 * @run main PropertiesReplaceTest
 */

import java.util.Properties;

public class PropertiesReplaceTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
        testReplaceWithNullKey();
        testReplaceWithNullValues();
    }

    static void testReplaceExistingKey() {
        Properties props = new Properties();
        String key = "existingKey";
        String initialValue = "initial";
        String newValue = "new";
        props.setProperty(key, initialValue);
        boolean replaced = props.replace(key, initialValue, newValue);
        assert replaced : "Replace should succeed for existing key with matching oldValue";
        assert newValue.equals(props.getProperty(key)) : "Value should be updated to newValue";
    }

    static void testReplaceNonExistingKey() {
        Properties props = new Properties();
        boolean replaced = props.replace("nonExistingKey", "old", "new");
        assert !replaced : "Replace should fail for non-existing key";
    }

    static void testReplaceWithNullKey() {
        Properties props = new Properties();
        try {
            props.replace(null, "old", "new");
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceWithNullValues() {
        Properties props = new Properties();
        String key = "key";
        String value = "value";
        props.setProperty(key, value);
        boolean replacedNullOld = false;
        boolean replacedNullNew = false;
        try {
            replacedNullOld = props.replace(key, null, "newVal");
        } catch (NullPointerException e) {
            // depending on implementation, may throw
        }
        try {
            replacedNullNew = props.replace(key, value, null);
        } catch (NullPointerException e) {
            // depending on implementation, may throw
        }
        // The replace method returns false if oldValue does not match, so check accordingly
        // Since null is not equal to any string, replace should return false
        assert replacedNullOld == false : "Replacing with null oldValue should return false";
        assert replacedNullNew == false : "Replacing with null newValue should return false";
    }
}
