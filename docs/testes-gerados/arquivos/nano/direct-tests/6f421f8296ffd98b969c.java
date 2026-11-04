/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInnerClassType for correct behavior and exception throwing
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInnerClassTypeWithValidState();
        testVisitInnerClassTypeWithInvalidState();
    }

    private static void testVisitInnerClassTypeWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, null);
        // Set internal state to CLASS_SIGNATURE to allow visitInnerClassType
        setState(adapter, CheckSignatureAdapter.CLASS_SIGNATURE);
        try {
            adapter.visitInnerClassType("InnerClassName");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid state", e);
        }
    }

    private static void testVisitInnerClassTypeWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(0, null);
        // Set internal state to an invalid state (not CLASS_TYPE)
        setState(adapter, -1);
        boolean exceptionThrown = false;
        try {
            adapter.visitInnerClassType("InnerClassName");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }

    private static void setState(CheckSignatureAdapter adapter, int state) {
        try {
            java.lang.reflect.Field field = CheckSignatureAdapter.class.getDeclaredField("state");
            field.setAccessible(true);
            field.setInt(adapter, state);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set internal state via reflection", e);
        }
    }
}
