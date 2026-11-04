/*
 * @test
 * @summary Tests for java.lang.module.ModuleDescriptor.exports(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithNullPackageName();
        testExportsWithValidPackageName();
    }

    public static void testExportsWithNullPackageName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder();
        try {
            builder.exports((String) null);
            System.out.println("FAIL: Expected IllegalArgumentException for null package name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null package name");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testExportsWithValidPackageName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder();
        try {
            ModuleDescriptor.Builder.Exports exports = builder.exports("com.example");
            if (exports != null) {
                System.out.println("PASS: exports returned non-null for valid package name");
            } else {
                System.out.println("FAIL: exports returned null for valid package name");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
