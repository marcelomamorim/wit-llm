/*
 * @test
 * @summary Test sun.text.IntHashtable.get(int) method behavior including default and absent keys
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        IntHashtable table = new IntHashtable();

        // Initially empty
        if (!table.isEmpty()) {
            throw new RuntimeException("Expected empty IntHashtable initially");
        }

        // Default value should be returned for absent keys
        int defaultValue = table.getDefaultValue();
        int absentKey = Integer.MAX_VALUE; // a key unlikely to be present
        int val = table.get(absentKey);
        if (val != defaultValue) {
            throw new RuntimeException("Expected default value for absent key");
        }

        // Put a key-value pair and get it back
        int key = 12345;
        int value = 67890;
        table.put(key, value);
        if (table.isEmpty()) {
            throw new RuntimeException("Expected non-empty after put");
        }
        int got = table.get(key);
        if (got != value) {
            throw new RuntimeException("Expected to get the value just put");
        }

        // Overwrite existing key with new value
        int newValue = 54321;
        table.put(key, newValue);
        got = table.get(key);
        if (got != newValue) {
            throw new RuntimeException("Expected updated value after put");
        }

        // Remove the key and check get returns default
        table.remove(key);
        got = table.get(key);
        if (got != defaultValue) {
            throw new RuntimeException("Expected default value after remove");
        }

        // Change default value and verify get returns new default for absent keys
        int newDefault = -9999;
        table.setDefaultValue(newDefault);
        if (table.getDefaultValue() != newDefault) {
            throw new RuntimeException("Expected updated default value");
        }
        got = table.get(absentKey);
        if (got != newDefault) {
            throw new RuntimeException("Expected new default value for absent key after setDefaultValue");
        }

        // Test keys at boundary: key <= DELETED (which is -1) should not be used
        // According to source, keys must be > DELETED (-1), so test get with key = -1 returns default
        int boundaryKey = -1;
        got = table.get(boundaryKey);
        if (got != newDefault) {
            throw new RuntimeException("Expected default value for boundary key <= DELETED");
        }

        // Test that size and count behave as expected
        IntHashtable emptyTable = new IntHashtable();
        if (emptyTable.size() != 0) {
            throw new RuntimeException("Expected size 0 for new IntHashtable");
        }
        emptyTable.put(1, 1);
        if (emptyTable.size() != 1) {
            throw new RuntimeException("Expected size 1 after one put");
        }
        emptyTable.remove(1);
        if (emptyTable.size() != 0) {
            throw new RuntimeException("Expected size 0 after remove");
        }

        System.out.println("All IntHashtable.get(int) tests passed.");
    }
}
