/* @test    @summary Test for Hashtable.replace method to verify exception paths and behavior    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceNullOldValue();
        testReplaceNullNewValue();
    }

    private static void testReplaceNullOldValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        try {
            table.replace("key", null, "newValue");
            System.out.println("FAIL: Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null oldValue");
        }
    }

    private static void testReplaceNullNewValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        try {
            table.replace("key", "value", null);
            System.out.println("FAIL: Expected NullPointerException for null newValue");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null newValue");
        }
    }
}
