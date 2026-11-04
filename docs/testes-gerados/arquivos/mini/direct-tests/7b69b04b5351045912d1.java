/*
 * @test
 * @summary Test sun.text.IntHashtable.put(int key, int value) method
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {
    public static void main(String[] args) {
        IntHashtable table = new IntHashtable();

        // Initially empty
        if (!table.isEmpty()) {
            throw new RuntimeException("Expected empty IntHashtable initially");
        }

        // Put a key-value pair
        table.put(10, 100);
        if (table.isEmpty()) {
            throw new RuntimeException("Expected non-empty after put");
        }
        if (table.size() != 1) {
            throw new RuntimeException("Expected size 1 after one put");
        }
        if (table.get(10) != 100) {
            throw new RuntimeException("Expected value 100 for key 10");
        }

        // Put another key-value pair
        table.put(20, 200);
        if (table.size() != 2) {
            throw new RuntimeException("Expected size 2 after two puts");
        }
        if (table.get(20) != 200) {
            throw new RuntimeException("Expected value 200 for key 20");
        }

        // Overwrite existing key
        table.put(10, 101);
        if (table.size() != 2) {
            throw new RuntimeException("Expected size 2 after overwrite put");
        }
        if (table.get(10) != 101) {
            throw new RuntimeException("Expected updated value 101 for key 10");
        }

        // Put keys that may trigger rehash (try to fill beyond highWaterMark)
        int initialSize = table.size();
        for (int i = 30; i < 1000; i += 10) {
            table.put(i, i + 1000);
        }
        if (table.size() <= initialSize) {
            throw new RuntimeException("Expected size to increase after multiple puts");
        }

        // Check some values after many puts
        if (table.get(30) != 1030) {
            throw new RuntimeException("Expected value 1030 for key 30");
        }
        if (table.get(990) != 1990) {
            throw new RuntimeException("Expected value 1990 for key 990");
        }

        // Put a key with value equal to defaultValue (should store normally)
        int defaultValue = table.getDefaultValue();
        table.put(5000, defaultValue);
        if (table.get(5000) != defaultValue) {
            throw new RuntimeException("Expected defaultValue for key 5000");
        }

        System.out.println("PASS");
    }
}
