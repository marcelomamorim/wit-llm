/*
 * @test
 * @summary Test for ModuleDescriptor.parse(String v) method.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testParseNull();
        testParseEmptyString();
    }

    private static void testParseNull() {
        try {
            ModuleDescriptor.Version.parse(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    private static void testParseEmptyString() {
        try {
            ModuleDescriptor.Version.parse("");
            System.err.println("Failed: Expected IllegalArgumentException for empty string");
        } catch (IllegalArgumentException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for empty string: " + e);
        }
    }
}
