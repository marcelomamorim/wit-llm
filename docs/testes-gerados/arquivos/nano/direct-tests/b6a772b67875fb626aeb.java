/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInterfaceBound() method for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceBoundWithNullSignatureVisitor();
        testVisitInterfaceBoundWithNonNullSignatureVisitor();
        testVisitInterfaceBoundWithInvalidState();
    }

    private static void testVisitInterfaceBoundWithNullSignatureVisitor() {
        try {
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
            SignatureVisitor result = adapter.visitInterfaceBound();
            if (result != null) {
                throw new AssertionError("Expected null when sv is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testVisitInterfaceBoundWithNonNullSignatureVisitor() {
        SignatureVisitor mockVisitor = new SignatureVisitor() {
            // minimal implementation for testing
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        SignatureVisitor result = adapter.visitInterfaceBound();
        if (result == null) {
            throw new AssertionError("Expected non-null SignatureVisitor");
        }
        if (!(result instanceof CheckSignatureAdapter)) {
            throw new AssertionError("Result should be instance of CheckSignatureAdapter");
        }
        CheckSignatureAdapter resultAdapter = (CheckSignatureAdapter) result;
        if (resultAdapter.type != CheckSignatureAdapter.TYPE_SIGNATURE) {
            throw new AssertionError("Incorrect type in result adapter");
        }
        if (resultAdapter.sv != mockVisitor) {
            throw new AssertionError("SignatureVisitor not correctly assigned");
        }
    }

    private static void testVisitInterfaceBoundWithInvalidState() {
        try {
            // Using reflection to set invalid state, as state is private and not accessible
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, -1); // invalid state
            adapter.visitInterfaceBound();
            throw new AssertionError("Expected IllegalArgumentException for invalid state");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
