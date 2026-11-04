/*
 * @test
 * @summary CheckSignatureAdapter.visitEnd requires a preceding class type state and rejects a second call
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testVisitEndAfterClassType();
        testSecondVisitEndThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testVisitEndAfterClassType() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitClassType("java/lang/Object");
        boolean threw = false;
        try {
            csa.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(!threw, "visitEnd after visitClassType should not throw");
    }

    static void testSecondVisitEndThrows() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitClassType("java/lang/Object");
        csa.visitEnd();
        boolean threw = false;
        try {
            csa.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "second visitEnd should throw IllegalStateException");
    }
}
