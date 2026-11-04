/*
 * @test
 * @summary Test CheckSignatureAdapter.visitClassType behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassTypeValid();
        testVisitClassTypeInvalidState();
    }

    private static void testVisitClassTypeValid() {
        // Create a CheckSignatureAdapter with type TYPE_SIGNATURE and state EMPTY
        // We use the protected constructor to set state to EMPTY (0) manually
        // but since state is private, we rely on public constructor and initial state
        // The public constructor sets state to EMPTY by default

        SignatureVisitor dummyVisitor = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, dummyVisitor);

        // The initial state is EMPTY (0) and type is TYPE_SIGNATURE (2), so visitClassType should succeed
        csa.visitClassType("java/lang/String");

        // No exception means pass
    }

    private static void testVisitClassTypeInvalidState() {
        // Create a CheckSignatureAdapter with type TYPE_SIGNATURE
        SignatureVisitor dummyVisitor = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, dummyVisitor);

        // We need to put the adapter in a state different from EMPTY to cause IllegalStateException
        // Since state is private and no setter, we simulate by calling visitClassType twice
        // The first call sets state to CLASS_TYPE, second call should throw

        csa.visitClassType("java/lang/String");

        boolean threw = false;
        try {
            csa.visitClassType("java/lang/String");
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException not thrown");
        }
    }
}
