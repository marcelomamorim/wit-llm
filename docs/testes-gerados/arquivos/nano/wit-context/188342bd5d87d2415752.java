/*
 * @test
 * @summary Test CheckModuleAdapter.visitUse method for normal and exceptional behavior
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitUseNormal();
        testVisitUseWithNullService();
        testVisitUseWithEmptyService();
    }

    private static void testVisitUseNormal() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        try {
            adapter.visitUse("com.example.Service");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal case", e);
        }
    }

    private static void testVisitUseWithNullService() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        try {
            adapter.visitUse(null);
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException for null service", e);
        }
    }

    private static void testVisitUseWithEmptyService() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        try {
            adapter.visitUse("");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException for empty service", e);
        }
    }
}
