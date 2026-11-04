/*
 * @test
 * @summary Verifies CheckModuleAdapter.visitEnd completes and delegates visitEnd to the wrapped ModuleVisitor
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {
    public static void main(String[] args) {
        testVisitEndWithoutDelegate();
        testVisitEndDelegatesOnce();
    }

    static void testVisitEndWithoutDelegate() {
        CheckModuleAdapter cma = new CheckModuleAdapter(null, false);
        cma.visitEnd();
    }

    static void testVisitEndDelegatesOnce() {
        final int[] calls = {0};
        ModuleVisitor mv = new ModuleVisitor(Opcodes.ASM6) {
            @Override
            public void visitEnd() {
                calls[0]++;
            }
        };
        CheckModuleAdapter cma = new CheckModuleAdapter(mv, true);
        cma.visitEnd();
        if (calls[0] != 1) {
            throw new RuntimeException("expected delegate visitEnd once, got " + calls[0]);
        }
    }
}
