/*
 * @test
 * @summary Test CheckSignatureAdapter.visitEnd() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    static class DummySignatureVisitor extends SignatureVisitor {
        boolean visitEndCalled = false;

        public DummySignatureVisitor() {
            super(Opcodes.ASM7);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitEndWithValidState();
        testVisitEndWithInvalidState();
    }

    private static void testVisitEndWithValidState() {
        DummySignatureVisitor dsv = new DummySignatureVisitor();
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dsv);

        // Set state to CLASS_TYPE (0) to allow visitEnd to proceed without exception
        // Using reflection to set private field 'state' since no public setter is available
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(csa, 0); // CLASS_TYPE
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        csa.visitEnd();

        // After visitEnd, state should be END (1)
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            int stateValue = stateField.getInt(csa);
            if (stateValue != 1) {
                throw new AssertionError("State after visitEnd should be END (1), but was " + stateValue);
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        if (!dsv.visitEndCalled) {
            throw new AssertionError("Delegate SignatureVisitor.visitEnd() was not called");
        }
    }

    private static void testVisitEndWithInvalidState() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);

        // Set state to an invalid value (e.g., 2) to cause IllegalStateException
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(csa, 2); // invalid state
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        boolean threw = false;
        try {
            csa.visitEnd();
        } catch (IllegalStateException ise) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("visitEnd() did not throw IllegalStateException when state != CLASS_TYPE");
        }
    }
}
