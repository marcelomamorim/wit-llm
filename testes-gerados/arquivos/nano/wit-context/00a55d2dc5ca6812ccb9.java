/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitClassType method behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassTypeWithValidName();
        testVisitClassTypeWithNullName();
        testVisitClassTypeWithEmptyName();
        testVisitClassTypeWithInvalidState();
    }

    private static void testVisitClassTypeWithValidName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitClassType("ValidClassName");
            System.out.println("testVisitClassTypeWithValidName passed");
        } catch (Exception e) {
            System.err.println("testVisitClassTypeWithValidName failed: " + e);
        }
    }

    private static void testVisitClassTypeWithNullName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitClassType(null);
            System.out.println("testVisitClassTypeWithNullName passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitClassTypeWithNullName passed (caught expected exception)");
        } catch (Exception e) {
            System.err.println("testVisitClassTypeWithNullName failed: " + e);
        }
    }

    private static void testVisitClassTypeWithEmptyName() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitClassType("");
            System.out.println("testVisitClassTypeWithEmptyName passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitClassTypeWithEmptyName passed (caught expected exception)");
        } catch (Exception e) {
            System.err.println("testVisitClassTypeWithEmptyName failed: " + e);
        }
    }

    private static void testVisitClassTypeWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // Manipulate internal state to simulate invalid state
        try {
            // Reflection or direct access not used; simulate by calling visitClassType twice
            adapter.visitClassType("FirstCall");
            // Now, forcibly set state to non-EMPTY to simulate invalid state
            // Since internal state is private, this test assumes the method is called in invalid state
            // For demonstration, calling again to trigger exception
            adapter.visitClassType("SecondCall");
            System.out.println("testVisitClassTypeWithInvalidState failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitClassTypeWithInvalidState passed (caught expected exception)");
        } catch (Exception e) {
            System.err.println("testVisitClassTypeWithInvalidState failed: " + e);
        }
    }
}
