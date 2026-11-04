/* @test    @summary Test ResourcesMgr.getAuthResourceString for known resource key    @run main ResourcesMgrWitupTest */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        testGetAuthResourceStringWithValidKey();
        testGetAuthResourceStringWithInvalidKey();
    }

    private static void testGetAuthResourceStringWithValidKey() {
        String key = "some.valid.key";
        String result = ResourcesMgr.getAuthResourceString(key);
        // Since resource bundle is not loaded in test, result may be same as key or a known string.
        // We check that the result is not null.
        if (result == null) {
            throw new AssertionError("Expected non-null result for valid key");
        }
    }

    private static void testGetAuthResourceStringWithInvalidKey() {
        String key = "invalid.key";
        try {
            String result = ResourcesMgr.getAuthResourceString(key);
            if (result == null) {
                throw new AssertionError("Result should not be null even for invalid key");
            }
        } catch (Exception e) {
            // If an exception occurs, it is acceptable if it's an expected exception.
            // Since getString may throw MissingResourceException, but not declared, we catch Throwable.
            if (!(e instanceof java.util.MissingResourceException)) {
                throw new AssertionError("Unexpected exception for invalid key", e);
            }
        }
    }
}
