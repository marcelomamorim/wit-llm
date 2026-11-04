/*
 * @test
 * @summary Witup generated tests for CheckSignatureAdapter.visitFormalTypeParameter exception paths
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        testValidFormalTypeParameterInClassSignature();
        testConsecutiveFormalTypeParameters();
        testValidFormalTypeParameterInMethodSignature();
        testTypeSignatureRejectsFormalTypeParameter();
        testNullNameRejected();
        testEmptyNameRejected();
    }

    static void testValidFormalTypeParameterInClassSignature() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.visitFormalTypeParameter("T");
    }

    static void testConsecutiveFormalTypeParameters() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.visitFormalTypeParameter("T");
        adapter.visitFormalTypeParameter("U");
    }

    static void testValidFormalTypeParameterInMethodSignature() {
        CheckSignatureAdapter adapter =
                new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        adapter.visitFormalTypeParameter("E");
    }

    static void testTypeSignatureRejectsFormalTypeParameter() {
        expectThrows(IllegalStateException.class, () -> {
            CheckSignatureAdapter adapter =
                    new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
            adapter.visitFormalTypeParameter("T");
        });
    }

    static void testNullNameRejected() {
        expectThrows(IllegalArgumentException.class, () -> {
            CheckSignatureAdapter adapter =
                    new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
            adapter.visitFormalTypeParameter(null);
        });
    }

    static void testEmptyNameRejected() {
        expectThrows(IllegalArgumentException.class, () -> {
            CheckSignatureAdapter adapter =
                    new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
            adapter.visitFormalTypeParameter("");
        });
    }

    static void expectThrows(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + expected.getName() + " was not thrown");
    }
}
