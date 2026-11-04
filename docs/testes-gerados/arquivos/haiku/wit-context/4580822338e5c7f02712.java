/*
 * @test
 * @summary Checks Builder.requires(String) exception paths for self-dependence, automatic modules and invalid names
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testSelfDependenceRejected();
        testAutomaticBuilderRejectsRequires();
        testInvalidNameRejectedInStrictMode();
        testValidRequiresRecorded();
    }

    static void testSelfDependenceRejected() {
        expectThrows(IllegalArgumentException.class,
                () -> ModuleDescriptor.newModule("m").requires("m"));
    }

    static void testAutomaticBuilderRejectsRequires() {
        expectThrows(IllegalStateException.class,
                () -> ModuleDescriptor.newAutomaticModule("am").requires("b"));
    }

    static void testInvalidNameRejectedInStrictMode() {
        expectThrows(IllegalArgumentException.class,
                () -> ModuleDescriptor.newModule("m").requires("1abc"));
    }

    static void testValidRequiresRecorded() {
        ModuleDescriptor md = ModuleDescriptor.newModule("a")
                .requires("b")
                .build();
        Set<ModuleDescriptor.Requires> reqs = md.requires();
        boolean found = false;
        for (ModuleDescriptor.Requires r : reqs) {
            if (r.name().equals("b")) {
                found = true;
            }
        }
        if (!found) {
            throw new AssertionError("requires(\"b\") not recorded");
        }
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new AssertionError("unexpected exception: " + t, t);
        }
        throw new AssertionError("expected " + type.getName());
    }
}
