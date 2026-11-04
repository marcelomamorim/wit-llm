/*
 * @test
 * @summary Test visitTypeArgument() method of CheckSignatureAdapter for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeArgumentWithValidState();
        testVisitTypeArgumentWithInvalidState();
    }

    private static void testVisitTypeArgumentWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter();
        adapter.state = CheckSignatureAdapter.CLASS_TYPE; // set to valid state
        adapter.sv = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public void visitTypeArgument() {
                visited = true;
            }
        };
        adapter.visitTypeArgument();
        // No exception should be thrown, and sv.visitTypeArgument() should be called
        System.out.println("testVisitTypeArgumentWithValidState passed.");
    }

    private static void testVisitTypeArgumentWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter();
        adapter.state = -1; // invalid state
        boolean exceptionThrown = false;
        try {
            adapter.visitTypeArgument();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown.");
        }
        System.out.println("testVisitTypeArgumentWithInvalidState passed.");
    }
}
