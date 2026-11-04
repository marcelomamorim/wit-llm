/* @test    @summary Tests for java.util.Hashtable methods    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextMethodNormal();
        testNextMethodConcurrentModification();
    }

    private static void testNextMethodNormal() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        java.util.Enumeration<String> enumeration = table.keys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = table.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    private static void testNextMethodConcurrentModification() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        java.util.Enumeration<String> enumeration = table.keys();
        table.put("key3", "value3"); // Modifies the hashtable after enumeration starts
        try {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                System.out.println("Key: " + key);
            }
            System.out.println("No ConcurrentModificationException thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException.");
        }
    }
}
