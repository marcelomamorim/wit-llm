/*
 * @test
 * @summary Test CheckSignatureAdapter.visitArrayType() for correct state and type validation
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    private static final int TYPE_SIGNATURE = CheckSignatureAdapter.TYPE_SIGNATURE;
    private static final int EMPTY = 0;
    private static final int SIMPLE_TYPE = 1;

    public static void main(String[] args) {
        testVisitArrayTypeValidState();
        testVisitArrayTypeInvalidState();
        testVisitArrayTypeInvalidType();
    }

    private static void testVisitArrayTypeValidState() {
        // Create a CheckSignatureAdapter with type = TYPE_SIGNATURE and state = EMPTY (default)
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, null);
        // The initial state is EMPTY (0) and type is TYPE_SIGNATURE (2), so visitArrayType should succeed
        SignatureVisitor result = adapter.visitArrayType();
        if (result == null) {
            throw new AssertionError("visitArrayType() returned null when it should return a CheckSignatureAdapter instance");
        }
    }

    private static void testVisitArrayTypeInvalidState() {
        // Create a subclass to forcibly set state to a value other than EMPTY
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, null) {
            {
                // forcibly set state to SIMPLE_TYPE (1) to simulate invalid state
                this.state = SIMPLE_TYPE;
            }
        };
        // According to source, visitArrayType should throw IllegalStateException if state != EMPTY
        try {
            adapter.visitArrayType();
            throw new AssertionError("Expected IllegalStateException for invalid state but none was thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitArrayTypeInvalidType() {
        // Create a subclass to forcibly set type to a value other than TYPE_SIGNATURE
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, null) {
            {
                // forcibly set type to CLASS_SIGNATURE (0) to simulate invalid type
                this.type = CheckSignatureAdapter.CLASS_SIGNATURE;
            }
        };
        // The state is EMPTY (default), but type != TYPE_SIGNATURE, so should throw
        try {
            adapter.visitArrayType();
            throw new AssertionError("Expected IllegalStateException for invalid type but none was thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    // Access to protected fields state and type is possible because this test class is in the same package
    // and we use an anonymous subclass to set them forcibly for testing invalid conditions.

}