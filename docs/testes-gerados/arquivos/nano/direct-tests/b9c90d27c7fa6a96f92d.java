/*
 * @test
 * @summary Test visitExceptionType method of CheckSignatureAdapter for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitExceptionTypeWithValidState();
        testVisitExceptionTypeWithInvalidState();
        testVisitExceptionTypeWithNullSignatureVisitor();
    }

    private static void testVisitExceptionTypeWithValidState() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitExceptionType() {
                System.out.println("visitExceptionType called");
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        try {
            SignatureVisitor result = adapter.visitExceptionType();
            if (result != null) {
                System.out.println("testVisitExceptionTypeWithValidState passed");
            } else {
                System.out.println("testVisitExceptionTypeWithValidState failed: result is null");
            }
        } catch (Exception e) {
            System.out.println("testVisitExceptionTypeWithValidState failed with exception: " + e);
        }
    }

    private static void testVisitExceptionTypeWithInvalidState() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitExceptionType() {
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        // Manually set state to an invalid value to simulate error
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, 999); // invalid state
            adapter.visitExceptionType();
            System.out.println("testVisitExceptionTypeWithInvalidState failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitExceptionTypeWithInvalidState passed");
        } catch (Exception e) {
            System.out.println("testVisitExceptionTypeWithInvalidState failed with unexpected exception: " + e);
        }
    }

    private static void testVisitExceptionTypeWithNullSignatureVisitor() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            SignatureVisitor result = adapter.visitExceptionType();
            if (result == null) {
                System.out.println("testVisitExceptionTypeWithNullSignatureVisitor passed");
            } else {
                System.out.println("testVisitExceptionTypeWithNullSignatureVisitor failed: result is not null");
            }
        } catch (Exception e) {
            System.out.println("testVisitExceptionTypeWithNullSignatureVisitor failed with exception: " + e);
        }
    }
}
