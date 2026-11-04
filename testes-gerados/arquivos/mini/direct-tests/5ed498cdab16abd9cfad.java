/*
 * @test
 * @summary Test ModuleDescriptor.Builder.exports(Exports) method behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Exports;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsNormalModule();
        testExportsDuplicatePackage();
        testExportsAutomaticModuleThrows();
    }

    private static void testExportsNormalModule() {
        Builder builder = new Builder("test.module");
        Exports exports = new Exports("com.example.pkg", false, null);
        Builder returned = builder.exports(exports);
        if (returned != builder) {
            throw new AssertionError("exports() should return the same Builder instance");
        }
    }

    private static void testExportsDuplicatePackage() {
        Builder builder = new Builder("test.module");
        Exports exports1 = new Exports("com.example.pkg", false, null);
        builder.exports(exports1);
        Exports exports2 = new Exports("com.example.pkg", false, null);
        try {
            builder.exports(exports2);
            throw new AssertionError("Expected IllegalStateException for duplicate exported package");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("already declared")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testExportsAutomaticModuleThrows() {
        Builder builder = new Builder("auto.module");
        // Set automatic flag to true via reflection since no public API to do so
        try {
            java.lang.reflect.Field automaticField = Builder.class.getDeclaredField("automatic");
            automaticField.setAccessible(true);
            automaticField.setBoolean(builder, true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to set automatic flag", e);
        }
        Exports exports = new Exports("com.example.pkg", false, null);
        try {
            builder.exports(exports);
            throw new AssertionError("Expected IllegalStateException for automatic module exports");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Automatic modules cannot declare")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
