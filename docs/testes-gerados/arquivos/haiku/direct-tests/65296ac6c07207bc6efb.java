/*
 * @test
 * @summary Checks CheckSignatureAdapter.visitReturnType() state checks, delegation and returned adapter type
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature java.base/jdk.internal.org.objectweb.asm
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    static class RecordingVisitor extends SignatureVisitor {
        int returnCalls = 0;

        RecordingVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public SignatureVisitor visitReturnType() {
            returnCalls++;
            return this;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testMethodSignatureReturnsVisitor() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        SignatureVisitor v = cv.visitReturnType();
        check(v != null, "visitReturnType must return non-null for METHOD_SIGNATURE");
        check(v instanceof CheckSignatureAdapter, "returned visitor must be a CheckSignatureAdapter");
    }

    static void testClassSignatureThrows() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            cv.visitReturnType();
            throw new RuntimeException("expected IllegalArgumentException for CLASS_SIGNATURE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testTypeSignatureThrows() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            cv.visitReturnType();
            throw new RuntimeException("expected IllegalArgumentException for TYPE_SIGNATURE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testSecondCallThrows() {
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        cv.visitReturnType();
        try {
            cv.visitReturnType();
            throw new RuntimeException("expected IllegalArgumentException on second visitReturnType");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testDelegateCalledOnce() {
        RecordingVisitor delegate = new RecordingVisitor();
        CheckSignatureAdapter cv = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, delegate);
        SignatureVisitor v = cv.visitReturnType();
        check(v != null, "visitReturnType must return non-null with a delegate");
        check(delegate.returnCalls == 1,
              "delegate visitReturnType must be called exactly once, was " + delegate.returnCalls);
    }

    public static void main(String[] args) {
        testMethodSignatureReturnsVisitor();
        testClassSignatureThrows();
        testTypeSignatureThrows();
        testSecondCallThrows();
        testDelegateCalledOnce();
    }
}
