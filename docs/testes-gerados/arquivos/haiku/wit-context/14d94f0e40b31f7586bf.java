/*
 * @test
 * @summary Witup-generated checks for CheckClassAdapter.checkClassSignature
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        CheckClassAdapter.checkClassSignature("Ljava/lang/Object;");
        CheckClassAdapter.checkClassSignature("<T:Ljava/lang/Object;>Ljava/lang/Object;");
        expectIllegalArgument("");
        expectIllegalArgument("Ljava/lang/Object;X");
        expectIllegalArgument("I");
    }

    static void expectIllegalArgument(String signature) {
        try {
            CheckClassAdapter.checkClassSignature(signature);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for: " + signature);
    }
}
