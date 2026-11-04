/*
 * @test
 * @summary Test sun.security.util.ResourcesMgr.getString(String) behavior including boot state check
 * @run main ResourcesMgrWitupTest
 */

import sun.security.util.ResourcesMgr;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ResourcesMgrWitupTest {
    public static void main(String[] args) throws Exception {
        testGetStringAfterBoot();
        testGetStringBeforeBootThrowsInternalError();
        System.out.println("ResourcesMgrWitupTest passed");
    }

    private static void testGetStringAfterBoot() {
        // This test assumes VM.isBooted() returns true in normal test environment
        // so getString should not throw and should return a non-null string or throw MissingResourceException if key not found
        // We test with a likely invalid key to check exception behavior
        String key = "nonexistent_key_for_test";
        try {
            String result = ResourcesMgr.getString(key);
            if (result == null) {
                throw new AssertionError("getString returned null for key: " + key);
            }
        } catch (java.util.MissingResourceException e) {
            // expected if key not found
        } catch (InternalError e) {
            throw new AssertionError("Unexpected InternalError when VM is booted", e);
        }
    }

    private static void testGetStringBeforeBootThrowsInternalError() throws Exception {
        // We need to simulate VM.isBooted() == false to trigger InternalError
        // VM.isBooted() is a native method returning a private static boolean field initLevel >= 4
        // We use reflection to set initLevel to 0 temporarily

        Class<?> vmClass = Class.forName("jdk.internal.misc.VM");
        Field initLevelField = null;
        for (Field f : vmClass.getDeclaredFields()) {
            if (f.getType() == int.class && Modifier.isStatic(f.getModifiers())) {
                f.setAccessible(true);
                initLevelField = f;
                break;
            }
        }
        if (initLevelField == null) {
            // Could not find initLevel field, skip this test
            System.out.println("Skipping testGetStringBeforeBootThrowsInternalError: cannot access initLevel field");
            return;
        }

        int originalValue = initLevelField.getInt(null);
        try {
            initLevelField.setInt(null, 0); // simulate not booted
            try {
                ResourcesMgr.getString("any");
                throw new AssertionError("Expected InternalError not thrown when VM not booted");
            } catch (InternalError e) {
                // expected
                String msg = e.getMessage();
                if (msg == null || !msg.contains("Expected to use ResourceBundle only after booted")) {
                    throw new AssertionError("InternalError message unexpected: " + msg);
                }
            }
        } finally {
            initLevelField.setInt(null, originalValue); // restore original value
        }
    }
}
