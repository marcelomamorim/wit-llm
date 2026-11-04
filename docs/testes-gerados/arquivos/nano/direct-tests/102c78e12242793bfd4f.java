/* @test    @summary Test constructor of SignatureVisitor for valid API values    @run main SignatureVisitorWitupTest */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SignatureVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with ASM4 (value 4)
        SignatureVisitor visitorASM4 = new SignatureVisitor(Opcodes.ASM4);
        assert visitorASM4 != null : "SignatureVisitor should be instantiated with ASM4";
        // Test with ASM6 (value 6)
        SignatureVisitor visitorASM6 = new SignatureVisitor(Opcodes.ASM6);
        assert visitorASM6 != null : "SignatureVisitor should be instantiated with ASM6";
    }

    private static void testInvalidApiValues() {
        // Test with value less than ASM4 (e.g., 3)
        try {
            new SignatureVisitor(3);
            assert false : "Expected IllegalArgumentException for API 3";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // Test with value greater than ASM6 (e.g., 7)
        try {
            new SignatureVisitor(7);
            assert false : "Expected IllegalArgumentException for API 7";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
