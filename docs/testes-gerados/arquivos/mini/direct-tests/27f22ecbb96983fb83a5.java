/*
 * @test
 * @summary Test java.util.ResourceBundle.getFormats(String) method behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testGetFormatsWithValidBaseName();
        testGetFormatsWithNullBaseName();
    }

    private static void testGetFormatsWithValidBaseName() {
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

        List<String> formats = rb.getFormats("baseName");
        if (formats == null) {
            throw new AssertionError("getFormats returned null for valid baseName");
        }
        if (!formats.contains("java.class") || !formats.contains("java.properties")) {
            throw new AssertionError("getFormats does not contain expected format strings");
        }
    }

    private static void testGetFormatsWithNullBaseName() {
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

        try {
            rb.getFormats(null);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
