/*
 * @test
 * @summary Checks CheckSignatureAdapter.visitInterfaceBound state validation and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        CheckSignatureAdapter fresh = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            fresh.visitInterfaceBound();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("visitInterfaceBound in invalid state must throw IllegalArgumentException");
        }

        CheckSignatureAdapter formal = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        formal.visitFormalTypeParameter("T");
        Object bound = formal.visitInterfaceBound();
        if (bound == null) {
            throw new RuntimeException("visitInterfaceBound after formal type parameter must return a visitor");
        }
        if (!(bound instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("visitInterfaceBound must return a CheckSignatureAdapter");
        }
    }
}
