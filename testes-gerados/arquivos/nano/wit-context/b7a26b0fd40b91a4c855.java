/* @test    @summary Test for Hashtable.remove(Object, Object) method to verify behavior with null value    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullValue();
        testRemoveWithExistingKeyAndValue();
        testRemoveWithNonExistingKey();
        testRemoveWithNullKey();
    }

    private static void testRemoveWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        try {
            boolean result = table.remove("key1", null);
            // Expect NullPointerException due to requireNonNull(value)
            System.out.println("testRemoveWithNullValue: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testRemoveWithNullValue: Passed");
        } catch (Exception e) {
            System.out.println("testRemoveWithNullValue: Failed - wrong exception: " + e);
        }
    }

    private static void testRemoveWithExistingKeyAndValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key2", "value2");
        boolean result = table.remove("key2", "value2");
        if (result && !table.containsKey("key2")) {
            System.out.println("testRemoveWithExistingKeyAndValue: Passed");
        } else {
            System.out.println("testRemoveWithExistingKeyAndValue: Failed");
        }
    }

    private static void testRemoveWithNonExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key3", "value3");
        boolean result = table.remove("nonexistent", "value3");
        if (!result && table.containsKey("key3")) {
            System.out.println("testRemoveWithNonExistingKey: Passed");
        } else {
            System.out.println("testRemoveWithNonExistingKey: Failed");
        }
    }

    private static void testRemoveWithNullKey() {
        Hashtable<String, String> table = new Hashtable<>();
        try {
            table.remove(null, "value");
            System.out.println("testRemoveWithNullKey: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testRemoveWithNullKey: Passed");
        } catch (Exception e) {
            System.out.println("testRemoveWithNullKey: Failed - wrong exception: " + e);
        }
    }
}
