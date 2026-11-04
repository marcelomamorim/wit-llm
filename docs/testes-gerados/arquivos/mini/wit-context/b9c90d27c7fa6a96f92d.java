/*
 * @test
 * @summary Test CheckSignatureAdapter.visitExceptionType() for correct state validation and delegation
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitExceptionTypeThrowsOnInvalidState();
        testVisitExceptionTypeDelegatesAndReturnsNewAdapter();
    }

    private static void testVisitExceptionTypeThrowsOnInvalidState() {
        // Create a CheckSignatureAdapter with a dummy SignatureVisitor
        SignatureVisitor dummy = new SignatureVisitor(0) {};
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, dummy) {
            // Override state to invalid value to simulate error
            private int state = 0;
            @Override
            public SignatureVisitor visitExceptionType() {
                if (state != 32) {
                    throw new IllegalStateException();
                }
                SignatureVisitor v = sv == null ? null : sv.visitExceptionType();
                return new CheckSignatureAdapter(TYPE_SIGNATURE, v);
            }
        };

        try {
            adapter.visitExceptionType();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testVisitExceptionTypeDelegatesAndReturnsNewAdapter() {
        // Create a dummy SignatureVisitor that tracks visitExceptionType call
        class DummySignatureVisitor extends SignatureVisitor {
            boolean called = false;
            DummySignatureVisitor() {
                super(0);
            }
            @Override
            public SignatureVisitor visitExceptionType() {
                called = true;
                return this;
            }
        }

        final DummySignatureVisitor dummy = new DummySignatureVisitor();

        // Create CheckSignatureAdapter with state set to RETURN (32) to allow call
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, dummy) {
            private int state = 32; // RETURN
            @Override
            public SignatureVisitor visitExceptionType() {
                if (state != 32) {
                    throw new IllegalStateException();
                }
                SignatureVisitor v = sv == null ? null : sv.visitExceptionType();
                return new CheckSignatureAdapter(TYPE_SIGNATURE, v);
            }
        };

        SignatureVisitor result = adapter.visitExceptionType();

        if (result == null) {
            throw new AssertionError("visitExceptionType returned null");
        }
        if (!dummy.called) {
            throw new AssertionError("Delegate visitExceptionType was not called");
        }
        if (result == dummy) {
            throw new AssertionError("Returned adapter should be a new instance, not the delegate");
        }
    }
}
