/*
 * @test
 * @summary Verifies MethodVisitor.visitParameter API version check and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {
    static final int ASM4_API = 262144;

    static class Recorder extends MethodVisitor {
        int calls;
        String lastName;
        int lastAccess;

        Recorder() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitParameter(String name, int access) {
            calls++;
            lastName = name;
            lastAccess = access;
        }
    }

    public static void main(String[] args) {
        testLowApiThrowsWithoutDelegate();
        testAsm5WithoutDelegateDoesNotThrow();
        testDelegation();
        testLowApiDoesNotDelegate();
    }

    static void testLowApiThrowsWithoutDelegate() {
        MethodVisitor mv = new MethodVisitor(ASM4_API) {};
        boolean threw = false;
        try {
            mv.visitParameter("p", 0);
        } catch (RuntimeException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected exception for api < ASM5");
        }
    }

    static void testAsm5WithoutDelegateDoesNotThrow() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM5) {};
        mv.visitParameter("p", 0);
    }

    static void testDelegation() {
        Recorder delegate = new Recorder();
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM5, delegate) {};
        mv.visitParameter("x", 8);
        if (delegate.calls != 1) {
            throw new RuntimeException("expected one delegated call, got " + delegate.calls);
        }
        if (!"x".equals(delegate.lastName)) {
            throw new RuntimeException("wrong name: " + delegate.lastName);
        }
        if (delegate.lastAccess != 8) {
            throw new RuntimeException("wrong access: " + delegate.lastAccess);
        }
    }

    static void testLowApiDoesNotDelegate() {
        Recorder delegate = new Recorder();
        MethodVisitor mv = new MethodVisitor(ASM4_API, delegate) {};
        boolean threw = false;
        try {
            mv.visitParameter("y", 1);
        } catch (RuntimeException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected exception for api < ASM5");
        }
        if (delegate.calls != 0) {
            throw new RuntimeException("delegate must not be called when api < ASM5");
        }
    }
}
