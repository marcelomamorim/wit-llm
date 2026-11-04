/*
 * @test
 * @summary Checks CheckClassAdapter.checkClassSignature accepts valid class signatures and rejects malformed ones with IllegalArgumentException
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testValidSimpleClassSignature();
        testValidSignatureWithFormalTypeParameters();
        testValidSignatureWithTypeArguments();
        testMissingSemicolonIsRejected();
        testTrailingGarbageIsRejected();
        testEmptySignatureIsRejected();
    }

    static void testValidSimpleClassSignature() {
        CheckClassAdapter.checkClassSignature("Ljava/lang/Object;");
    }

    static void testValidSignatureWithFormalTypeParameters() {
        CheckClassAdapter.checkClassSignature("<T:Ljava/lang/Object;>Ljava/lang/Object;");
    }

    static void testValidSignatureWithTypeArguments() {
        CheckClassAdapter.checkClassSignature("Ljava/util/List<Ljava/lang/String;>;");
    }

    static void testMissingSemicolonIsRejected() {
        expectIllegalArgument("Ljava/lang/Object");
    }

    static void testTrailingGarbageIsRejected() {
        expectIllegalArgument("Ljava/lang/Object;X");
    }

    static void testEmptySignatureIsRejected() {
        expectIllegalArgument("");
    }

    static void expectIllegalArgument(String signature) {
        try {
            CheckClassAdapter.checkClassSignature(signature);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for: \"" + signature + "\"");
    }
}
