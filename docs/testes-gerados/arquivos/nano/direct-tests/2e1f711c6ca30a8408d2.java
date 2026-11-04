/* @test    @summary Test ModuleResolution.withDeprecatedForRemoval() method    @run main ModuleResolutionWitupTest */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithDeprecatedForRemovalWhenNoFlagsSet();
        testWithDeprecatedForRemovalWhenWarnDeprecatedSet();
        testWithDeprecatedForRemovalWhenWarnIncubatingSet();
        testWithDeprecatedForRemovalWhenWarnDeprecatedForRemovalSet();
    }

    private static void testWithDeprecatedForRemovalWhenNoFlagsSet() {
        ModuleResolution mr = ModuleResolution.empty();
        try {
            ModuleResolution result = mr.withDeprecatedForRemoval();
            if (result.value() != (mr.value() | ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL)) {
                throw new AssertionError("Expected value: " + (mr.value() | ModuleResolution.WARN_DEPRECATED_FOR_REMOVAL) + ", but got: " + result.value());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testWithDeprecatedForRemovalWhenWarnDeprecatedSet() {
        ModuleResolution mr = new ModuleResolution(ModuleResolution.WARN_DEPRECATED);
        try {
            mr.withDeprecatedForRemoval();
            throw new AssertionError("Expected InternalError to be thrown");
        } catch (InternalError e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testWithDeprecatedForRemovalWhenWarnIncubatingSet() {
        ModuleResolution mr = new ModuleResolution(ModuleResolution.WARN_INCUBATING);
        try {
            mr.withDeprecatedForRemoval();
            throw new AssertionError("Expected InternalError to be thrown");
        } catch (InternalError e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
