/*
 * @test
 * @summary Test jdk.internal.misc.VM.getSavedProperties() behavior
 * @run main VMWitupTest
 */

import java.util.Map;

public class VMWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that getSavedProperties throws IllegalStateException if savedProps is null
        try {
            jdk.internal.misc.VM.getSavedProperties();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            if (!"Not yet initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Use reflection to set savedProps to a non-null Map to test normal return
        java.lang.reflect.Field f = jdk.internal.misc.VM.class.getDeclaredField("savedProps");
        f.setAccessible(true);
        java.util.Map<String,String> testMap = new java.util.HashMap<>();
        testMap.put("key", "value");
        f.set(null, testMap);

        Map<String,String> result = jdk.internal.misc.VM.getSavedProperties();
        if (result == null) {
            throw new RuntimeException("getSavedProperties returned null");
        }
        if (!"value".equals(result.get("key"))) {
            throw new RuntimeException("getSavedProperties returned incorrect map content");
        }

        // Reset savedProps to null to avoid side effects
        f.set(null, null);
    }
}
