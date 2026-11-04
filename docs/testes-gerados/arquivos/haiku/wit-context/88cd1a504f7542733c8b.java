/*
 * @test
 * @summary Checks that CheckSignatureAdapter.visitClassBound() rejects calls outside the FORMAL state and rejects a repeated call
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 *          java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundWithoutFormalParameterThrows();
        testVisitClassBoundAfterFormalParameterReturnsVisitor();
        testRepeatedVisitClassBoundThrows();
    }

    static void testVisitClassBoundWithoutFormalParameterThrows() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            adapter.visitClassBound();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when state != FORMAL");
        }
    }

    static void testVisitClassBoundAfterFormalParameterReturnsVisitor() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.visitFormalTypeParameter("T");
        Object bound = adapter.visitClassBound();
        if (bound == null) {
            throw new RuntimeException("visitClassBound() returned null in FORMAL state");
        }
    }

    static void testRepeatedVisitClassBoundThrows() {
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
            throw new RuntimeException("Expected IllegalStateException on second visitClassBound()");
        }
    }
}
