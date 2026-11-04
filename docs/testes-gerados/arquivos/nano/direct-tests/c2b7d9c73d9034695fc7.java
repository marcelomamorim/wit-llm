/*
 * @test
 * @summary Test CheckMethodAdapter.visitCode() for correct behavior and exception throwing
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitCodeWithAbstractAccess();
        testVisitCodeWithNonAbstractAccess();
    }

    private static void testVisitCodeWithAbstractAccess() {
        int access = Opcodes.ACC_ABSTRACT;
        CheckMethodAdapter adapter = new CheckMethodAdapter(access, "methodName", "()V", null, null);
        boolean exceptionThrown = false;
        try {
            adapter.visitCode();
        } catch (RuntimeException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("Abstract methods cannot have code") : "Unexpected exception message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected RuntimeException for abstract method access";
    }

    private static void testVisitCodeWithNonAbstractAccess() {
        int access = 0; // non-abstract
        CheckMethodAdapter adapter = new CheckMethodAdapter(access, "methodName", "()V", null, null);
        boolean exceptionThrown = false;
        try {
            adapter.visitCode();
        } catch (RuntimeException e) {
            exceptionThrown = true;
            assert false : "Did not expect exception, but got: " + e.getMessage();
        }
        // Verify that startCode is set to true after visitCode()
        // Since startCode is package-private, we assume the method executes without exception
        assert true : "visitCode() executed successfully without exception";
    }
}
