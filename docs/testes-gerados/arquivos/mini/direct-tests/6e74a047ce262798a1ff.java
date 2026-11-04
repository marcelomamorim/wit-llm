/*
 * @test
 * @summary Test visitParameter method behavior in MethodVisitor
 * @run main MethodVisitorWitupTest
 */

public class MethodVisitorWitupTest {

    static class DummyMethodVisitor extends jdk.internal.org.objectweb.asm.MethodVisitor {
        boolean called = false;
        String lastName = null;
        int lastAccess = -1;

        public DummyMethodVisitor(int api) {
            super(api);
        }

        @Override
        public void visitParameter(String name, int access) {
            called = true;
            lastName = name;
            lastAccess = access;
        }
    }

    public static void main(String[] args) {
        testVisitParameterWithApiLessThanASM5();
        testVisitParameterWithNullMv();
        testVisitParameterWithNonNullMv();
    }

    private static void testVisitParameterWithApiLessThanASM5() {
        // api < Opcodes.ASM5 (which is 5)
        final int api = 4;
        jdk.internal.org.objectweb.asm.MethodVisitor mv = new jdk.internal.org.objectweb.asm.MethodVisitor(api) {};
        try {
            mv.visitParameter("param", 123);
            throw new RuntimeException("Expected RuntimeException not thrown for api < ASM5");
        } catch (RuntimeException e) {
            // expected
        }
    }

    private static void testVisitParameterWithNullMv() {
        // api >= ASM5, mv == null, should not throw and do nothing
        final int api = 5;
        jdk.internal.org.objectweb.asm.MethodVisitor mv = new jdk.internal.org.objectweb.asm.MethodVisitor(api) {};
        // mv field is null by default
        mv.visitParameter("param", 123);
        // no exception means pass
    }

    private static void testVisitParameterWithNonNullMv() {
        // api >= ASM5, mv != null, should delegate call
        final int api = 5;
        DummyMethodVisitor delegate = new DummyMethodVisitor(api);
        jdk.internal.org.objectweb.asm.MethodVisitor mv = new jdk.internal.org.objectweb.asm.MethodVisitor(api, delegate) {};
        mv.visitParameter("paramName", 456);
        if (!delegate.called) {
            throw new RuntimeException("Delegate visitParameter was not called");
        }
        if (!"paramName".equals(delegate.lastName)) {
            throw new RuntimeException("Delegate visitParameter called with wrong name");
        }
        if (delegate.lastAccess != 456) {
            throw new RuntimeException("Delegate visitParameter called with wrong access");
        }
    }
}
