/*
 * @test
 * @summary Checks state and type validation of CheckSignatureAdapter.visitTypeVariable
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testTypeSignatureAcceptsTypeVariable();
        testClassSignatureRejectsTypeVariable();
        testMethodSignatureRejectsTypeVariable();
        testSecondTypeVariableRejected();
    }

    static void testTypeSignatureAcceptsTypeVariable() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitTypeVariable("T");
    }

    static void testClassSignatureRejectsTypeVariable() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        expectIllegalState(csa, "T");
    }

    static void testMethodSignatureRejectsTypeVariable() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        expectIllegalState(csa, "T");
    }

    static void testSecondTypeVariableRejected() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitTypeVariable("T");
        expectIllegalState(csa, "U");
    }

    static void expectIllegalState(CheckSignatureAdapter csa, String name) {
        try {
            csa.visitTypeVariable(name);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected");
    }
}
