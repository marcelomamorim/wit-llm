/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInterface() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceValidState();
        testVisitInterfaceInvalidState();
    }

    private static void testVisitInterfaceValidState() {
        // Create a CheckSignatureAdapter with state SUPER to allow visitInterface call
        // We use the protected constructor to set state to SUPER (1)
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, CheckSignatureAdapter.TYPE_SIGNATURE, null) {
            {
                // forcibly set state to SUPER (1) to allow visitInterface
                this.state = 1; // SUPER
            }
        };

        SignatureVisitor sv = adapter.visitInterface();
        if (sv == null) {
            throw new AssertionError("visitInterface() returned null SignatureVisitor");
        }
        if (!(sv instanceof CheckSignatureAdapter)) {
            throw new AssertionError("visitInterface() did not return a CheckSignatureAdapter instance");
        }
    }

    private static void testVisitInterfaceInvalidState() {
        // Create a CheckSignatureAdapter with state not SUPER (e.g. 0)
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, CheckSignatureAdapter.TYPE_SIGNATURE, null) {
            {
                this.state = 0; // not SUPER
            }
        };

        try {
            adapter.visitInterface();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
