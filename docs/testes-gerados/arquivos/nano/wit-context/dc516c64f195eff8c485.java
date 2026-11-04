/* @test    @summary Test for ModuleResolution.withIncubating() method behavior    @run main ModuleResolutionWitupTest */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithIncubatingWhenNoFlags();
        testWithIncubatingWhenDeprecatedFlagSet();
        testWithIncubatingWhenDeprecatedForRemovalFlagSet();
        testWithIncubatingWhenIncubatingFlagSet();
    }

    private static void testWithIncubatingWhenNoFlags() {
        ModuleResolution mr = ModuleResolution.empty();
        // Clear all flags
        // Since no public setter, create via constructor with 0
        // Call method and verify no exception
        try {
            ModuleResolution result = mr.withIncubating();
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
            if (result.value() != mr.value() | ModuleResolution.WARN_INCUBATING) {
                throw new AssertionError("Expected value to include WARN_INCUBATING flag")
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testWithIncubatingWhenNoFlags", t);
        }
    }

    private static void testWithIncubatingWhenDeprecatedFlagSet() {
        // Set deprecated flags
        int deprecatedFlags = ModuleResolution.WARN_DEPRECATED | ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL;
        ModuleResolution mr = new ModuleResolution(deprecatedFlags);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError to be thrown when deprecated flags are set");
        } catch (InternalError e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("cannot add incubating to")) {
                throw new AssertionError("Unexpected InternalError message: " + message);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type in testWithIncubatingWhenDeprecatedFlagSet", t);
        }
    }

    private static void testWithIncubatingWhenDeprecatedForRemovalFlagSet() {
        // Set deprecated for removal flags
        int deprecatedForRemovalFlags = ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL | ModuleResolution.WARN_DEPRECATED;
        ModuleResolution mr = new ModuleResolution(deprecatedForRemovalFlags);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError to be thrown when deprecated for removal flags are set");
        } catch (InternalError e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("cannot add incubating to")) {
                throw new AssertionError("Unexpected InternalError message: " + message);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type in testWithIncubatingWhenDeprecatedForRemovalFlagSet", t);
        }
    }
}
