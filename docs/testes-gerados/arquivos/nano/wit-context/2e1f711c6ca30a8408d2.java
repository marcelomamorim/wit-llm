/*
 * @test
 * @summary Test for ModuleResolution.withDeprecatedForRemoval()
 * @run main ModuleResolutionWitupTest
 */

import java.lang.module.ModuleReference;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithDeprecatedForRemovalThrowsOnInvalidState();
        testWithDeprecatedForRemovalReturnsExpected();
    }

    private static void testWithDeprecatedForRemovalThrowsOnInvalidState() {
        // Arrange: create a ModuleResolution with flags that include WARN_DEPRECATED or WARN_INCUBATING
        ModuleResolution mr = ModuleResolution.empty();
        // Use reflection or direct access if possible; here, simulate by creating a subclass or dummy object
        // Since constructor is package-private, assume we can access via a factory or reflection; for test, simulate
        // For simplicity, assume we can instantiate with a known value
        // Note: in real test, use reflection or a test-specific constructor if available
        int invalidValue = ModuleResolution.WARN_DEPRECATED | ModuleResolution.WARN_INCUBATING;
        ModuleResolution invalidMr = new ModuleResolution(invalidValue);

        try {
            invalidMr.withDeprecatedForRemoval();
            // Fail if no exception
            throw new AssertionError("Expected InternalError was not thrown");
        } catch (InternalError e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("cannot add deprecated for removal")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }

    private static void testWithDeprecatedForRemovalReturnsExpected() {
        // Arrange: create a ModuleResolution with a valid state
        ModuleResolution mr = ModuleResolution.empty();
        // Use a value that does not include WARN_DEPRECATED or WARN_INCUBATING
        int value = 0;
        ModuleResolution mr2 = new ModuleResolution(value);
        // Act: call withDeprecatedForRemoval
        ModuleResolution result = mr2.withDeprecatedForRemoval();
        // Assert: check that the returned object has the correct flags
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if ((result.value() & ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL) == 0) {
            throw new AssertionError("Expected WARN_DEPRECATED_FOR_REMOVAL flag to be set");
        }
        // Also, original object should remain unchanged
        if ((mr2.value() & ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL) != 0) {
            throw new AssertionError("Original object should not be modified");
        }
    }
}
