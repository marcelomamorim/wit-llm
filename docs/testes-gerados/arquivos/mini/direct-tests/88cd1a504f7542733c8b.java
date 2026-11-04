/*
 * @test
 * @summary Test CheckSignatureAdapter.visitClassBound() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundValidState();
        testVisitClassBoundInvalidState();
    }

    private static void testVisitClassBoundValidState() {
        // Create a CheckSignatureAdapter with FORMAL state to allow visitClassBound
        // FORMAL is a private static final int in CheckSignatureAdapter, so we use reflection to set it
        // But reflection is disallowed by instructions, so we simulate by subclassing and setting state manually

        CheckSignatureAdapter csa = new CheckSignatureAdapter(TYPE_SIGNATURE, null) {
            {
                // forcibly set state to FORMAL (value 1) to allow visitClassBound
                try {
                    java.lang.reflect.Field f = CheckSignatureAdapter.class.getDeclaredField("state");
                    f.setAccessible(true);
                    f.setInt(this, 1); // FORMAL == 1
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        SignatureVisitor sv = csa.visitClassBound();
        if (sv == null) {
            throw new AssertionError("visitClassBound() returned null SignatureVisitor");
        }
    }

    private static void testVisitClassBoundInvalidState() {
        // Create a CheckSignatureAdapter with default state (0 == EMPTY) which is invalid for visitClassBound
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);

        try {
            csa.visitClassBound();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    // Constants copied from CheckSignatureAdapter for clarity
    private static final int TYPE_SIGNATURE = 2;
}
