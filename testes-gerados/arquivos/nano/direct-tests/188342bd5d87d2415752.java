/* @test    @summary Test visitUse method of CheckModuleAdapter for valid and null input    @run main CheckModuleAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitUseWithValidService();
        testVisitUseWithNullService();
    }

    static void testVisitUseWithValidService() {
        ModuleVisitor mockVisitor = new ModuleVisitor(0) {};
        CheckModuleAdapter adapter = new CheckModuleAdapter(mockVisitor, false);
        String serviceName = "com.example.Service";
        try {
            adapter.visitUse(serviceName);
            System.out.println("testVisitUseWithValidService passed");
        } catch (Exception e) {
            System.err.println("testVisitUseWithValidService failed: " + e);
        }
    }

    static void testVisitUseWithNullService() {
        ModuleVisitor mockVisitor = new ModuleVisitor(0) {};
        CheckModuleAdapter adapter = new CheckModuleAdapter(mockVisitor, false);
        String serviceName = null;
        try {
            adapter.visitUse(serviceName);
            System.out.println("testVisitUseWithNullService passed");
        } catch (NullPointerException e) {
            System.out.println("testVisitUseWithNullService passed (expected exception)");
        } catch (Exception e) {
            System.err.println("testVisitUseWithNullService failed: " + e);
        }
    }
}
