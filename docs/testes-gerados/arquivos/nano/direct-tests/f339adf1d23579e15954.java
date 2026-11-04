/* @test    @summary Test the exports method of ModuleDescriptor for a given package name    @run main ModuleDescriptorWitupTest */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithValidPackage();
        testExportsWithEmptyPackage();
        testExportsWithNullPackage();
    }

    static void testExportsWithValidPackage() {
        String packageName = "com.example";
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        // Since the method is public, we can call it directly
        ModuleDescriptor.Builder result = null;
        try {
            result = new ModuleDescriptor().exports(packageName);
            assert result != null : "exports should return a Builder instance";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid package", e);
        }
    }

    static void testExportsWithEmptyPackage() {
        String packageName = "";
        ModuleDescriptor.Builder result = null;
        try {
            result = new ModuleDescriptor().exports(packageName);
            assert result != null : "exports should handle empty package name";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty package", e);
        }
    }

    static void testExportsWithNullPackage() {
        String packageName = null;
        try {
            new ModuleDescriptor().exports(packageName);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
