/*
 * @test
 * @summary Checks visitFormalTypeParameter accepts valid class signature calls and rejects type signatures with IllegalStateException
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @modules java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        csa.visitFormalTypeParameter("T");
        csa.visitFormalTypeParameter("U");

        CheckSignatureAdapter tsa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        boolean thrown = false;
        try {
            tsa.visitFormalTypeParameter("T");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException for TYPE_SIGNATURE");
        }
    }
}
