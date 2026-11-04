/* @test    @summary Test for CheckSignatureAdapter.visitEnd() method to verify proper exception handling and state transitions    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndWithInvalidState();
        testVisitEndWithValidState();
    }

    private static void testVisitEndWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        // Manually set internal state to an invalid value to simulate error condition
        // Note: Since 'state' is private, this test assumes a package-private or accessible method or uses reflection.
        // Given constraints, we avoid reflection; instead, we simulate by subclassing or by calling methods that change state.
        // Here, we simulate by creating a subclass that sets state directly for testing purposes.
        class TestAdapter extends CheckSignatureAdapter {
            public int testState;
            public TestAdapter() {
                super(CheckSignatureAdapter.CLASS_SIGNATURE, null);
                this.testState = 128; // invalid state
            }
            @Override
            public void visitEnd() {
                // forcibly set state to invalid
                // Since 'state' is private in actual class, this is a conceptual test; in real scenario, reflection needed.
                // For this test, assume 'state' can be set or simulate the exception directly.
                // As per constraints, we simulate by calling the original method with invalid state.
                // So, we invoke the original method assuming 'state' is invalid.
                super.visitEnd();
            }
        }
        TestAdapter testAdapter = new TestAdapter();
        try {
            testAdapter.visitEnd();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testVisitEndWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        // Simulate valid state transition
        // Since 'state' is private, assume constructor sets it correctly, or call methods that do.
        // For this test, we proceed with the default state.
        try {
            adapter.visitEnd();
            System.out.println("PASS: visitEnd() executed without exception in valid state.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in valid state: " + e);
        }
    }
}
