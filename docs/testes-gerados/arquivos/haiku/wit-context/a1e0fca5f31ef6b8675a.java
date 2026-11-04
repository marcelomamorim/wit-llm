/*
 * @test
 * @summary Verifies that ResourcesMgr.getString reports a missing key with MissingResourceException after the VM has booted
 * @modules java.base/sun.security.util
 * @run main ResourcesMgrWitupTest
 */

import java.util.MissingResourceException;
import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        String missingKey = "witup.nonexistent.key.for.test";

        checkMissingKey(missingKey);
        checkMissingKey(missingKey);
    }

    private static void checkMissingKey(String key) {
        try {
            ResourcesMgr.getString(key);
            throw new RuntimeException("Expected MissingResourceException for key: " + key);
        } catch (MissingResourceException expected) {
            // expected behavior
        }
    }
}
