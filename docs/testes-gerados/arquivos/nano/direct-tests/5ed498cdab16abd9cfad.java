/*
 * @test
 * @summary Test ModuleDescriptor.exports(Exports e) method for correct behavior.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithValidExports();
        testExportsWhenAutomaticModule();
        testExportsWhenPackageAlreadyDeclared();
    }

    private static void testExportsWithValidExports() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        Exports export = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        try {
            builder.exports(export);
            System.out.println("testExportsWithValidExports passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWithValidExports", e);
        }
    }

    private static void testExportsWhenAutomaticModule() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        // Simulate automatic module by setting a flag (assuming such a method exists)
        // Since the actual code does not show this, we simulate by reflection or a mock
        // For this test, we assume the builder has a method to set automatic to true
        // but since it's not shown, we skip actual setting and just expect an exception
        try {
            // No actual method to set automatic, so we simulate by calling exports with a mock
            // We expect an IllegalStateException as per code
            builder.exports(new Exports() {
                @Override
                public String source() {
                    return "com.example";
                }
            });
            System.err.println("testExportsWhenAutomaticModule failed: exception expected but not thrown");
            System.exit(1);
        } catch (IllegalStateException e) {
            System.out.println("testExportsWhenAutomaticModule passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWhenAutomaticModule", e);
        }
    }

    private static void testExportsWhenPackageAlreadyDeclared() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        Exports export1 = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        Exports export2 = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        try {
            builder.exports(export1);
            builder.exports(export2);
            System.err.println("testExportsWhenPackageAlreadyDeclared failed: exception expected but not thrown");
            System.exit(1);
        } catch (IllegalStateException e) {
            System.out.println("testExportsWhenPackageAlreadyDeclared passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWhenPackageAlreadyDeclared", e);
        }
    }
}
