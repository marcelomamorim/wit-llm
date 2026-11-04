/*
 * @test
 * @summary Test GeneratorAdapter.loadThis() behavior for static and non-static methods
 * @run main GeneratorAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class GeneratorAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        int lastOpcode = -1;
        int lastVar = -1;

        DummyMethodVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            lastOpcode = opcode;
            lastVar = var;
        }
    }

    public static void main(String[] args) {
        testLoadThisNonStatic();
        testLoadThisStaticThrows();
    }

    private static void testLoadThisNonStatic() {
        DummyMethodVisitor mv = new DummyMethodVisitor();
        // access without ACC_STATIC
        int access = Opcodes.ACC_PUBLIC;
        GeneratorAdapter ga = new GeneratorAdapter(access, "methodName", "()V", mv);
        ga.loadThis();
        if (mv.lastOpcode != Opcodes.ALOAD || mv.lastVar != 0) {
            throw new AssertionError("Expected visitVarInsn with ALOAD and var 0, got opcode: " + mv.lastOpcode + ", var: " + mv.lastVar);
        }
    }

    private static void testLoadThisStaticThrows() {
        DummyMethodVisitor mv = new DummyMethodVisitor();
        // access with ACC_STATIC
        int access = Opcodes.ACC_STATIC;
        GeneratorAdapter ga = new GeneratorAdapter(access, "methodName", "()V", mv);
        try {
            ga.loadThis();
            throw new AssertionError("Expected IllegalStateException for static method");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!"no 'this' pointer within static method".equals(msg)) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
