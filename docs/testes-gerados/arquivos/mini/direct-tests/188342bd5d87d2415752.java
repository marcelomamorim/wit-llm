/*
 * @test
 * @summary Test CheckModuleAdapter.visitUse method behavior
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitUseValid();
        testVisitUseDuplicate();
        testVisitUseNullService();
        testVisitUseAfterEnd();
    }

    private static void testVisitUseValid() {
        CheckModuleAdapter cma = new CheckModuleAdapter(new ModuleVisitor(Opcodes.ASM6) {}, false);
        // valid internal name
        cma.visitUse("java/lang/Runnable");
        // second different valid internal name
        cma.visitUse("java/util/List");
    }

    private static void testVisitUseDuplicate() {
        CheckModuleAdapter cma = new CheckModuleAdapter(new ModuleVisitor(Opcodes.ASM6) {}, false);
        cma.visitUse("java/lang/Runnable");
        try {
            cma.visitUse("java/lang/Runnable");
            throw new Error("Expected IllegalStateException for duplicate use");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitUseNullService() {
        CheckModuleAdapter cma = new CheckModuleAdapter(new ModuleVisitor(Opcodes.ASM6) {}, false);
        try {
            cma.visitUse(null);
            throw new Error("Expected IllegalArgumentException for null service");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testVisitUseAfterEnd() {
        CheckModuleAdapter cma = new CheckModuleAdapter(new ModuleVisitor(Opcodes.ASM6) {}, false);
        cma.visitEnd();
        try {
            cma.visitUse("java/lang/Runnable");
            throw new Error("Expected IllegalStateException for visitUse after visitEnd");
        } catch (IllegalStateException ise) {
            // expected
        }
    }
}
