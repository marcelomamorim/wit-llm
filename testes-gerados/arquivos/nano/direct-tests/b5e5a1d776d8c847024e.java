/* @test    @summary Test ModuleResolution.withDeprecated() method for normal behavior    @run main ModuleResolutionWitupTest */

import static jdk.internal.module.ClassFileConstants.*;
import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithDeprecatedNormal();
        testWithDeprecatedInvalid();
    }

    private static void testWithDeprecatedNormal() {
        ModuleResolution mr = ModuleResolution.empty();
        // Ensure no exception is thrown and the returned object has the expected flag set
        ModuleResolution result = mr.withDeprecated();
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (!result.hasDeprecatedWarning()) {
            throw new AssertionError("Expected deprecated warning flag to be set");
        }
        if (result.value() != (mr.value() | ModuleResolution.WARN_DEPRECATED)) {
            throw new AssertionError("Flags do not match expected value");
        }
    }

    private static void testWithDeprecatedInvalid() {
        // Create a ModuleResolution with conflicting flags to trigger InternalError
        ModuleResolution mr = new ModuleResolution(ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL | ModuleResolution.WARN_INCUBATING);
        try {
            mr.withDeprecated();
            throw new AssertionError("Expected InternalError to be thrown");
        } catch (InternalError e) {
            // Expected exception
            if (!e.getMessage().contains("cannot add deprecated to")) {
                throw new AssertionError("Unexpected error message: " + e.getMessage());
            }
        }
    }
}
