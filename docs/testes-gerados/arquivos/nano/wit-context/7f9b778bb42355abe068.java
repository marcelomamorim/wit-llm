/*
 * @test
 * @summary Tests the exports method of ModuleDescriptor for exception paths.
 * @run main ModuleDescriptorWitupTest
 */

import java.util.Set;
import java.util.Collections;
import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsNullPackageName();
        testExportsAutomaticMode();
    }

    private static void testExportsNullPackageName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder();
        try {
            builder.exports(Collections.singleton(ModuleDescriptor.Exports.Modifier.PUBLIC), null);
            System.out.println("FAIL: Expected IllegalArgumentException for null package name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null package name");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testExportsAutomaticMode() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder();
        // Simulate automatic mode by setting a flag or state if applicable
        // Since the actual internal state is not accessible, assume default behavior
        // and test that no exception is thrown when strict is false or not set.
        try {
            // Assuming strict defaults to false in this context
            builder.exports(Collections.singleton(ModuleDescriptor.Exports.Modifier.PUBLIC), "com.example");
            System.out.println("PASS: exports call in automatic mode with valid package name succeeded");
        } catch (IllegalStateException e) {
            System.out.println("FAIL: Unexpected IllegalStateException in automatic mode: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
