/*
 * @test
 * @summary Verify ModuleDescriptor.Builder.version(Version) rejects null, stores the given version, returns the same builder, and keeps the last version set
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testNullVersionThrowsNPE();
        testVersionIsStoredAndBuilderIsReturned();
        testLastVersionWins();
    }

    static void testNullVersionThrowsNPE() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m.test");
        try {
            b.version(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null version");
    }

    static void testVersionIsStoredAndBuilderIsReturned() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m.test");
        ModuleDescriptor.Version v = ModuleDescriptor.Version.parse("1.2.3");
        ModuleDescriptor.Builder returned = b.version(v);
        if (returned != b) {
            throw new RuntimeException("version(Version) must return the same Builder");
        }
        ModuleDescriptor md = b.build();
        if (!md.version().isPresent()) {
            throw new RuntimeException("version not present after version(Version)");
        }
        if (!md.version().get().equals(v)) {
            throw new RuntimeException("unexpected version: " + md.version().get());
        }
    }

    static void testLastVersionWins() {
        ModuleDescriptor.Version v1 = ModuleDescriptor.Version.parse("1.0");
        ModuleDescriptor.Version v2 = ModuleDescriptor.Version.parse("2.0");
        ModuleDescriptor md = ModuleDescriptor.newModule("m.test")
                .version(v1)
                .version(v2)
                .build();
        if (!md.version().isPresent() || !md.version().get().equals(v2)) {
            throw new RuntimeException("expected last version to win: " + md.version());
        }
    }
}
