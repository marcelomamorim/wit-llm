/*
 * @test
 * @summary Tests ModuleResolution.withIncubating() observable behavior and its InternalError conflicts
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        testAddsIncubatingWarning();
        testReturnsNewInstanceAndKeepsOriginal();
        testPreservesDoNotResolveByDefault();
        testIdempotent();
        testConflictWithDeprecated();
        testConflictWithDeprecatedForRemoval();
    }

    static void testAddsIncubatingWarning() {
        ModuleResolution mr = ModuleResolution.empty().withIncubating();
        check(mr.hasIncubatingWarning(), "incubating warning expected");
        check(!mr.hasDeprecatedWarning(), "deprecated warning not expected");
        check(!mr.hasDeprecatedForRemovalWarning(), "deprecated-for-removal warning not expected");
        check(!mr.doNotResolveByDefault(), "doNotResolveByDefault not expected");
    }

    static void testReturnsNewInstanceAndKeepsOriginal() {
        ModuleResolution original = ModuleResolution.empty();
        ModuleResolution result = original.withIncubating();
        check(result != original, "withIncubating must return a new instance");
        check(!original.hasIncubatingWarning(), "original must not be modified");
        check(result.value() != original.value(), "value should change after adding incubating");
    }

    static void testPreservesDoNotResolveByDefault() {
        ModuleResolution mr = ModuleResolution.empty()
                .withDoNotResolveByDefault()
                .withIncubating();
        check(mr.doNotResolveByDefault(), "DO_NOT_RESOLVE_BY_DEFAULT flag must be preserved");
        check(mr.hasIncubatingWarning(), "incubating warning expected");
    }

    static void testIdempotent() {
        ModuleResolution once = ModuleResolution.empty().withIncubating();
        ModuleResolution twice = once.withIncubating();
        check(twice.hasIncubatingWarning(), "incubating warning expected after second call");
        check(twice.value() == once.value(), "repeated withIncubating should keep same value");
    }

    static void testConflictWithDeprecated() {
        ModuleResolution deprecated = ModuleResolution.empty().withDeprecated();
        boolean thrown = false;
        try {
            deprecated.withIncubating();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "InternalError expected when adding incubating to deprecated");
    }

    static void testConflictWithDeprecatedForRemoval() {
        ModuleResolution dfr = ModuleResolution.empty().withDeprecatedForRemoval();
        boolean thrown = false;
        try {
            dfr.withIncubating();
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "InternalError expected when adding incubating to deprecated-for-removal");
    }
}
