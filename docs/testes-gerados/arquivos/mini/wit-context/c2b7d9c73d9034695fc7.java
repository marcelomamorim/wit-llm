/*
 * @test
 * @summary Test visitCode method of CheckMethodAdapter for abstract method exception and normal flow
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visitCodeCalled = false;
        DummyMethodVisitor() {
            super(Opcodes.ASM7);
        }
        @Override
        public void visitCode() {
            visitCodeCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitCodeThrowsOnAbstract();
        testVisitCodeNormal();
    }

    private static void testVisitCodeThrowsOnAbstract() {
        // access with ACC_ABSTRACT bit set
        final int accessAbstract = Opcodes.ACC_ABSTRACT;
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(accessAbstract, "m", "()V", dmv, null);
        try {
            cma.visitCode();
            throw new Error("Expected RuntimeException for abstract method not thrown");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (!"Abstract methods cannot have code".equals(msg)) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testVisitCodeNormal() {
        // access without ACC_ABSTRACT bit set
        final int accessNormal = 0;
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(accessNormal, "m", "()V", dmv, null);
        cma.visitCode();
        if (!dmv.visitCodeCalled) {
            throw new Error("visitCode was not delegated to MethodVisitor");
        }
    }
}
