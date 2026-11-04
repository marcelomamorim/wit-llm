/*
 * @test
 * @summary Checks CheckSignatureAdapter.visitEnd state validation
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndOnFreshClassSignatureThrows();
        testVisitEndAfterClassTypeSucceeds();
        testSecondVisitEndThrows();
    }

    static void testVisitEndOnFreshClassSignatureThrows() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            csa.visitEnd();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("visitEnd on fresh CLASS_SIGNATURE adapter must throw IllegalStateException");
        }
    }

    static void testVisitEndAfterClassTypeSucceeds() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitClassType("java/lang/String");
        try {
            csa.visitEnd();
        } catch (IllegalStateException e) {
            throw new RuntimeException("visitEnd after visitClassType must not throw", e);
        }
    }

    static void testSecondVisitEndThrows() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitClassType("java/lang/String");
        csa.visitEnd();
        boolean thrown = false;
        try {
            csa.visitEnd();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("second visitEnd must throw IllegalStateException because state is END");
        }
    }
}
