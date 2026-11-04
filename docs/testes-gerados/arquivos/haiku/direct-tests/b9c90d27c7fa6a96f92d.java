/*
 * @test
 * @summary Verify CheckSignatureAdapter.visitExceptionType throws IllegalStateException when the adapter is not in the RETURN state
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testFreshMethodAdapterThrows();
        testFreshTypeAdapterThrows();
        testRepeatedCallsStillThrow();
    }

    static void testFreshMethodAdapterThrows() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        expectIllegalState(a);
    }

    static void testFreshTypeAdapterThrows() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        expectIllegalState(a);
    }

    static void testRepeatedCallsStillThrow() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        expectIllegalState(a);
        expectIllegalState(a);
    }

    static void expectIllegalState(CheckSignatureAdapter a) {
        try {
            a.visitExceptionType();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException from visitExceptionType");
    }
}
