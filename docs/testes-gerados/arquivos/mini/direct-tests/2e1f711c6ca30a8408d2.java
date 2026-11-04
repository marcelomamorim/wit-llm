/*
 * @test
 * @summary Test ModuleResolution.withDeprecatedForRemoval method behavior
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {
    public static void main(String[] args) {
        testWithDeprecatedForRemovalAddsFlag();
        testWithDeprecatedForRemovalThrowsIfDeprecatedFlagSet();
        testWithDeprecatedForRemovalThrowsIfIncubatingFlagSet();
    }

    private static void testWithDeprecatedForRemovalAddsFlag() {
        ModuleResolution base = ModuleResolution.empty();
        ModuleResolution result = base.withDeprecatedForRemoval();
        int expectedFlag = 0x4; // WARN_DEPRECATED_FOR_REMOVAL
        if ((result.value() & expectedFlag) == 0) {
            throw new AssertionError("Expected WARN_DEPRECATED_FOR_REMOVAL flag to be set");
        }
    }

    private static void testWithDeprecatedForRemovalThrowsIfDeprecatedFlagSet() {
        int WARN_DEPRECATED = 0x2;
        ModuleResolution base = new ModuleResolution(WARN_DEPRECATED);
        try {
            base.withDeprecatedForRemoval();
            throw new AssertionError("Expected InternalError when WARN_DEPRECATED flag is set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated for removal")) {
                throw new AssertionError("Unexpected InternalError message: " + msg);
            }
        }
    }

    private static void testWithDeprecatedForRemovalThrowsIfIncubatingFlagSet() {
        int WARN_INCUBATING = 0x8;
        ModuleResolution base = new ModuleResolution(WARN_INCUBATING);
        try {
            base.withDeprecatedForRemoval();
            throw new AssertionError("Expected InternalError when WARN_INCUBATING flag is set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated for removal")) {
                throw new AssertionError("Unexpected InternalError message: " + msg);
            }
        }
    }
}
