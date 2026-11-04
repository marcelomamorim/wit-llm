/*
 * @test
 * @summary Verify ResourcesMgr.getAuthResourceString failure behavior for unknown and null keys
 * @modules java.base/sun.security.util
 * @run main ResourcesMgrWitupTest
 */

import java.util.MissingResourceException;
import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        checkUnknownKeyThrowsMissingResource();
        checkRepeatedUnknownKeyIsConsistent();
        checkNullKeyThrowsNullPointerException();
    }

    private static boolean throwsMissingResource(String key) {
        try {
            ResourcesMgr.getAuthResourceString(key);
            return false;
        } catch (MissingResourceException expected) {
            return true;
        }
    }

    private static void checkUnknownKeyThrowsMissingResource() {
        if (!throwsMissingResource("witup.nonexistent.key.4f1c")) {
            throw new RuntimeException("MissingResourceException not thrown for unknown key");
        }
    }

    private static void checkRepeatedUnknownKeyIsConsistent() {
        String key = "witup.nonexistent.key.9a2b";
        boolean first = throwsMissingResource(key);
        boolean second = throwsMissingResource(key);
        if (!first || !second) {
            throw new RuntimeException("Repeated lookup of unknown key was inconsistent");
        }
    }

    private static void checkNullKeyThrowsNullPointerException() {
        try {
            ResourcesMgr.getAuthResourceString(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException not thrown for null key");
    }
}
