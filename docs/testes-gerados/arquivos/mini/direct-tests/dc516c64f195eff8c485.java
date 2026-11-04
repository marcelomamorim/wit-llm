/*
 * @test
 * @summary Test ModuleResolution.withIncubating() behavior
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {
    public static void main(String[] args) {
        testWithIncubatingAddsFlag();
        testWithIncubatingThrowsIfDeprecatedFlagSet();
        testWithIncubatingThrowsIfDeprecatedForRemovalFlagSet();
    }

    private static void testWithIncubatingAddsFlag() {
        ModuleResolution mr = ModuleResolution.empty();
        ModuleResolution incubating = mr.withIncubating();
        if (!incubating.hasIncubatingWarning()) {
            throw new AssertionError("Expected incubating warning flag to be set");
        }
        if (incubating.value() != (mr.value() | 0x4)) { // WARN_INCUBATING = 0x4
            throw new AssertionError("Value does not include WARN_INCUBATING flag");
        }
    }

    private static void testWithIncubatingThrowsIfDeprecatedFlagSet() {
        final int WARN_DEPRECATED = 0x1;
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError when adding incubating to deprecated");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add incubating")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testWithIncubatingThrowsIfDeprecatedForRemovalFlagSet() {
        final int WARN_DEPRECATED_FOR_REMOVAL = 0x2;
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED_FOR_REMOVAL);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError when adding incubating to deprecated for removal");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add incubating")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
