/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.parse(String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testParseValidVersion();
        testParseNullVersion();
    }

    private static void testParseValidVersion() {
        String versionString = "1.2.3";
        Version version = ModuleDescriptor.parse(versionString);
        if (!version.toString().equals(versionString)) {
            throw new AssertionError("Expected version string '" + versionString + "' but got '" + version.toString() + "'");
        }
    }

    private static void testParseNullVersion() {
        try {
            ModuleDescriptor.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
