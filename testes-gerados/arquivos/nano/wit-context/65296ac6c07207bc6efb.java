/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitReturnType() method.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitReturnType_Normal();
        testVisitReturnType_InvalidType();
    }

    private static void testVisitReturnType_Normal() {
        try {
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
            SignatureVisitor result = adapter.visitReturnType();
            if (result == null) {
                throw new AssertionError("Expected non-null SignatureVisitor");
            }
            System.out.println("testVisitReturnType_Normal passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitReturnType_Normal", e);
        }
    }

    private static void testVisitReturnType_InvalidType() {
        try {
            // Passing an invalid type to trigger IllegalArgumentException
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(999, null); // invalid type
            adapter.visitReturnType();
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitReturnType_InvalidType passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitReturnType_InvalidType", e);
        }
    }
}
