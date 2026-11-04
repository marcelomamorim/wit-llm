/*
 * @test
 * @summary Verify GeneratorAdapter.loadThis behavior for static and non-static methods
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.commons
 * @run main GeneratorAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;

public class GeneratorAdapterWitupTest {

    static class Recorder extends MethodVisitor {
        int count = 0;
        int lastOpcode = -1;
        int lastVar = -1;

        Recorder() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            count++;
            lastOpcode = opcode;
            lastVar = var;
        }
    }

    public static void main(String[] args) throws Exception {
        testStaticMethodThrows();
        testInstanceMethodEmitsALOAD0(Opcodes.ACC_PUBLIC);
        testInstanceMethodEmitsALOAD0(0);
    }

    static void testStaticMethodThrows() {
        Recorder r = new Recorder();
        GeneratorAdapter ga = new GeneratorAdapter(r, Opcodes.ACC_STATIC, "m", "()V");
        boolean thrown = false;
        try {
            ga.loadThis();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException for static method");
        }
        if (r.count != 0) {
            throw new RuntimeException("no instruction expected when loadThis fails, got " + r.count);
        }
    }

    static void testInstanceMethodEmitsALOAD0(int access) {
        Recorder r = new Recorder();
        GeneratorAdapter ga = new GeneratorAdapter(r, access, "m", "()V");
        ga.loadThis();
        if (r.count != 1) {
            throw new RuntimeException("expected exactly one instruction, got " + r.count);
        }
        if (r.lastOpcode != Opcodes.ALOAD) {
            throw new RuntimeException("expected ALOAD, got " + r.lastOpcode);
        }
        if (r.lastVar != 0) {
            throw new RuntimeException("expected local index 0, got " + r.lastVar);
        }
    }
}
