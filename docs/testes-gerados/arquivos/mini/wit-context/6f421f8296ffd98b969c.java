/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInnerClassType for correct exception and normal behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInnerClassTypeThrowsIllegalStateException();
        testVisitInnerClassTypeThrowsIllegalArgumentException();
        testVisitInnerClassTypeDelegatesToSv();
    }

    private static void testVisitInnerClassTypeThrowsIllegalStateException() {
        // Create a CheckSignatureAdapter with type != CLASS_SIGNATURE to trigger IllegalStateException
        SignatureVisitor dummySv = null;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, dummySv);
        try {
            csa.visitInnerClassType("ValidName");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitInnerClassTypeThrowsIllegalArgumentException() {
        // Create a CheckSignatureAdapter with type == CLASS_SIGNATURE
        // Passing null or empty string should cause IllegalArgumentException from checkIdentifier
        SignatureVisitor dummySv = null;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dummySv);

        // null name
        try {
            csa.visitInnerClassType(null);
            throw new AssertionError("Expected IllegalArgumentException for null name not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // empty name
        try {
            csa.visitInnerClassType("");
            throw new AssertionError("Expected IllegalArgumentException for empty name not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testVisitInnerClassTypeDelegatesToSv() {
        // Create a dummy SignatureVisitor that records if visitInnerClassType was called
        class DummySignatureVisitor extends SignatureVisitor {
            boolean called = false;
            String receivedName = null;

            DummySignatureVisitor() {
                super(0);
            }

            @Override
            public SignatureVisitor visitInnerClassType(String name) {
                called = true;
                receivedName = name;
                return this;
            }
        }

        DummySignatureVisitor dummySv = new DummySignatureVisitor();
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dummySv);

        String testName = "InnerName";
        csa.visitInnerClassType(testName);

        if (!dummySv.called) {
            throw new AssertionError("visitInnerClassType was not delegated to SignatureVisitor");
        }
        if (!testName.equals(dummySv.receivedName)) {
            throw new AssertionError("visitInnerClassType received wrong name: expected '" + testName + "' but got '" + dummySv.receivedName + "'");
        }
    }
}
