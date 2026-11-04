/*
 * @test
 * @summary Tests ModuleDescriptor.Builder.uses(String) observable behavior: builder return value, recorded service, duplicate and automatic-module rejection
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    interface Action {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        testUsesReturnsSameBuilderAndRecordsService();
        testDuplicateUsesThrowsIllegalStateException();
        testAutomaticModuleUsesThrowsIllegalStateException();
    }

    static void testUsesReturnsSameBuilderAndRecordsService() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m1");
        ModuleDescriptor.Builder r = b.uses("java.lang.Runnable");
        if (r != b) {
            throw new RuntimeException("uses(String) should return the same Builder");
        }
        ModuleDescriptor md = b.build();
        Set<String> uses = md.uses();
        if (!uses.contains("java.lang.Runnable")) {
            throw new RuntimeException("service not recorded: " + uses);
        }
        if (uses.size() != 1) {
            throw new RuntimeException("unexpected uses size: " + uses.size());
        }
    }

    static void testDuplicateUsesThrowsIllegalStateException() throws Exception {
        final ModuleDescriptor.Builder b =
            ModuleDescriptor.newModule("m2").uses("java.lang.Runnable");
        expectIllegalState(() -> b.uses("java.lang.Runnable"));
    }

    static void testAutomaticModuleUsesThrowsIllegalStateException() throws Exception {
        final ModuleDescriptor.Builder b = ModuleDescriptor.newAutomaticModule("auto1");
        expectIllegalState(() -> b.uses("java.lang.Runnable"));
    }

    static void expectIllegalState(Action action) throws Exception {
        try {
            action.run();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected");
    }
}
