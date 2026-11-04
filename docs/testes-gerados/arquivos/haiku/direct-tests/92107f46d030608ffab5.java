/*
 * @test
 * @summary Tests CheckSignatureAdapter.visitArrayType state checks and returned visitor
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static boolean throwsISE(Runnable r) {
        try {
            r.run();
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        testTypeSignatureFreshReturnsAdapter();
        testSecondCallThrows();
        testClassSignatureThrows();
        testMethodSignatureThrows();
        testReturnedVisitorAcceptsArrayType();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testTypeSignatureFreshReturnsAdapter() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        Object v = csa.visitArrayType();
        check(v != null, "visitArrayType on fresh TYPE_SIGNATURE must not return null");
        check(v instanceof CheckSignatureAdapter, "returned visitor must be a CheckSignatureAdapter");
    }

    static void testSecondCallThrows() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitArrayType();
        check(throwsISE(() -> csa.visitArrayType()),
              "second visitArrayType on same adapter must throw IllegalStateException");
    }

    static void testClassSignatureThrows() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        check(throwsISE(() -> csa.visitArrayType()),
              "visitArrayType on CLASS_SIGNATURE must throw IllegalStateException");
    }

    static void testMethodSignatureThrows() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        check(throwsISE(() -> csa.visitArrayType()),
              "visitArrayType on METHOD_SIGNATURE must throw IllegalStateException");
    }

    static void testReturnedVisitorAcceptsArrayType() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        Object inner = csa.visitArrayType();
        check(inner instanceof CheckSignatureAdapter, "returned visitor must be a CheckSignatureAdapter");
        CheckSignatureAdapter innerCsa = (CheckSignatureAdapter) inner;
        Object nested = innerCsa.visitArrayType();
        check(nested != null, "nested visitArrayType on returned TYPE_SIGNATURE adapter must not return null");
    }
}
