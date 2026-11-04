/*
 * @test
 * @summary Test java.util.ResourceBundle.getFormats(String) method behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        ResourceBundle rb = new ResourceBundle() {
            private final List<String> formats = super.getFormats("base");
            @Override
            protected Object handleGetObject(String key) {
                return null;
            }
            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
            @Override
            public List<String> getFormats(String baseName) {
                return super.getFormats(baseName);
            }
        };

        // Test 1: getFormats returns the expected list (non-null)
        List<String> formats = rb.getFormats("base");
        if (formats == null) {
            throw new AssertionError("getFormats returned null for non-null baseName");
        }

        // Test 2: getFormats throws NullPointerException when baseName is null
        boolean npeThrown = false;
        try {
            rb.getFormats(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("getFormats did not throw NullPointerException when baseName is null");
        }
    }
}
