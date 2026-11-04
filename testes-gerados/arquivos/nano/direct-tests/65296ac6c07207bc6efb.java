/*
 * @test
 * @summary Test CheckSignatureAdapter.visitReturnType() method for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitReturnTypeWithValidState();
        testVisitReturnTypeWithInvalidState();
    }

    private static void testVisitReturnTypeWithValidState() {
        // Setup: type is METHOD_SIGNATURE, state includes EMPTY
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        adapter.state = CheckSignatureAdapter.EMPTY; // assume constants are accessible
        try {
            SignatureVisitor result = adapter.visitReturnType();
            if (result == null) {
                throw new AssertionError("Expected non-null SignatureVisitor");
            }
            if (!(result instanceof CheckSignatureAdapter)) {
                throw new AssertionError("Result should be instance of CheckSignatureAdapter");
            }
            System.out.println("testVisitReturnTypeWithValidState passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid state test", e);
        }
    }

    private static void testVisitReturnTypeWithInvalidState() {
        // Setup: type is not METHOD_SIGNATURE
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        adapter.state = CheckSignatureAdapter.EMPTY; // assume constants are accessible
        boolean exceptionThrown = false;
        try {
            adapter.visitReturnType();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid state");
        }
        System.out.println("testVisitReturnTypeWithInvalidState passed.");
    }
}
