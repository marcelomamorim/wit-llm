/*
 * @test
 * @summary Checks state guards of CheckSignatureAdapter.visitArrayType
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testFirstCallReturnsAdapter();
        testSecondCallOnSameTypeSignatureThrows();
        testClassSignatureThrows();
        testReturnedAdapterAllowsNestedArray();
    }

    static void testFirstCallReturnsAdapter() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        if (a.visitArrayType() == null) {
            throw new RuntimeException("visitArrayType returned null on first call");
        }
    }

    static void testSecondCallOnSameTypeSignatureThrows() {
        final CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        a.visitArrayType();
        expectIllegalState(new Runnable() {
            public void run() {
                a.visitArrayType();
            }
        });
    }

    static void testClassSignatureThrows() {
        final CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        expectIllegalState(new Runnable() {
            public void run() {
                a.visitArrayType();
            }
        });
    }

    static void testReturnedAdapterAllowsNestedArray() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        CheckSignatureAdapter nested = (CheckSignatureAdapter) a.visitArrayType();
        if (nested == null) {
            throw new RuntimeException("returned adapter is null");
        }
        if (nested.visitArrayType() == null) {
            throw new RuntimeException("nested visitArrayType returned null");
        }
    }

    static void expectIllegalState(Runnable r) {
        try {
            r.run();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException was not thrown");
    }
}
