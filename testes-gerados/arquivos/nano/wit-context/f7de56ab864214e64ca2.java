/*
 * @test
 * @summary Test for ResourceBundle.containsKey handling null key
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithValidKey();
    }

    public static void testContainsKeyWithNull() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ListResourceBundle");
        boolean threwException = false;
        try {
            rb.containsKey(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when calling containsKey with null");
        }
    }

    public static void testContainsKeyWithValidKey() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ListResourceBundle");
        // Use a known key from the resource bundle, e.g., "list" in ListResourceBundle
        boolean result = rb.containsKey("list");
        if (!result) {
            throw new AssertionError("Expected containsKey to return true for key 'list'");
        }
    }
}
