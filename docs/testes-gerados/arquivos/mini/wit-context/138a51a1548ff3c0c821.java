/*
 * @test
 * @summary Test GeneratorAdapter.loadThis() behavior including IllegalStateException for static methods
 * @run main GeneratorAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Method;

public class GeneratorAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visited = false;
        int opcodeVisited = -1;
        int varIndexVisited = -1;

        DummyMethodVisitor() {
            super(Opcodes.ASM7);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            visited = true;
            opcodeVisited = opcode;
            varIndexVisited = var;
        }
    }

    public static void main(String[] args) {
        testLoadThisNonStatic();
        testLoadThisStaticThrows();
    }

    private static void testLoadThisNonStatic() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        // Use public constructor: GeneratorAdapter(MethodVisitor mv, int access, String name, String desc)
        // access without ACC_STATIC
        int access = Opcodes.ACC_PUBLIC;
        String name = "methodName";
        String desc = "()V";

        GeneratorAdapter ga = new GeneratorAdapter(dmv, access, name, desc);

        ga.loadThis();

        if (!dmv.visited) {
            throw new Error("Expected visitVarInsn to be called");
        }
        if (dmv.opcodeVisited != Opcodes.ALOAD) {
            throw new Error("Expected opcode ALOAD but got " + dmv.opcodeVisited);
        }
        if (dmv.varIndexVisited != 0) {
            throw new Error("Expected var index 0 but got " + dmv.varIndexVisited);
        }
    }

    private static void testLoadThisStaticThrows() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        // access with ACC_STATIC bit set
        int access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;
        String name = "staticMethod";
        String desc = "()V";

        GeneratorAdapter ga = new GeneratorAdapter(dmv, access, name, desc);

        try {
            ga.loadThis();
            throw new Error("Expected IllegalStateException for static method");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("no 'this' pointer")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }
}
