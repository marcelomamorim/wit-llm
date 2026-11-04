/*
 * @test
 * @summary Verifies ModuleDescriptor.Builder.version(Version) stores the given version, replaces a previous version, and rejects null.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;
import java.util.Optional;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testSetsVersion();
        testReplacesPreviousVersion();
        testNullVersionThrowsNPE();
    }

    static void testSetsVersion() {
        Version v = Version.parse("1.2.3");
        ModuleDescriptor md = ModuleDescriptor.newModule("m").version(v).build();
        if (!md.version().equals(Optional.of(v))) {
            throw new RuntimeException("version not set: " + md.version());
        }
        if (!md.version().get().toString().equals("1.2.3")) {
            throw new RuntimeException("unexpected version string: " + md.version().get());
        }
    }

    static void testReplacesPreviousVersion() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m");
        b.version("1.0.0");
        Version v2 = Version.parse("2.0");
        ModuleDescriptor md = b.version(v2).build();
        if (!md.version().equals(Optional.of(v2))) {
            throw new RuntimeException("previous version not replaced: " + md.version());
        }
        if (!md.version().get().toString().equals("2.0")) {
            throw new RuntimeException("unexpected version after replace: " + md.version().get());
        }
    }

    static void testNullVersionThrowsNPE() {
        try {
            ModuleDescriptor.newModule("m").version((Version) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null version");
    }
}
