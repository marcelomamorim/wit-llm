/*
 * @test
 * @summary Test ModuleResolution.withIncubating() method behavior including exception path
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithIncubatingNoConflict();
        testWithIncubatingWithDeprecatedFlag();
        testWithIncubatingWithDeprecatedForRemovalFlag();
    }

    private static void testWithIncubatingNoConflict() {
        // value with no deprecated or deprecated for removal flags set
        int baseValue = 0;
        ModuleResolution mr = new ModuleResolution(baseValue);
        ModuleResolution result = mr.withIncubating();
        // The returned value should have the WARN_INCUBATING bit set
        if ((result.value() & (1 << 3)) == 0) { // WARN_INCUBATING is 1 << 3
            throw new AssertionError("WARN_INCUBATING flag not set in result");
        }
    }

    private static void testWithIncubatingWithDeprecatedFlag() {
        // value with WARN_DEPRECATED bit set (1 << 1)
        int deprecatedFlag = 1 << 1;
        ModuleResolution mr = new ModuleResolution(deprecatedFlag);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError not thrown when WARN_DEPRECATED flag set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add incubating")) {
                throw new AssertionError("Unexpected InternalError message: " + msg);
            }
        }
    }

    private static void testWithIncubatingWithDeprecatedForRemovalFlag() {
        // value with WARN_DEPRECATED_FOR_REMOVAL bit set (1 << 2)
        int deprecatedForRemovalFlag = 1 << 2;
        ModuleResolution mr = new ModuleResolution(deprecatedForRemovalFlag);
        try {
            mr.withIncubating();
            throw new AssertionError("Expected InternalError not thrown when WARN_DEPRECATED_FOR_REMOVAL flag set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add incubating")) {
                throw new AssertionError("Unexpected InternalError message: " + msg);
            }
        }
    }
}
