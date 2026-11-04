/*
 * @test
 * @summary Test CheckModuleAdapter.visitRequire for null module input and end state
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitRequireNullModule();
        testVisitRequireEndState();
    }

    private static void testVisitRequireNullModule() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        try {
            adapter.visitRequire(null, Opcodes.ACC_TRANSITIVE, "1.0");
            System.out.println("testVisitRequireNullModule: FAILED (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitRequireNullModule: PASSED");
        } catch (Throwable t) {
            System.out.println("testVisitRequireNullModule: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testVisitRequireEndState() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, true); // simulate end state
        try {
            adapter.visitRequire("moduleName", Opcodes.ACC_TRANSITIVE, "1.0");
            System.out.println("testVisitRequireEndState: FAILED (no exception)");
        } catch (IllegalStateException e) {
            System.out.println("testVisitRequireEndState: PASSED");
        } catch (Throwable t) {
            System.out.println("testVisitRequireEndState: FAILED (unexpected exception: " + t + ")");
        }
    }
}
