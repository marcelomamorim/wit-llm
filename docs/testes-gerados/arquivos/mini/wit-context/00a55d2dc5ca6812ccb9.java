/*
 * @test
 * @summary Test CheckSignatureAdapter.visitClassType for correct exception and state handling
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    private static final int TYPE_SIGNATURE = CheckSignatureAdapter.TYPE_SIGNATURE;
    private static final int EMPTY = 0;
    private static final int CLASS_TYPE = 1;

    public static void main(String[] args) {
        testThrowsIllegalStateExceptionIfTypeOrStateInvalid();
        testThrowsIllegalArgumentExceptionIfNameNullOrEmpty();
        testStateChangeAndDelegateCall();
    }

    private static void testThrowsIllegalStateExceptionIfTypeOrStateInvalid() {
        // Create a CheckSignatureAdapter with type TYPE_SIGNATURE and initial state EMPTY
        // then forcibly set state to invalid value to simulate state != EMPTY
        // Since state is private, we simulate by creating with type != TYPE_SIGNATURE
        // Use type = CLASS_SIGNATURE (0) to cause type != TYPE_SIGNATURE (2)

        SignatureVisitor dummyVisitor = null;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dummyVisitor);

        // The initial state is EMPTY (0) but type is CLASS_SIGNATURE (0) != TYPE_SIGNATURE (2)
        // So visitClassType should throw IllegalStateException
        try {
            csa.visitClassType("ValidName");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testThrowsIllegalArgumentExceptionIfNameNullOrEmpty() {
        // Create with type=TYPE_SIGNATURE and initial state=EMPTY
        // call visitClassType with null and empty string to trigger IllegalArgumentException

        SignatureVisitor dummyVisitor = null;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(TYPE_SIGNATURE, dummyVisitor);

        // First call with null name
        try {
            csa.visitClassType(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null name");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Reset adapter to initial state for next call
        csa = new CheckSignatureAdapter(TYPE_SIGNATURE, dummyVisitor);

        // Call with empty string
        try {
            csa.visitClassType("");
            throw new RuntimeException("Expected IllegalArgumentException not thrown for empty name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testStateChangeAndDelegateCall() {
        // Create a dummy SignatureVisitor that records if visitClassType is called
        final boolean[] called = new boolean[1];
        SignatureVisitor sv = new SignatureVisitor(0) {
            @Override
            public void visitClassType(String name) {
                called[0] = true;
                if (!"TestName".equals(name)) {
                    throw new RuntimeException("Unexpected name passed to visitClassType: " + name);
                }
            }
        };

        CheckSignatureAdapter csa = new CheckSignatureAdapter(TYPE_SIGNATURE, sv);

        // Initially state is EMPTY, type is TYPE_SIGNATURE
        // call visitClassType with valid name
        csa.visitClassType("TestName");

        // After call, state should be CLASS_TYPE (1) - but state is private, so we test observable effect
        // The delegate's visitClassType should have been called
        if (!called[0]) {
            throw new RuntimeException("Delegate visitClassType was not called");
        }
    }
}
