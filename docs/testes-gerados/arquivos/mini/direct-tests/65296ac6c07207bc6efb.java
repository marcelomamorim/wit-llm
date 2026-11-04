/*
 * @test
 * @summary Test CheckSignatureAdapter.visitReturnType() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitReturnTypeValidState();
        testVisitReturnTypeInvalidType();
        testVisitReturnTypeInvalidState();
    }

    private static void testVisitReturnTypeValidState() {
        // Setup: type = METHOD_SIGNATURE, state includes PARAM (bit 8)
        // Use protected constructor to set initial state to PARAM
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(327680, CheckSignatureAdapter.METHOD_SIGNATURE, null) {
            {
                this.state = PARAM;
            }
        };

        SignatureVisitor sv = adapter.visitReturnType();
        if (sv == null) {
            throw new AssertionError("visitReturnType() returned null SignatureVisitor");
        }
    }

    private static void testVisitReturnTypeInvalidType() {
        // Setup: type != METHOD_SIGNATURE (e.g., CLASS_SIGNATURE), state includes PARAM
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(327680, CheckSignatureAdapter.CLASS_SIGNATURE, null) {
            {
                this.state = PARAM;
            }
        };

        try {
            adapter.visitReturnType();
            throw new AssertionError("Expected IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testVisitReturnTypeInvalidState() {
        // Setup: type = METHOD_SIGNATURE, but state does not include any of EMPTY, FORMAL, BOUND, PARAM
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(327680, CheckSignatureAdapter.METHOD_SIGNATURE, null) {
            {
                this.state = 0; // no bits set
            }
        };

        try {
            adapter.visitReturnType();
            throw new AssertionError("Expected IllegalArgumentException for invalid state");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    // Constants copied from CheckSignatureAdapter source for state bits
    private static final int EMPTY = 0x01;
    private static final int FORMAL = 0x02;
    private static final int BOUND = 0x04;
    private static final int PARAM = 0x08;
    private static final int RETURN = 0x10;
}