/*
 * @test
 * @summary Witup-derived checks for ModuleDescriptor.Version.parse(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testValidVersionRoundTrip();
        testEmptyVersionRejected();
    }

    static void testValidVersionRoundTrip() {
        ModuleDescriptor.Version v = ModuleDescriptor.Version.parse("1.2.3");
        if (!"1.2.3".equals(v.toString())) {
            throw new AssertionError("unexpected toString: " + v);
        }
    }

    static void testEmptyVersionRejected() {
        try {
            ModuleDescriptor.Version.parse("");
            throw new AssertionError("expected IllegalArgumentException for empty version");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
