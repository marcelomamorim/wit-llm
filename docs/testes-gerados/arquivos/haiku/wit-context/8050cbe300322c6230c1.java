/*
 * @test
 * @summary Verify that CheckSignatureAdapter.visitTypeArgument() rejects calls made outside the CLASS_TYPE state
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testFreshAdapterThrowsIllegalState();
        testAfterBaseTypeThrowsIllegalState();
    }

    static void testFreshAdapterThrowsIllegalState() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        boolean thrown = false;
        try {
            csa.visitTypeArgument();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from visitTypeArgument() on fresh adapter");
        }
    }

    static void testAfterBaseTypeThrowsIllegalState() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitBaseType('I');
        boolean thrown = false;
        try {
            csa.visitTypeArgument();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from visitTypeArgument() after visitBaseType()");
        }
    }
}
