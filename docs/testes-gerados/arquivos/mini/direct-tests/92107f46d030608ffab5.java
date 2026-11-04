/*
 * @test
 * @summary Test CheckSignatureAdapter.visitArrayType() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitArrayTypeValid();
        testVisitArrayTypeInvalidState();
        testVisitArrayTypeNullVisitor();
    }

    private static void testVisitArrayTypeValid() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // state is initially EMPTY (0), type is TYPE_SIGNATURE (2), so visitArrayType should succeed
        SignatureVisitor sv = csa.visitArrayType();
        if (sv == null) {
            throw new AssertionError("visitArrayType returned null SignatureVisitor");
        }
        if (!(sv instanceof CheckSignatureAdapter)) {
            throw new AssertionError("visitArrayType did not return a CheckSignatureAdapter instance");
        }
    }

    private static void testVisitArrayTypeInvalidState() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // forcibly set state to non-EMPTY to trigger IllegalStateException
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(csa, 1); // SIMPLE_TYPE
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }
        try {
            csa.visitArrayType();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitArrayTypeNullVisitor() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // state is EMPTY and type is TYPE_SIGNATURE, sv is null, so visitArrayType returns a new CheckSignatureAdapter with null sv
        SignatureVisitor sv = csa.visitArrayType();
        if (sv == null) {
            throw new AssertionError("visitArrayType returned null SignatureVisitor when sv is null");
        }
    }
}
