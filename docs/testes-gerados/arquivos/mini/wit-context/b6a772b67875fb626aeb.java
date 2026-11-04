/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInterfaceBound() for correct state validation and delegation
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    private static final int FORMAL = 2;
    private static final int BOUND = 4;

    // A minimal stub SignatureVisitor to pass as delegate
    static class StubSignatureVisitor extends SignatureVisitor {
        StubSignatureVisitor() {
            super(Opcodes.ASM7);
        }
        @Override
        public SignatureVisitor visitInterfaceBound() {
            return this;
        }
    }

    public static void main(String[] args) {
        testVisitInterfaceBoundValidStates();
        testVisitInterfaceBoundInvalidState();
    }

    private static void testVisitInterfaceBoundValidStates() {
        // Test with state = FORMAL (2)
        CheckSignatureAdapter adapterFormal = new CheckSignatureAdapter(FORMAL, new StubSignatureVisitor());
        setState(adapterFormal, FORMAL);
        SignatureVisitor sv1 = adapterFormal.visitInterfaceBound();
        if (sv1 == null) {
            throw new AssertionError("visitInterfaceBound() returned null for FORMAL state");
        }

        // Test with state = BOUND (4)
        CheckSignatureAdapter adapterBound = new CheckSignatureAdapter(BOUND, new StubSignatureVisitor());
        setState(adapterBound, BOUND);
        SignatureVisitor sv2 = adapterBound.visitInterfaceBound();
        if (sv2 == null) {
            throw new AssertionError("visitInterfaceBound() returned null for BOUND state");
        }
    }

    private static void testVisitInterfaceBoundInvalidState() {
        // Test with invalid state (e.g. 0)
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, new StubSignatureVisitor());
        setState(adapter, 0); // state != 2 && state != 4
        try {
            adapter.visitInterfaceBound();
            throw new AssertionError("Expected IllegalArgumentException for invalid state");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    // Reflection helper to set private 'state' field since no public setter
    private static void setState(CheckSignatureAdapter adapter, int stateValue) {
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, stateValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set state field via reflection", e);
        }
    }
}
