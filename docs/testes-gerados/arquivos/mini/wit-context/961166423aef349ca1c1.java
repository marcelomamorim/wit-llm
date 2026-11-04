/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.parse(String) for valid and invalid inputs
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testValidVersion();
        testNullInput();
        testEmptyStringInput();
        System.out.println("All tests passed.");
    }

    private static void testValidVersion() {
        // Valid version string should create a Version instance without exception
        Version v = ModuleDescriptor.parse("1.2.3");
        if (v == null) {
            throw new AssertionError("Version.parse returned null for valid input");
        }
    }

    private static void testNullInput() {
        // According to WIT expath and code, parse(null) should throw IllegalArgumentException
        try {
            ModuleDescriptor.parse(null);
            throw new AssertionError("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testEmptyStringInput() {
        // According to WIT expath, parse("") should throw IllegalArgumentException
        try {
            ModuleDescriptor.parse("");
            throw new AssertionError("Expected IllegalArgumentException for empty string input");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
