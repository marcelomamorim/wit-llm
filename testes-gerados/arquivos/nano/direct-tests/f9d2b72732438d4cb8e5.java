/* @test    @summary Test constructor of MethodVisitor for valid API values    @run main MethodVisitorWitupTest */

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    static void testValidApiValues() {
        // Opcodes.ASM4 and Opcodes.ASM6 are not defined here, so we assume valid integer values
        // For testing, use 4 and 6 as valid API values
        int validApi1 = 4;
        int validApi2 = 6;
        MethodVisitor mv1 = new MethodVisitor(validApi1, null);
        MethodVisitor mv2 = new MethodVisitor(validApi2, mv1);
        assert mv1 != null : "MethodVisitor with valid API 4 should not be null";
        assert mv2 != null : "MethodVisitor with valid API 6 should not be null";
    }

    static void testInvalidApiValues() {
        try {
            new MethodVisitor(3, null); // below ASM4
            assert false : "Expected IllegalArgumentException for API 3";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new MethodVisitor(7, null); // above ASM6
            assert false : "Expected IllegalArgumentException for API 7";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
