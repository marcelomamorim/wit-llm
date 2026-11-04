/*
 * @test
 * @summary Test visitInterface method of CheckSignatureAdapter for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceWithNullSignatureVisitor();
        testVisitInterfaceWithNonNullSignatureVisitor();
    }

    private static void testVisitInterfaceWithNullSignatureVisitor() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        SignatureVisitor result = adapter.visitInterface();
        if (result != null) {
            throw new AssertionError("Expected null when sv is null, but got: " + result);
        }
    }

    private static void testVisitInterfaceWithNonNullSignatureVisitor() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {};
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        SignatureVisitor result = adapter.visitInterface();
        if (result == null) {
            throw new AssertionError("Expected non-null SignatureVisitor, but got null.");
        }
        if (!(result instanceof CheckSignatureAdapter)) {
            throw new AssertionError("Result should be instance of CheckSignatureAdapter.");
        }
        CheckSignatureAdapter castedResult = (CheckSignatureAdapter) result;
        if (castedResult.sv != mockVisitor) {
            throw new AssertionError("Inner SignatureVisitor does not match the provided one.");
        }
        if (castedResult.type != CheckSignatureAdapter.TYPE_SIGNATURE) {
            throw new AssertionError("Type does not match TYPE_SIGNATURE.");
        }
    }
}
