/*
 * @test
 * @summary Test constructor of SignatureVisitor with valid and invalid api versions
 * @run main SignatureVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class SignatureVisitorWitupTest {
    public static void main(String[] args) {
        testValidApi();
        testInvalidApiBelow();
        testInvalidApiAbove();
    }

    private static void testValidApi() {
        // Test with valid api values: ASM4, ASM5, ASM6
        SignatureVisitor sv4 = new SignatureVisitor(Opcodes.ASM4);
        if (sv4 == null) throw new RuntimeException("SignatureVisitor instance is null for ASM4");

        SignatureVisitor sv5 = new SignatureVisitor(Opcodes.ASM5);
        if (sv5 == null) throw new RuntimeException("SignatureVisitor instance is null for ASM5");

        SignatureVisitor sv6 = new SignatureVisitor(Opcodes.ASM6);
        if (sv6 == null) throw new RuntimeException("SignatureVisitor instance is null for ASM6");
    }

    private static void testInvalidApiBelow() {
        try {
            new SignatureVisitor(Opcodes.ASM4 - 1);
            throw new RuntimeException("Expected IllegalArgumentException for api below ASM4");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidApiAbove() {
        try {
            new SignatureVisitor(Opcodes.ASM6 + 1);
            throw new RuntimeException("Expected IllegalArgumentException for api above ASM6");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
