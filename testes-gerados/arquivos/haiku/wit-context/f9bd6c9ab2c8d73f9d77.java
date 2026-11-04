/*
 * @test
 * @summary Verify CheckMethodAdapter.visitAttribute exception behavior for null attributes and visits after visitEnd
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testNullAttributeBeforeEndThrowsIllegalArgument();
        testVisitAttributeAfterVisitEndThrowsIllegalState();
    }

    private static void testNullAttributeBeforeEndThrowsIllegalArgument() {
        MethodVisitor target = new MethodVisitor(Opcodes.ASM5) {};
        CheckMethodAdapter cma = new CheckMethodAdapter(target);
        Throwable t = captureThrowable(() -> cma.visitAttribute(null));
        if (!(t instanceof IllegalArgumentException)) {
            throw new RuntimeException("Expected IllegalArgumentException but got: " + t);
        }
    }

    private static void testVisitAttributeAfterVisitEndThrowsIllegalState() {
        MethodVisitor target = new MethodVisitor(Opcodes.ASM5) {};
        CheckMethodAdapter cma = new CheckMethodAdapter(target);
        cma.visitEnd();
        Throwable t = captureThrowable(() -> cma.visitAttribute(null));
        if (!(t instanceof IllegalStateException)) {
            throw new RuntimeException("Expected IllegalStateException but got: " + t);
        }
    }

    private interface Action {
        void run();
    }

    private static Throwable captureThrowable(Action action) {
        try {
            action.run();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }
}
