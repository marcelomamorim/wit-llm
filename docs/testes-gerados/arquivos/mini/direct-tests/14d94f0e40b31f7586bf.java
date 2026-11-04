/*
 * @test
 * @summary Test CheckClassAdapter.checkClassSignature with various inputs
 * @run main CheckClassAdapterWitupTest
 */

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testEmptySignature();
        testValidSimpleClassSignature();
        testValidFormalTypeParameters();
        testMultipleClassTypeSignatures();
        testInvalidSignatureExtraChars();
    }

    private static void testEmptySignature() {
        // Empty signature is invalid because checkClassTypeSignature expects at least one class type signature
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature("");
            throw new Error("Expected IllegalArgumentException for empty signature");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidSimpleClassSignature() {
        // Signature with one class type signature only
        // Example: Ljava/lang/Object;
        String sig = "Ljava/lang/Object;";
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(sig);
    }

    private static void testValidFormalTypeParameters() {
        // Signature with formal type parameters and one class type signature
        // Example: <T:Ljava/lang/Object;>Ljava/lang/Object;
        String sig = "<T:Ljava/lang/Object;>Ljava/lang/Object;";
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(sig);
    }

    private static void testMultipleClassTypeSignatures() {
        // Signature with one formal type parameter and multiple class type signatures starting with 'L'
        // Example: <T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/io/Serializable;
        String sig = "<T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/io/Serializable;";
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(sig);
    }

    private static void testInvalidSignatureExtraChars() {
        // Signature with extra characters after valid class type signatures
        // Should throw IllegalArgumentException with error index
        String sig = "Ljava/lang/Object;X";
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(sig);
            throw new Error("Expected IllegalArgumentException for extra characters");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("error at index")) {
                throw new Error("Exception message does not contain error index");
            }
        }
    }
}
