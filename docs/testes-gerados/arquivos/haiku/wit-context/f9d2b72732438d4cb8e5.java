/*
 * @test
 * @summary Checks the api version guard of the two-argument MethodVisitor constructor
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {
    public static void main(String[] args) {
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            new MethodVisitor(api, null) {};
        }

        MethodVisitor delegate = new MethodVisitor(Opcodes.ASM6, null) {};
        new MethodVisitor(Opcodes.ASM5, delegate) {};

        expectIllegalArgument(Opcodes.ASM4 - 1, null);
        expectIllegalArgument(Opcodes.ASM6 + 1, null);
        expectIllegalArgument(0, null);
        expectIllegalArgument(Opcodes.ASM6 + 1, delegate);
    }

    static void expectIllegalArgument(int api, MethodVisitor mv) {
        try {
            new MethodVisitor(api, mv) {};
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for api " + api);
    }
}
