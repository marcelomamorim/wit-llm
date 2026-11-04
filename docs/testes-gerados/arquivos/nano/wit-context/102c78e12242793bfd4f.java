/* @test    @summary Test for SignatureVisitor constructor with invalid 'api' parameter    @run main SignatureVisitorWitupTest */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SignatureVisitorWitupTest {

    public static void main(String[] args) {
        testInvalidApiBelow();
        testInvalidApiAbove();
        testValidApiOpcodes();
    }

    private static void testInvalidApiBelow() {
        try {
            new SignatureVisitor(Opcodes.ASM4 - 1);
            System.err.println("Failed: Expected IllegalArgumentException for api below ASM4");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testInvalidApiAbove() {
        try {
            new SignatureVisitor(Opcodes.ASM6 + 1);
            System.err.println("Failed: Expected IllegalArgumentException for api above ASM6");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testValidApiOpcodes() {
        for (int api = Opcodes.ASM4; api <= Opcodes.ASM6; api++) {
            try {
                SignatureVisitor sv = new SignatureVisitor(api);
                if (sv == null) {
                    System.err.println("Failed: Constructor returned null for api=" + api);
                }
            } catch (Exception e) {
                System.err.println("Failed: Unexpected exception for api=" + api + ": " + e);
            }
        }
    }
}
