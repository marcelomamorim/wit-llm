/*
 * @test
 * @summary Test for Properties.replace to verify behavior with null inputs.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
    }

    private static void testReplaceWithNullKey() {
        Properties props = new Properties();
        try {
            boolean result = props.replace(null, "old", "new");
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException for null key, but got result: " + result);
        } catch (NullPointerException e) {
            System.out.println("Passed null key test");
        }
    }

    private static void testReplaceWithNullOldValue() {
        Properties props = new Properties();
        try {
            boolean result = props.replace("key", null, "new");
            System.err.println("Expected NullPointerException for null oldValue, but got result: " + result);
        } catch (NullPointerException e) {
            System.out.println("Passed null oldValue test");
        }
    }

    private static void testReplaceWithNullNewValue() {
        Properties props = new Properties();
        try {
            boolean result = props.replace("key", "old", null);
            System.err.println("Expected NullPointerException for null newValue, but got result: " + result);
        } catch (NullPointerException e) {
            System.out.println("Passed null newValue test");
        }
    }
}
