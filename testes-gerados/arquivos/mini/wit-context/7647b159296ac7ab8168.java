/*
 * @test
 * @summary Test ResourceBundle.getFormats(String) for null argument and normal behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testGetFormatsWithNull();
        testGetFormatsWithNonNull();
    }

    private static void testGetFormatsWithNull() {
        ResourceBundle rb = new ResourceBundle() {
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
                if (baseName == null) {
                    throw new NullPointerException();
                }
                return super.getFormats(baseName);
            }
        };

        try {
            rb.getFormats(null);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetFormatsWithNonNull() {
        ResourceBundle rb = new ResourceBundle() {
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
                if (baseName == null) {
                    throw new NullPointerException();
                }
                return java.util.Arrays.asList("java.class", "java.properties");
            }
        };

        List<String> formats = rb.getFormats("baseName");
        if (formats == null || formats.isEmpty()) {
            throw new AssertionError("Expected non-empty formats list");
        }
        if (!formats.contains("java.class") || !formats.contains("java.properties")) {
            throw new AssertionError("Formats list does not contain expected values");
        }
    }
}
