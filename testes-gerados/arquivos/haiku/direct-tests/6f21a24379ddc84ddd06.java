/*
 * @test
 * @summary Verifies that ResourcesMgr.getAuthResourceString reports missing keys with MissingResourceException
 * @modules java.base/sun.security.util
 * @run main ResourcesMgrWitupTest
 */

import java.util.MissingResourceException;
import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    private static final String MISSING_KEY = "witup.this.key.should.not.exist.8f3a2c";

    public static void main(String[] args) {
        testMissingKeyThrows();
        testMissingKeyThrowsConsistently();
    }

    private static void testMissingKeyThrows() {
        try {
            String value = ResourcesMgr.getAuthResourceString(MISSING_KEY);
            throw new RuntimeException("Expected MissingResourceException, got: " + value);
        } catch (MissingResourceException expected) {
            // expected
        }
    }

    private static void testMissingKeyThrowsConsistently() {
        for (int i = 0; i < 2; i++) {
            try {
                ResourcesMgr.getAuthResourceString(MISSING_KEY);
                throw new RuntimeException("Expected MissingResourceException on call " + i);
            } catch (MissingResourceException expected) {
                // expected
            }
        }
    }
}
