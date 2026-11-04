/* @test    @summary Test constructor of FieldVisitor for valid and invalid API values    @run main FieldVisitorWitupTest */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with ASM4
        try {
            new FieldVisitor(Opcodes.ASM4, null);
        } catch (Throwable t) {
            throw new AssertionError("Failed with ASM4", t);
        }
        // Test with ASM5
        try {
            new FieldVisitor(Opcodes.ASM5, null);
        } catch (Throwable t) {
            throw new AssertionError("Failed with ASM5", t);
        }
        // Test with ASM6
        try {
            new FieldVisitor(Opcodes.ASM6, null);
        } catch (Throwable t) {
            throw new AssertionError("Failed with ASM6", t);
        }
    }

    private static void testInvalidApiValues() {
        // Below ASM4
        try {
            new FieldVisitor(Opcodes.ASM4 - 1, null);
            throw new AssertionError("Expected IllegalArgumentException for api < ASM4");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for api < ASM4", t);
        }
        // Above ASM6
        try {
            new FieldVisitor(Opcodes.ASM6 + 1, null);
            throw new AssertionError("Expected IllegalArgumentException for api > ASM6");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for api > ASM6", t);
        }
    }
}
