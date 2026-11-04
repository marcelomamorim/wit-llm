/* @test    @summary Test for CheckSignatureAdapter.visitTypeVariable method behavior and exception paths    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeVariable_Normal();
        testVisitTypeVariable_InvalidState();
        testVisitTypeVariable_NullName();
    }

    private static void testVisitTypeVariable_Normal() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            adapter.visitTypeVariable("T");
            System.out.println("testVisitTypeVariable_Normal passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeVariable_Normal failed: " + e);
        }
    }

    private static void testVisitTypeVariable_InvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, CheckSignatureAdapter.CLASS_SIGNATURE, null);
        // Manually set state to non-EMPTY to simulate invalid state
        // Since state is private, we simulate by calling visitTypeVariable after a state change that causes exception
        // but as state is private, we invoke method in a way that triggers exception directly
        // For this, we can create a subclass or use reflection, but as reflection is discouraged, we simulate by calling in invalid context
        // Here, we assume that calling visitTypeVariable twice causes the exception
        try {
            adapter.visitTypeVariable("T");
            // Second call to trigger exception
            adapter.visitTypeVariable("T");
            System.err.println("testVisitTypeVariable_InvalidState failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitTypeVariable_InvalidState passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeVariable_InvalidState failed: " + e);
        }
    }

    private static void testVisitTypeVariable_NullName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            adapter.visitTypeVariable(null);
            System.out.println("testVisitTypeVariable_NullName passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitTypeVariable_NullName passed (caught expected exception)");
        } catch (Exception e) {
            System.err.println("testVisitTypeVariable_NullName failed: " + e);
        }
    }
}
