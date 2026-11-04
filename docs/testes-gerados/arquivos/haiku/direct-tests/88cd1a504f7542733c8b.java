/*
 * @test
 * @summary Verify CheckSignatureAdapter.visitClassBound state checks on the public API
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundOnFreshAdapterThrowsIllegalState();
        testVisitClassBoundAfterFormalTypeParameterReturnsAdapter();
        testSecondVisitClassBoundThrowsIllegalState();
    }

    static void testVisitClassBoundOnFreshAdapterThrowsIllegalState() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            adapter.visitClassBound();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on fresh adapter");
        }
    }

    static void testVisitClassBoundAfterFormalTypeParameterReturnsAdapter() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.visitFormalTypeParameter("T");
        SignatureVisitor v = adapter.visitClassBound();
        if (v == null) {
            throw new RuntimeException("visitClassBound returned null");
        }
        if (!(v instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("Returned visitor is not a CheckSignatureAdapter");
        }
    }

    static void testSecondVisitClassBoundThrowsIllegalState() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.visitFormalTypeParameter("T");
        adapter.visitClassBound();
        boolean thrown = false;
        try {
            adapter.visitClassBound();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on second visitClassBound");
        }
    }
}
