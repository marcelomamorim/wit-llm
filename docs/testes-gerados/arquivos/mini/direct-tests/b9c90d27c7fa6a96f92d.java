/*
 * @test
 * @summary Test CheckSignatureAdapter.visitExceptionType() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitExceptionTypeThrowsIfStateNotReturn();
        testVisitExceptionTypeReturnsCheckSignatureAdapter();
    }

    private static void testVisitExceptionTypeThrowsIfStateNotReturn() {
        // Create a subclass to set state != RETURN (RETURN = 3) to test exception
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null) {
            // forcibly set state to 0 (initial) to simulate invalid state
            {
                try {
                    java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
                    stateField.setAccessible(true);
                    stateField.setInt(this, 0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        try {
            csa.visitExceptionType();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitExceptionTypeReturnsCheckSignatureAdapter() {
        // Create a CheckSignatureAdapter with state = RETURN (3) to allow visitExceptionType
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            // Set state to RETURN (3) via reflection
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(csa, 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SignatureVisitor sv = csa.visitExceptionType();
        if (sv == null) {
            throw new AssertionError("visitExceptionType() returned null");
        }
        if (!(sv instanceof CheckSignatureAdapter)) {
            throw new AssertionError("visitExceptionType() did not return CheckSignatureAdapter instance");
        }
    }
}
