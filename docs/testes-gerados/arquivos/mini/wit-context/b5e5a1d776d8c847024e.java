/*
 * @test
 * @summary Test ModuleResolution.withDeprecated() method behavior including exception path
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {
    public static void main(String[] args) {
        testWithDeprecatedNoException();
        testWithDeprecatedThrowsInternalError();
    }

    private static void testWithDeprecatedNoException() {
        // Create a ModuleResolution with no conflicting flags
        ModuleResolution mr = new ModuleResolution(0);
        ModuleResolution result = mr.withDeprecated();
        // The returned value should have WARN_DEPRECATED bit set
        int WARN_DEPRECATED = 0x1; // from ClassFileConstants
        if ((result.value() & WARN_DEPRECATED) == 0) {
            throw new AssertionError("withDeprecated() did not set WARN_DEPRECATED flag");
        }
    }

    private static void testWithDeprecatedThrowsInternalError() {
        // Prepare values for WARN_DEPRECATED_FOR_REMOVAL and WARN_INCUBATING
        int WARN_DEPRECATED_FOR_REMOVAL = 0x2; // from ClassFileConstants
        int WARN_INCUBATING = 0x4; // from ClassFileConstants

        // Test with WARN_DEPRECATED_FOR_REMOVAL flag set
        ModuleResolution mr1 = new ModuleResolution(WARN_DEPRECATED_FOR_REMOVAL);
        try {
            mr1.withDeprecated();
            throw new AssertionError("Expected InternalError not thrown when WARN_DEPRECATED_FOR_REMOVAL flag set");
        } catch (InternalError e) {
            // Expected
        }

        // Test with WARN_INCUBATING flag set
        ModuleResolution mr2 = new ModuleResolution(WARN_INCUBATING);
        try {
            mr2.withDeprecated();
            throw new AssertionError("Expected InternalError not thrown when WARN_INCUBATING flag set");
        } catch (InternalError e) {
            // Expected
        }

        // Test with both flags set
        ModuleResolution mr3 = new ModuleResolution(WARN_DEPRECATED_FOR_REMOVAL | WARN_INCUBATING);
        try {
            mr3.withDeprecated();
            throw new AssertionError("Expected InternalError not thrown when both flags set");
        } catch (InternalError e) {
            // Expected
        }
    }
}
