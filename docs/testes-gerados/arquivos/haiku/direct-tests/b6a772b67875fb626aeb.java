/*
 * @test
 * @summary Verify CheckSignatureAdapter.visitInterfaceBound state checks and returned adapter
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testInterfaceBoundRejectedInEmptyState();
        testInterfaceBoundAfterFormalTypeParameter();
    }

    static void testInterfaceBoundRejectedInEmptyState() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            csa.visitInterfaceBound();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("visitInterfaceBound must reject EMPTY state");
        }
    }

    static void testInterfaceBoundAfterFormalTypeParameter() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        csa.visitFormalTypeParameter("T");
        SignatureVisitor v = csa.visitInterfaceBound();
        if (v == null) {
            throw new RuntimeException("visitInterfaceBound returned null");
        }
        if (!(v instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("returned visitor must be a CheckSignatureAdapter");
        }
    }
}
