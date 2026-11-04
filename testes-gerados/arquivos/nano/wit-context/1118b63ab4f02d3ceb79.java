/*
 * @test
 * @summary Test for Hashtable.setValue(V value) method, focusing on null input.
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testSetValueWithNull();
        testSetValueWithNonNull();
    }

    private static void testSetValueWithNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        try {
            table.setValue(null);
            System.out.println("FAIL: Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null value");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testSetValueWithNonNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        String oldValue = table.setValue("newValue");
        if ("value".equals(oldValue) && "newValue".equals(table.get("key"))) {
            System.out.println("PASS: setValue with non-null value updates correctly");
        } else {
            System.out.println("FAIL: setValue with non-null value did not behave as expected");
        }
    }
}
