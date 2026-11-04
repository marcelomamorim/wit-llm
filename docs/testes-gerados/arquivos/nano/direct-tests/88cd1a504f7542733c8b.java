/* @test    @summary Test visitClassBound method of CheckSignatureAdapter    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassBoundWithNullSignatureVisitor();
        testVisitClassBoundWithNonNullSignatureVisitor();
        testVisitClassBoundInvalidState();
    }

    private static void testVisitClassBoundWithNullSignatureVisitor() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            SignatureVisitor result = adapter.visitClassBound();
            if (result != null) {
                System.out.println("PASS: visitClassBound with null sv returns null as expected.");
            } else {
                System.out.println("FAIL: visitClassBound with null sv should return null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown in visitClassBound with null sv: " + e);
        }
    }

    private static void testVisitClassBoundWithNonNullSignatureVisitor() {
        SignatureVisitor mockVisitor = new SignatureVisitor(0) {};
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        try {
            SignatureVisitor result = adapter.visitClassBound();
            if (result != null && result instanceof CheckSignatureAdapter) {
                CheckSignatureAdapter resultAdapter = (CheckSignatureAdapter) result;
                if (resultAdapter.state == CheckSignatureAdapter.BOUND && resultAdapter.sv == mockVisitor) {
                    System.out.println("PASS: visitClassBound with non-null sv returns new CheckSignatureAdapter with correct state.");
                } else {
                    System.out.println("FAIL: Returned adapter does not have expected state or sv.");
                }
            } else {
                System.out.println("FAIL: visitClassBound with non-null sv did not return expected adapter.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown in visitClassBound with non-null sv: " + e);
        }
    }

    private static void testVisitClassBoundInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // Manually set state to an invalid value to simulate error
        adapter.state = -1;
        try {
            adapter.visitClassBound();
            System.out.println("FAIL: Expected IllegalStateException not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
