/*
 * @test
 * @summary Test CheckSignatureAdapter.visitFormalTypeParameter for valid and invalid usage.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidVisitFormalTypeParameter();
        testInvalidVisitFormalTypeParameter();
    }

    private static void testValidVisitFormalTypeParameter() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitFormalTypeParameter("T");
        } catch (Exception e) {
            throw new AssertionError("Expected no exception for valid usage", e);
        }
    }

    private static void testInvalidVisitFormalTypeParameter() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // Simulate invalid state by calling visitFormalTypeParameter out of order or multiple times
        try {
            adapter.visitFormalTypeParameter("T");
            // Calling again to simulate invalid usage
            adapter.visitFormalTypeParameter("U");
            throw new AssertionError("Expected IllegalStateException for invalid usage")
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
