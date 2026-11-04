/*
 * @test
 * @summary Test java.util.ResourceBundle.getFormats(String) for null input and default return
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return null;
            }
            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
        };

        // Test that getFormats throws NullPointerException when baseName is null
        try {
            rb.getFormats(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that getFormats returns the default formats list for a non-null baseName
        List<String> formats = rb.getFormats("anyBaseName");
        if (formats == null) {
            throw new RuntimeException("getFormats returned null for non-null baseName");
        }
        if (formats.isEmpty()) {
            throw new RuntimeException("getFormats returned empty list, expected default formats");
        }
        // Check that the returned list contains "java.class" and "java.properties" as typical defaults
        boolean containsClass = false;
        boolean containsProperties = false;
        for (String f : formats) {
            if ("java.class".equals(f)) {
                containsClass = true;
            } else if ("java.properties".equals(f)) {
                containsProperties = true;
            }
        }
        if (!containsClass || !containsProperties) {
            throw new RuntimeException("getFormats default list missing expected format strings");
        }
    }
}
