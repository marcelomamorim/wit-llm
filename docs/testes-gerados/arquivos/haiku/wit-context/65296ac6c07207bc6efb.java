/*
 * @test
 * @summary Witup-derived checks for CheckSignatureAdapter.visitReturnType
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testMethodSignatureReturnsAdapter();
        expectIllegalArgument(CheckSignatureAdapter.CLASS_SIGNATURE);
        expectIllegalArgument(CheckSignatureAdapter.TYPE_SIGNATURE);
        testSecondReturnTypeThrows();
        testReturnedAdapterRejectsNestedReturnType();
    }

    static void testMethodSignatureReturnsAdapter() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        SignatureVisitor r = cv.visitReturnType();
        if (r == null) {
            throw new RuntimeException("visitReturnType returned null");
        }
        if (!(r instanceof CheckSignatureAdapter)) {
            throw new RuntimeException("returned visitor is not a CheckSignatureAdapter");
        }
    }

    static void expectIllegalArgument(int type) {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(type, null);
        try {
            cv.visitReturnType();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for type " + type);
    }

    static void testSecondReturnTypeThrows() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        cv.visitReturnType();
        try {
            cv.visitReturnType();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException on second visitReturnType call");
    }

    static void testReturnedAdapterRejectsNestedReturnType() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        SignatureVisitor r = cv.visitReturnType();
        try {
            r.visitReturnType();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException from nested visitReturnType");
    }
}
