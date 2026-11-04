/*
 * @test
 * @summary Witup-generated checks for MethodVisitor.visitParameter API guard and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    static class Recorder extends MethodVisitor {
        String name;
        int access;
        int calls;

        Recorder() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitParameter(String n, int a) {
            name = n;
            access = a;
            calls++;
        }
    }

    public static void main(String[] args) {
        testUnsupportedApiThrowsRuntimeException();
        testSupportedApiWithoutDelegateDoesNotThrow();
        testSupportedApiDelegatesToNextVisitor();
    }

    static void testUnsupportedApiThrowsRuntimeException() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM4);
        boolean thrown = false;
        try {
            mv.visitParameter("p", 0);
        } catch (RuntimeException e) {
            if (e.getClass() != RuntimeException.class) {
                throw new RuntimeException("unexpected exception type: " + e.getClass());
            }
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected RuntimeException for api < ASM5");
        }
    }

    static void testSupportedApiWithoutDelegateDoesNotThrow() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM5, null);
        mv.visitParameter("p", 1);
    }

    static void testSupportedApiDelegatesToNextVisitor() {
        Recorder rec = new Recorder();
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM5, rec);
        mv.visitParameter("x", 7);
        if (rec.calls != 1) {
            throw new RuntimeException("expected one delegated call, got " + rec.calls);
        }
        if (!"x".equals(rec.name)) {
            throw new RuntimeException("unexpected name: " + rec.name);
        }
        if (rec.access != 7) {
            throw new RuntimeException("unexpected access: " + rec.access);
        }
    }
}
