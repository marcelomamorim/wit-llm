/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitInterface() method.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceWithValidState();
        testVisitInterfaceWithInvalidState();
    }

    private static void testVisitInterfaceWithValidState() {
        // Setup: state == SUPER (assumed to be 0), sv != null
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            // minimal implementation
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.SUPER, mockVisitor);
        // Force state to SUPER
        try {
            // Reflection or direct access not used; assume constructor sets state properly
            // Call method
            SignatureVisitor result = adapter.visitInterface();
            if (result == null) {
                System.err.println("testVisitInterfaceWithValidState failed: result is null");
            } else {
                System.out.println("testVisitInterfaceWithValidState passed");
            }
        } catch (Exception e) {
            System.err.println("testVisitInterfaceWithValidState failed with exception: " + e);
        }
    }

    private static void testVisitInterfaceWithInvalidState() {
        // Setup: state != SUPER (simulate invalid state)
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            // minimal implementation
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.SUPER, mockVisitor);
        // Manually set state to an invalid value, e.g., 8
        // Since state is private, assume we can set it via reflection or constructor (not shown here)
        // For simplicity, simulate by subclassing or assuming constructor allows setting state
        // But as per constraints, we avoid reflection; so we simulate by subclassing
        class TestAdapter extends CheckSignatureAdapter {
            public int testState;
            public TestAdapter(int state, SignatureVisitor sv) {
                super(state, sv);
                this.testState = state;
            }
            @Override
            public SignatureVisitor visitInterface() {
                // override to set state
                // Not possible without reflection; so we simulate by directly calling super after setting state
                return super.visitInterface();
            }
        }
        TestAdapter invalidAdapter = new TestAdapter(8, mockVisitor);
        try {
            invalidAdapter.visitInterface();
            System.err.println("testVisitInterfaceWithInvalidState failed: exception not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitInterfaceWithInvalidState passed");
        } catch (Exception e) {
            System.err.println("testVisitInterfaceWithInvalidState failed with unexpected exception: " + e);
        }
    }
}
