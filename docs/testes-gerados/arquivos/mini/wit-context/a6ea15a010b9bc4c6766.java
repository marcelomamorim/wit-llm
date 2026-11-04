/*
 * @test
 * @summary Test sun.security.util.LocalizedMessage.getLocalized method behavior
 * @run main LocalizedMessageWitupTest
 */

import sun.security.util.LocalizedMessage;
import sun.security.util.ResourcesMgr;
import java.text.MessageFormat;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        testGetLocalizedWithNullArguments();
        testGetLocalizedWithArguments();
        testGetLocalizedWithInvalidKey();
    }

    private static void testGetLocalizedWithNullArguments() {
        // Use a known key from ResourcesMgr for testing
        String key = "test.key";
        // We simulate ResourcesMgr.getString to return a fixed string for this key
        // Since we cannot mock, we rely on the actual ResourcesMgr behavior
        // If key not found, getString returns key itself or throws MissingResourceException
        // So we test that getLocalized returns the string from ResourcesMgr unchanged when arguments is null

        String result = LocalizedMessage.getLocalized(key, (Object[]) null);
        if (result == null) {
            throw new RuntimeException("getLocalized returned null for key: " + key);
        }
        // The result should contain the key or a localized string
        if (result.isEmpty()) {
            throw new RuntimeException("getLocalized returned empty string for key: " + key);
        }
    }

    private static void testGetLocalizedWithArguments() {
        // Use a key that likely contains placeholders, e.g. "test.format"
        // Since we cannot guarantee ResourcesMgr contents, we test that formatting does not throw
        String key = "test.format";
        Object[] args = new Object[] {"arg1", 2};

        String result = null;
        try {
            result = LocalizedMessage.getLocalized(key, args);
        } catch (Exception e) {
            throw new RuntimeException("getLocalized threw exception with arguments", e);
        }

        if (result == null) {
            throw new RuntimeException("getLocalized returned null with arguments");
        }
    }

    private static void testGetLocalizedWithInvalidKey() {
        // Use a key that likely does not exist
        String key = "non.existent.key.for.testing";

        String result = null;
        try {
            result = LocalizedMessage.getLocalized(key);
        } catch (InternalError e) {
            // According to WIT, InternalError may be thrown if initLevel < 4
            // This is acceptable behavior, test passes if exception thrown
            return;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }

        if (result == null) {
            throw new RuntimeException("getLocalized returned null for invalid key");
        }
    }
}
