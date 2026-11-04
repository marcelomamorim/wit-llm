/*
 * @test
 * @summary Test jdk.internal.misc.VM.getSavedProperty behavior
 * @run main VMWitupTest
 */

import jdk.internal.misc.VM;

public class VMWitupTest {
    public static void main(String[] args) throws Exception {
        testGetSavedPropertyBeforeInit();
        testGetSavedPropertyAfterInit();
    }

    private static void testGetSavedPropertyBeforeInit() {
        try {
            VM.getSavedProperty("anyKey");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            if (!"Not yet initialized".equals(e.getMessage())) {
                throw new RuntimeException("IllegalStateException message mismatch", e);
            }
        }
    }

    private static void testGetSavedPropertyAfterInit() throws Exception {
        // Use reflection to set savedProps to a known Map<String,String> instance
        java.lang.reflect.Field savedPropsField = VM.class.getDeclaredField("savedProps");
        savedPropsField.setAccessible(true);

        java.util.Map<String,String> map = new java.util.HashMap<>();
        map.put("key1", "value1");
        map.put("key2", null);

        savedPropsField.set(null, map);

        // Test existing key
        String v1 = VM.getSavedProperty("key1");
        if (!"value1".equals(v1)) {
            throw new RuntimeException("Expected value1 but got " + v1);
        }

        // Test key with null value
        String v2 = VM.getSavedProperty("key2");
        if (v2 != null) {
            throw new RuntimeException("Expected null but got " + v2);
        }

        // Test missing key returns null
        String v3 = VM.getSavedProperty("missingKey");
        if (v3 != null) {
            throw new RuntimeException("Expected null for missing key but got " + v3);
        }

        // Cleanup: reset savedProps to null to avoid side effects
        savedPropsField.set(null, null);
    }
}
