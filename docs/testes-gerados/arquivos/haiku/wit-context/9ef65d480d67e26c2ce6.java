/*
 * @test
 * @summary WITUP-derived checks for CheckSignatureAdapter.visitInterface state handling
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 *          java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceOnFreshAdapterThrows();
        testVisitInterfaceAfterSuperclassReturnsAdapter();
    }

    static void testVisitInterfaceOnFreshAdapterThrows() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            csa.visitInterface();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected: state is not SUPER before visitSuperclass
        }
    }

    static void testVisitInterfaceAfterSuperclassReturnsAdapter() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        csa.visitSuperclass();
        SignatureVisitor v = csa.visitInterface();
        if (v == null) {
            throw new RuntimeException("visitInterface returned null");
        }
        if (!(v instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("unexpected visitor type: " + v.getClass());
        }
    }
}
