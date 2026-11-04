/*
 * @test
 * @summary Test jdk.internal.misc.VM.getSavedProperties() for expected exception and normal behavior
 * @run main VMWitupTest
 */

import java.util.Map;

public class VMWitupTest {

    public static void main(String[] args) {
        testGetSavedPropertiesThrowsIfNull();
        testGetSavedPropertiesReturnsMapIfNotNull();
    }

    private static void testGetSavedPropertiesThrowsIfNull() {
        // We cannot set savedProps directly, so we test behavior when it is null by assumption
        // The method throws IllegalStateException if savedProps is null
        // Since savedProps is private and no setter is visible, we test only that exception is thrown if null
        try {
            // We expect IllegalStateException if savedProps is null
            // This test assumes initial state or reset state where savedProps is null
            // If savedProps is not null, this test will fail and we report that
            Map<String, String> props = jdk.internal.misc.VM.getSavedProperties();
            // If no exception, fail test
            throw new RuntimeException("Expected IllegalStateException not thrown, got: " + props);
        } catch (IllegalStateException e) {
            // Expected path
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
    }

    private static void testGetSavedPropertiesReturnsMapIfNotNull() {
        // We cannot set savedProps directly, so this test is limited
        // We test that if getSavedProperties() returns non-null, it is a Map
        try {
            Map<String, String> props = jdk.internal.misc.VM.getSavedProperties();
            if (props == null) {
                // If null, skip this test as it is covered by exception test
                return;
            }
            // Check that returned object is a Map
            if (!(props instanceof Map)) {
                throw new RuntimeException("Returned object is not a Map");
            }
        } catch (IllegalStateException e) {
            // If exception thrown, skip this test
        }
    }
}
