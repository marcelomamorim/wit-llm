/*
 * @test
 * @summary Verifies SignatureVisitor constructor accepts supported ASM API levels and rejects values outside the supported range
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.signature
 * @run main SignatureVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class SignatureVisitorWitupTest {

    public static void main(String[] args) {
        new SignatureVisitor(Opcodes.ASM4) {};
        new SignatureVisitor(Opcodes.ASM6) {};
        expectIllegalArgument(Opcodes.ASM4 - 1);
        expectIllegalArgument(Opcodes.ASM6 + 1);
    }

    static void expectIllegalArgument(int api) {
        try {
            new SignatureVisitor(api) {};
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for api=" + api);
    }
}
