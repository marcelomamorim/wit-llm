/*
 * @test
 * @summary Verify ModuleResolution.withDeprecated behavior through public API
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        ModuleResolution base = ModuleResolution.empty();
        check(base.value() == 0, "empty() should have value 0");

        ModuleResolution dep = base.withDeprecated();
        check(dep.hasDeprecatedWarning(), "withDeprecated() should set deprecated warning");
        check(!dep.hasDeprecatedForRemovalWarning(), "withDeprecated() should not set deprecated-for-removal warning");
        check(!dep.hasIncubatingWarning(), "withDeprecated() should not set incubating warning");
        check(!dep.doNotResolveByDefault(), "withDeprecated() should not set do-not-resolve flag");

        ModuleResolution preserved = base.withDoNotResolveByDefault().withDeprecated();
        check(preserved.doNotResolveByDefault(), "withDeprecated() should preserve do-not-resolve flag");
        check(preserved.hasDeprecatedWarning(), "withDeprecated() should set deprecated warning on combined value");

        boolean thrown = false;
        try {
            base.withDeprecatedForRemoval().withDeprecated();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "withDeprecated() should throw InternalError when deprecated-for-removal is set");

        thrown = false;
        try {
            base.withIncubating().withDeprecated();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "withDeprecated() should throw InternalError when incubating is set");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
