/*
 * @test
 * @summary Test ModuleResolution.withDeprecatedForRemoval() behavior including exception path
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {
    public static void main(String[] args) {
        testWithDeprecatedForRemovalNoConflict();
        testWithDeprecatedForRemovalThrowsOnDeprecatedFlag();
        testWithDeprecatedForRemovalThrowsOnIncubatingFlag();
    }

    private static void testWithDeprecatedForRemovalNoConflict() {
        // value with no WARN_DEPRECATED or WARN_INCUBATING bits set
        ModuleResolution mr = new ModuleResolution(0);
        ModuleResolution result = mr.withDeprecatedForRemoval();
        int expectedValue = 0 | (1 << 2); // WARN_DEPRECATED_FOR_REMOVAL bit
        if (result.value() != expectedValue) {
            throw new Error("Expected value " + expectedValue + " but got " + result.value());
        }
    }

    private static void testWithDeprecatedForRemovalThrowsOnDeprecatedFlag() {
        // value with WARN_DEPRECATED bit set (1 << 1)
        int WARN_DEPRECATED = 1 << 1;
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED);
        try {
            mr.withDeprecatedForRemoval();
            throw new Error("Expected InternalError not thrown when WARN_DEPRECATED bit set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated for removal")) {
                throw new Error("Unexpected InternalError message: " + msg);
            }
        }
    }

    private static void testWithDeprecatedForRemovalThrowsOnIncubatingFlag() {
        // value with WARN_INCUBATING bit set (1 << 3)
        int WARN_INCUBATING = 1 << 3;
        ModuleResolution mr = new ModuleResolution(WARN_INCUBATING);
        try {
            mr.withDeprecatedForRemoval();
            throw new Error("Expected InternalError not thrown when WARN_INCUBATING bit set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated for removal")) {
                throw new Error("Unexpected InternalError message: " + msg);
            }
        }
    }
}
