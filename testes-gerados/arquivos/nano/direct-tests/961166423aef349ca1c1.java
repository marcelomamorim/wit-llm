/*
 * @test
 * @summary Test parsing of version string in ModuleDescriptor
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testParseValidVersion();
        testParseEmptyString();
        testParseNullInput();
        testParseInvalidFormat();
    }

    static void testParseValidVersion() {
        String versionStr = "1.0.0";
        ModuleDescriptor.Version version = ModuleDescriptor.parse(versionStr);
        assert version != null : "Parsed version should not be null";
        assert version.toString().equals(versionStr) : "Version string mismatch";
    }

    static void testParseEmptyString() {
        String versionStr = "";
        try {
            ModuleDescriptor.Version version = ModuleDescriptor.parse(versionStr);
            assert false : "Parsing empty string should throw";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testParseNullInput() {
        String versionStr = null;
        try {
            ModuleDescriptor.Version version = ModuleDescriptor.parse(versionStr);
            assert false : "Parsing null should throw";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseInvalidFormat() {
        String versionStr = "abc";
        try {
            ModuleDescriptor.Version version = ModuleDescriptor.parse(versionStr);
            assert false : "Parsing invalid format should throw";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
