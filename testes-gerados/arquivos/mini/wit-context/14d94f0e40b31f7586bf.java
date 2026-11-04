/*
 * @test
 * @summary Deterministic tests for CheckClassAdapter.checkClassSignature(String)
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testValidSignatures();
        testInvalidSignatures();
    }

    private static void testValidSignatures() {
        // Valid signature with formal type parameters and class type signatures
        CheckClassAdapter.checkClassSignature("<T:Ljava/lang/Object;>Ljava/lang/Object;");

        // Valid signature without formal type parameters
        CheckClassAdapter.checkClassSignature("Ljava/lang/Object;");

        // Valid signature with multiple class type signatures
        CheckClassAdapter.checkClassSignature("Ljava/lang/Object;Ljava/util/List;");
    }

    private static void testInvalidSignatures() {
        // Empty string should throw IllegalArgumentException because pos != length after parsing
        try {
            CheckClassAdapter.checkClassSignature("");
            throw new AssertionError("Expected IllegalArgumentException for empty signature");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Signature with invalid trailing characters
        try {
            CheckClassAdapter.checkClassSignature("Ljava/lang/Object;X");
            throw new AssertionError("Expected IllegalArgumentException for trailing invalid char");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Signature starting with invalid character (not '<' or 'L')
        try {
            CheckClassAdapter.checkClassSignature("X");
            throw new AssertionError("Expected IllegalArgumentException for invalid start char");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
