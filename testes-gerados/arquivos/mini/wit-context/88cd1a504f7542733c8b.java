/*
 * @test
 * @summary Test CheckSignatureAdapter.visitClassBound() for correct state handling and exception throwing
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundValidState();
        testVisitClassBoundInvalidState();
    }

    private static void testVisitClassBoundValidState() {
        // Create a CheckSignatureAdapter with state = FORMAL (0) to allow visitClassBound()
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null) {
            // Override state to FORMAL to simulate valid state
            {
                this.state = FORMAL;
            }
        };

        SignatureVisitor sv = adapter.visitClassBound();
        if (sv == null) {
            throw new AssertionError("visitClassBound() returned null SignatureVisitor when sv is null");
        }
    }

    private static void testVisitClassBoundInvalidState() {
        // Create a CheckSignatureAdapter with state != FORMAL to trigger IllegalStateException
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null) {
            // Override state to an invalid value (e.g. 1) to simulate invalid state
            {
                this.state = 1; // not FORMAL
            }
        };

        try {
            adapter.visitClassBound();
            throw new AssertionError("Expected IllegalStateException not thrown when state != FORMAL");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    // Constants copied from CheckSignatureAdapter for state values
    private static final int FORMAL = 0;
    private static final int BOUND = 1;
}
