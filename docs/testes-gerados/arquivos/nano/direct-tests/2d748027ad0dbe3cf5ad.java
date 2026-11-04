/* @test    @summary Test the replace method of Hashtable for key present and absent cases    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "existingKey";
        String initialValue = "initial";
        String newValue = "replaced";
        table.put(key, initialValue);
        String oldValue = table.replace(key, newValue);
        if (!"initial".equals(oldValue)) {
            throw new AssertionError("Expected old value 'initial', but got: " + oldValue);
        }
        String currentValue = table.get(key);
        if (!"replaced".equals(currentValue)) {
            throw new AssertionError("Expected current value 'replaced', but got: " + currentValue);
        }
    }

    private static void testReplaceNonExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "absentKey";
        String value = "value";
        String result = table.replace(key, value);
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key, but got: " + result);
        }
        String currentValue = table.get(key);
        if (currentValue != null) {
            throw new AssertionError("Expected null for absent key, but got: " + currentValue);
        }
    }
}
