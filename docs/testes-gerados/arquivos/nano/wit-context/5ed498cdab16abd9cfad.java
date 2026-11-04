/*
 * @test
 * @summary Tests the exports method of ModuleDescriptor for normal behavior.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithValidExports();
        testExportsWithNullExports();
        testExportsWhenAlreadyDeclared();
    }

    static void testExportsWithValidExports() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder("test.module");
        Exports e = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        try {
            builder.exports(e);
            System.out.println("testExportsWithValidExports passed");
        } catch (Exception ex) {
            System.err.println("testExportsWithValidExports failed: " + ex);
        }
    }

    static void testExportsWithNullExports() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder("test.module");
        try {
            builder.exports(null);
            System.err.println("testExportsWithNullExports failed: no exception thrown");
        } catch (NullPointerException npe) {
            System.out.println("testExportsWithNullExports passed");
        } catch (Exception ex) {
            System.err.println("testExportsWithNullExports failed: " + ex);
        }
    }

    static void testExportsWhenAlreadyDeclared() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.Builder.newBuilder("test.module");
        Exports e1 = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        Exports e2 = new Exports() {
            @Override
            public String source() {
                return "com.example";
            }
        };
        try {
            builder.exports(e1);
            builder.exports(e2);
            System.err.println("testExportsWhenAlreadyDeclared failed: no exception thrown on second export");
        } catch (IllegalStateException ise) {
            System.out.println("testExportsWhenAlreadyDeclared passed");
        } catch (Exception ex) {
            System.err.println("testExportsWhenAlreadyDeclared failed: " + ex);
        }
    }
}
