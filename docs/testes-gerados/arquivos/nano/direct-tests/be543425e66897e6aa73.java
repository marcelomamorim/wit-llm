/*
 * @test
 * @summary Test the 'opens' method of ModuleDescriptor.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testOpensWithEmptySet();
        testOpensWithNull();
    }

    static void testOpensWithEmptySet() {
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder();
            // Since 'opens' returns a Builder, test that calling 'opens' with a package name returns a Builder
            ModuleDescriptor.Builder result = new ModuleDescriptor().opens("");
            if (result == null) {
                throw new AssertionError("opens with empty string returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testOpensWithEmptySet", e);
        }
    }

    static void testOpensWithNull() {
        try {
            ModuleDescriptor.Builder result = new ModuleDescriptor().opens(null);
            if (result == null) {
                throw new AssertionError("opens with null returned null");
            }
        } catch (NullPointerException e) {
            // Expected, as passing null should throw NullPointerException
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testOpensWithNull", e);
        }
    }
}
