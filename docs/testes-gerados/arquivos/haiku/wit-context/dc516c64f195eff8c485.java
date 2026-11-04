/*
 * @test
 * @summary Checks ModuleResolution.withIncubating() flag composition and its InternalError guard through public API
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testIncubatingFlagIsSet();
        testOriginalInstanceIsNotMutated();
        testExistingDoNotResolveFlagIsPreserved();
        testIncubatingAfterDeprecatedThrows();
        testIncubatingAfterDeprecatedForRemovalThrows();
    }

    static void testIncubatingFlagIsSet() {
        ModuleResolution mr = ModuleResolution.empty().withIncubating();
        check(mr.hasIncubatingWarning(), "hasIncubatingWarning should be true");
        check(!mr.hasDeprecatedWarning(), "hasDeprecatedWarning should be false");
        check(!mr.hasDeprecatedForRemovalWarning(), "hasDeprecatedForRemovalWarning should be false");
        check(mr.value() != 0, "value should be non-zero");
    }

    static void testOriginalInstanceIsNotMutated() {
        ModuleResolution original = ModuleResolution.empty();
        ModuleResolution result = original.withIncubating();
        check(!original.hasIncubatingWarning(), "original must not gain incubating flag");
        check(result.hasIncubatingWarning(), "result must have incubating flag");
    }

    static void testExistingDoNotResolveFlagIsPreserved() {
        ModuleResolution mr = ModuleResolution.empty()
                .withDoNotResolveByDefault()
                .withIncubating();
        check(mr.doNotResolveByDefault(), "doNotResolveByDefault flag should be preserved");
        check(mr.hasIncubatingWarning(), "hasIncubatingWarning should be true");
    }

    static void testIncubatingAfterDeprecatedThrows() {
        ModuleResolution deprecated = ModuleResolution.empty().withDeprecated();
        boolean thrown = false;
        try {
            deprecated.withIncubating();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "withIncubating() after withDeprecated() must throw InternalError");
    }

    static void testIncubatingAfterDeprecatedForRemovalThrows() {
        ModuleResolution removal = ModuleResolution.empty().withDeprecatedForRemoval();
        boolean thrown = false;
        try {
            removal.withIncubating();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "withIncubating() after withDeprecatedForRemoval() must throw InternalError");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
