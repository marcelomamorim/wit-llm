/*
 * @test
 * @summary Verify ModuleResolution.withDeprecated() adds the deprecated flag, keeps the receiver immutable, and rejects combinations with removal/incubating flags
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectInternalError(Runnable r, String message) {
        try {
            r.run();
        } catch (InternalError expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        ModuleResolution empty = ModuleResolution.empty();
        ModuleResolution dep = empty.withDeprecated();

        check(empty.value() == 0, "empty() must have value 0");
        check(!empty.hasDeprecatedWarning(), "empty() must not have deprecated warning");

        check(dep.hasDeprecatedWarning(), "withDeprecated() must set deprecated warning");
        check(!dep.hasDeprecatedForRemovalWarning(), "withDeprecated() must not set removal warning");
        check(!dep.hasIncubatingWarning(), "withDeprecated() must not set incubating warning");
        check(dep.value() != 0, "withDeprecated() must change value");

        check(!empty.hasDeprecatedWarning(), "receiver must remain unchanged");
        check(empty.value() == 0, "receiver value must remain 0");

        check(dep.withDeprecated().value() == dep.value(),
              "withDeprecated() on already deprecated resolution must keep value");

        ModuleResolution dnr = empty.withDoNotResolveByDefault().withDeprecated();
        check(dnr.doNotResolveByDefault(), "DO_NOT_RESOLVE_BY_DEFAULT flag must be preserved");
        check(dnr.hasDeprecatedWarning(), "deprecated flag must be added alongside existing flag");

        expectInternalError(() -> empty.withDeprecatedForRemoval().withDeprecated(),
                            "withDeprecated() must reject removal-warning resolution");
        expectInternalError(() -> empty.withIncubating().withDeprecated(),
                            "withDeprecated() must reject incubating resolution");
    }
}
