/*
 * @test
 * @summary Verify SignatureVisitor constructor accepts api values in [ASM4, ASM6] and rejects values outside that range
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.signature
 * @run main SignatureVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class SignatureVisitorWitupTest {

    public static void main(String[] args) {
        checkAccepted(Opcodes.ASM4);
        checkAccepted(Opcodes.ASM6);
        checkRejected(Opcodes.ASM4 - 1);
        checkRejected(Opcodes.ASM6 + 1);
    }

    static void checkAccepted(int api) {
        new SignatureVisitor(api) { };
    }

    static void checkRejected(int api) {
        try {
            new SignatureVisitor(api) { };
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for api=" + api);
    }
}
