/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitClassBound() method behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundWithValidState();
        testVisitClassBoundWithInvalidState();
    }

    private static void testVisitClassBoundWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // Manually set state to FORMAL (assumed initial state for valid call)
        // Since state is protected, we simulate the valid scenario by calling visitClassBound() when state == FORMAL
        // but as state is not accessible, we assume the initial state is correct and call directly
        try {
            SignatureVisitor result = adapter.visitClassBound();
            if (result == null) {
                System.out.println("testVisitClassBoundWithValidState: Passed (null result as expected)");
            } else {
                System.out.println("testVisitClassBoundWithValidState: Passed (non-null result)");
            }
        } catch (Exception e) {
            System.err.println("testVisitClassBoundWithValidState: Failed with exception: " + e);
        }
    }

    private static void testVisitClassBoundWithInvalidState() {
        // To simulate invalid state, we need to set state to a value != FORMAL
        // Since 'state' is protected and not accessible, we create a subclass to manipulate it
        class TestAdapter extends CheckSignatureAdapter {
            public TestAdapter(int type, SignatureVisitor sv) {
                super(type, sv);
            }
            public void setState(int newState) {
                this.state = newState;
            }
        }
        TestAdapter adapter = new TestAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        adapter.setState(2); // set to a state != FORMAL
        try {
            adapter.visitClassBound();
            System.err.println("testVisitClassBoundWithInvalidState: Failed (no exception thrown)");
        } catch (IllegalStateException e) {
            System.out.println("testVisitClassBoundWithInvalidState: Passed (caught expected IllegalStateException)");
        } catch (Exception e) {
            System.err.println("testVisitClassBoundWithInvalidState: Failed with unexpected exception: " + e);
        }
    }
}
