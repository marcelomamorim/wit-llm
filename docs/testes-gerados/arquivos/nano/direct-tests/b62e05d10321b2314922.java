/* @test    @summary Test visitEnd method of CheckSignatureAdapter for proper state transition and exception handling    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndWithInvalidState();
        testVisitEndWithValidState();
    }

    private static void testVisitEndWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, null);
        // Force state to an invalid value to trigger IllegalStateException
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, -1); // invalid state
            adapter.visitEnd();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testVisitEndWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, null);
        try {
            // Set state to CLASS_TYPE to simulate valid state
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, CheckSignatureAdapter.CLASS_SIGNATURE);
            // Call visitEnd, should not throw
            adapter.visitEnd();
            System.out.println("PASS: visitEnd executed successfully with valid state.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during valid visitEnd: " + e);
        }
    }
}
