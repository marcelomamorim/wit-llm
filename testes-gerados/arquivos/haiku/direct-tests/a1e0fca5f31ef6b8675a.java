/*
 * @test
 * @summary Verify ResourcesMgr.getString reports a missing key with MissingResourceException, consistently across repeated calls
 * @modules java.base/sun.security.util
 * @run main ResourcesMgrWitupTest
 */

import java.util.MissingResourceException;
import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {
    public static void main(String[] args) {
        String key = "witup.nonexistent.key.4f9c";
        for (int i = 0; i < 2; i++) {
            try {
                ResourcesMgr.getString(key);
                throw new RuntimeException("Expected MissingResourceException for key: " + key);
            } catch (MissingResourceException e) {
                if (!key.equals(e.getKey())) {
                    throw new RuntimeException("Unexpected key in exception: " + e.getKey());
                }
            }
        }
    }
}
