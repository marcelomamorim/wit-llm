/*
 * @test
 * @summary Verifies GeneratorAdapter.loadThis emits ALOAD 0 for instance methods and throws IllegalStateException for static methods
 * @modules java.base/jdk.internal.org.objectweb.asm.commons java.base/jdk.internal.org.objectweb.asm
 * @run main GeneratorAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;

public class GeneratorAdapterWitupTest {

    static final class RecordingVisitor extends MethodVisitor {
        int count;
        int lastOpcode = -1;
        int lastVar = -1;

        RecordingVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            count++;
            lastOpcode = opcode;
            lastVar = var;
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testInstanceMethodEmitsAload0() {
        RecordingVisitor rv = new RecordingVisitor();
        GeneratorAdapter ga = new GeneratorAdapter(rv, Opcodes.ACC_PUBLIC, "m", "()V");
        ga.loadThis();
        if (rv.count != 1) {
            fail("expected 1 var instruction, got " + rv.count);
        }
        if (rv.lastOpcode != Opcodes.ALOAD) {
            fail("expected ALOAD opcode, got " + rv.lastOpcode);
        }
        if (rv.lastVar != 0) {
            fail("expected local index 0, got " + rv.lastVar);
        }
    }

    static void testStaticMethodThrows() {
        RecordingVisitor rv = new RecordingVisitor();
        GeneratorAdapter ga = new GeneratorAdapter(rv, Opcodes.ACC_STATIC, "s", "()V");
        boolean thrown = false;
        try {
            ga.loadThis();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected IllegalStateException for static method");
        }
        if (rv.count != 0) {
            fail("no instruction should be emitted for static method, got " + rv.count);
        }
    }

    public static void main(String[] args) {
        testInstanceMethodEmitsAload0();
        testStaticMethodThrows();
    }
}
