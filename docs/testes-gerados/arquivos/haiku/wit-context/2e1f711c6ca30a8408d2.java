/*
 * @test
 * @summary Witup-generated tests for ModuleResolution.withDeprecatedForRemoval()
 * @modules java.base/jdk.internal.module
 * @run main ModuleResolutionWitupTest
 */

import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testAddsFlagOnEmpty();
        testRejectsWhenDeprecatedPresent();
        testRejectsWhenIncubatingPresent();
        testIdempotentWhenAlreadyForRemoval();
        testImmutability();
        testPreservesOtherFlags();
    }

    static void testAddsFlagOnEmpty() {
        ModuleResolution mr = ModuleResolution.empty().withDeprecatedForRemoval();
        if (!mr.hasDeprecatedForRemovalWarning()) {
            throw new RuntimeException("deprecated for removal flag not set");
        }
        if (mr.hasDeprecatedWarning()) {
            throw new RuntimeException("unexpected deprecated flag");
        }
        if (mr.hasIncubatingWarning()) {
            throw new RuntimeException("unexpected incubating flag");
        }
    }

    static void testRejectsWhenDeprecatedPresent() {
        ModuleResolution mr = ModuleResolution.empty().withDeprecated();
        expectInternalError(mr);
    }

    static void testRejectsWhenIncubatingPresent() {
        ModuleResolution mr = ModuleResolution.empty().withIncubating();
        expectInternalError(mr);
    }

    static void testIdempotentWhenAlreadyForRemoval() {
        ModuleResolution mr = ModuleResolution.empty().withDeprecatedForRemoval();
        ModuleResolution again = mr.withDeprecatedForRemoval();
        if (!again.hasDeprecatedForRemovalWarning()) {
            throw new RuntimeException("flag lost on repeated call");
        }
    }

    static void testImmutability() {
        ModuleResolution base = ModuleResolution.empty();
        ModuleResolution derived = base.withDeprecatedForRemoval();
        if (base.hasDeprecatedForRemovalWarning()) {
            throw new RuntimeException("receiver was mutated");
        }
        if (!derived.hasDeprecatedForRemovalWarning()) {
            throw new RuntimeException("derived instance missing flag");
        }
    }

    static void testPreservesOtherFlags() {
        ModuleResolution mr = ModuleResolution.empty()
                .withDoNotResolveByDefault()
                .withDeprecatedForRemoval();
        if (!mr.doNotResolveByDefault()) {
            throw new RuntimeException("DO_NOT_RESOLVE_BY_DEFAULT lost");
        }
        if (!mr.hasDeprecatedForRemovalWarning()) {
            throw new RuntimeException("deprecated for removal flag not set");
        }
    }

    static void expectInternalError(ModuleResolution mr) {
        try {
            mr.withDeprecatedForRemoval();
        } catch (InternalError e) {
            return;
        }
        throw new RuntimeException("expected InternalError was not thrown");
    }
}
