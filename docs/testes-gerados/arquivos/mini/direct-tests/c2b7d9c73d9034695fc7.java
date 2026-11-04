/*
 * @test
 * @summary Test visitCode method of CheckMethodAdapter for abstract and non-abstract methods
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visitCodeCalled = false;

        public DummyMethodVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitCode() {
            visitCodeCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitCodeNonAbstract();
        testVisitCodeAbstractThrows();
    }

    private static void testVisitCodeNonAbstract() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);

        // access without ACC_ABSTRACT bit set
        setAccess(cma, 0);

        cma.visitCode();

        if (!dmv.visitCodeCalled) {
            throw new RuntimeException("visitCode was not called on super MethodVisitor");
        }
    }

    private static void testVisitCodeAbstractThrows() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);

        // set ACC_ABSTRACT bit
        setAccess(cma, Opcodes.ACC_ABSTRACT);

        try {
            cma.visitCode();
            throw new RuntimeException("Expected RuntimeException for abstract method not thrown");
        } catch (RuntimeException e) {
            if (!"Abstract methods cannot have code".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    // Reflection helper to set protected 'access' field in CheckMethodAdapter
    private static void setAccess(CheckMethodAdapter cma, int access) {
        try {
            java.lang.reflect.Field f = CheckMethodAdapter.class.getDeclaredField("access");
            f.setAccessible(true);
            f.setInt(cma, access);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set access field", e);
        }
    }
}
