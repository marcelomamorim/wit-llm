/*
 * @test
 * @summary Verifies that CheckSignatureAdapter.visitExceptionType() requires a preceding return type and succeeds after it
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        CheckSignatureAdapter fresh = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        boolean thrown = false;
        try {
            fresh.visitExceptionType();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException when visitExceptionType is called before visitReturnType");
        }

        CheckSignatureAdapter ordered = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        ordered.visitReturnType();
        if (ordered.visitExceptionType() == null) {
            throw new RuntimeException("visitExceptionType returned null after visitReturnType");
        }
    }
}
