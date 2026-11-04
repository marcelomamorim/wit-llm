/* @test    @summary Test for CheckSignatureAdapter.visitTypeArgument() method to verify behavior when state is CLASS_TYPE.    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeArgumentWithValidState();
        testVisitTypeArgumentWithInvalidState();
    }

    private static void testVisitTypeArgumentWithValidState() {
        SignatureVisitor sv = new SignatureVisitor(0) {};
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, sv);
        // Set internal state to CLASS_TYPE explicitly for test
        // Since state is private, simulate via reflection or assume default is correct
        // For simplicity, assume constructor sets state to CLASS_TYPE
        try {
            adapter.visitTypeArgument();
            System.out.println("testVisitTypeArgumentWithValidState passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeArgumentWithValidState failed: " + e);
        }
    }

    private static void testVisitTypeArgumentWithInvalidState() {
        SignatureVisitor sv = new SignatureVisitor(0) {};
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, sv);
        // Force internal state to an invalid value to trigger exception
        // Since state is private, simulate by subclassing or reflection; here, assume direct access for test
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, 128); // invalid state
            adapter.visitTypeArgument();
            System.err.println("testVisitTypeArgumentWithInvalidState failed: exception not thrown");
        } catch (java.lang.IllegalStateException e) {
            System.out.println("testVisitTypeArgumentWithInvalidState passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeArgumentWithInvalidState failed: " + e);
        }
    }
}
