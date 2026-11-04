/*
 * @test
 * @summary Tests for CheckSignatureAdapter.visitFormalTypeParameter
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitFormalTypeParameterWithNullName();
        testVisitFormalTypeParameterWithEmptyName();
        testVisitFormalTypeParameterWithValidName();
    }

    private static void testVisitFormalTypeParameterWithNullName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitFormalTypeParameter(null);
        } catch (NullPointerException e) {
            System.out.println("Passed: Null name throws NullPointerException");
            return;
        }
        System.out.println("Failed: Null name did not throw NullPointerException");
    }

    private static void testVisitFormalTypeParameterWithEmptyName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitFormalTypeParameter("");
            System.out.println("Passed: Empty name accepted without exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: Empty name threw IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("Failed: Empty name threw unexpected exception: " + e);
        }
    }

    private static void testVisitFormalTypeParameterWithValidName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitFormalTypeParameter("T");
            System.out.println("Passed: Valid name processed without exception");
        } catch (Exception e) {
            System.out.println("Failed: Valid name threw exception: " + e);
        }
    }
}
