/*
 * @test
 * @summary Verify ModuleResolution deprecated-for-removal flag behavior and conflict checks
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    interface Action {
        void run();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectInternalError(Action action, String message) {
        try {
            action.run();
        } catch (InternalError e) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        ModuleResolution base = ModuleResolution.empty();

        ModuleResolution r = base.withDeprecatedForRemoval();
        check(r.hasDeprecatedForRemovalWarning(), 'flag for removal should be set');
        check(!r.hasDeprecatedWarning(), 'deprecated flag should not be set');
        check(!r.hasIncubatingWarning(), 'incubating flag should not be set');
        check(!r.doNotResolveByDefault(), 'do-not-resolve flag should not be set');
        check(!base.hasDeprecatedForRemovalWarning(), 'receiver must be unchanged');

        ModuleResolution again = r.withDeprecatedForRemoval();
        check(again.value() == r.value(), 'adding the same flag twice should be idempotent');
        check(again.hasDeprecatedForRemovalWarning(), 'flag should remain set after repeat');

        ModuleResolution d = base.withDoNotResolveByDefault().withDeprecatedForRemoval();
        check(d.doNotResolveByDefault(), 'existing do-not-resolve flag should be preserved');
        check(d.hasDeprecatedForRemovalWarning(), 'flag for removal should be added');

        expectInternalError(() -> base.withDeprecated().withDeprecatedForRemoval(),
                'adding for-removal after deprecated should fail');
        expectInternalError(() -> base.withIncubating().withDeprecatedForRemoval(),
                'adding for-removal after incubating should fail');
    }
}
