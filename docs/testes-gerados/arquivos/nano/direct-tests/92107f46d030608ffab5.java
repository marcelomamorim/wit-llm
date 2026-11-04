/*
 * @test
 * @summary Test CheckSignatureAdapter.visitArrayType() method for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitArrayTypeNormal();
        testVisitArrayTypeInvalidState();
    }

    private static void testVisitArrayTypeNormal() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public SignatureVisitor visitArrayType() {
                visited = true;
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        SignatureVisitor result = adapter.visitArrayType();
        assert result == mockVisitor : "Expected the returned visitor to be the mockVisitor";
        System.out.println("testVisitArrayTypeNormal passed.");
    }

    private static void testVisitArrayTypeInvalidState() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitArrayType() {
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        // Manually set internal state to trigger exception
        try {
            // Reflection is needed to set private fields, but since the context restricts reflection,
            // we simulate the invalid state by calling visitArrayType() twice.
            adapter.visitArrayType(); // First call, should succeed
            adapter.visitArrayType(); // Second call, should throw IllegalStateException
            assert false : "Expected IllegalStateException to be thrown";
        } catch (IllegalStateException e) {
            System.out.println("testVisitArrayTypeInvalidState passed.");
        }
    }
}
