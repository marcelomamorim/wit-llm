/*
 * @test
 * @summary Verify CheckSignatureAdapter.visitInterface state checks and returned adapter
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testFreshClassAdapterThrowsIllegalState();
        testMethodAdapterThrowsIllegalState();
        testAfterSuperclassReturnsCheckAdapter();
    }

    static void testFreshClassAdapterThrowsIllegalState() {
        CheckSignatureAdapter a =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            a.visitInterface();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException on fresh adapter");
    }

    static void testMethodAdapterThrowsIllegalState() {
        CheckSignatureAdapter a =
                new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        try {
            a.visitInterface();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException on method adapter");
    }

    static void testAfterSuperclassReturnsCheckAdapter() {
        CheckSignatureAdapter a =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        a.visitSuperclass();
        Object v = a.visitInterface();
        if (v == null) {
            throw new RuntimeException("visitInterface returned null");
        }
        if (!(v instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("visitInterface did not return CheckSignatureAdapter");
        }
    }
}
