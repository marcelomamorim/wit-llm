/*
 * @test
 * @summary Test constructor of AnnotationVisitor for valid and invalid api versions
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiVersions();
        testInvalidApiVersions();
    }

    private static void testValidApiVersions() {
        // Test with valid api versions: ASM4, ASM5, ASM6
        AnnotationVisitor av1 = new AnnotationVisitor(Opcodes.ASM4, null) {};
        if (av1.api != Opcodes.ASM4) {
            throw new AssertionError("api field not set correctly for ASM4");
        }

        AnnotationVisitor av2 = new AnnotationVisitor(Opcodes.ASM5, null) {};
        if (av2.api != Opcodes.ASM5) {
            throw new AssertionError("api field not set correctly for ASM5");
        }

        AnnotationVisitor av3 = new AnnotationVisitor(Opcodes.ASM6, null) {};
        if (av3.api != Opcodes.ASM6) {
            throw new AssertionError("api field not set correctly for ASM6");
        }

        // Test that the av field is set correctly
        AnnotationVisitor delegate = new AnnotationVisitor(Opcodes.ASM4, null) {};
        AnnotationVisitor av4 = new AnnotationVisitor(Opcodes.ASM5, delegate) {};
        if (av4.av != delegate) {
            throw new AssertionError("av field not set correctly");
        }
    }

    private static void testInvalidApiVersions() {
        // Test api less than ASM4
        try {
            new AnnotationVisitor(Opcodes.ASM4 - 1, null) {};
            throw new AssertionError("Expected IllegalArgumentException for api < ASM4");
        } catch (IllegalArgumentException expected) {
        }

        // Test api greater than ASM6
        try {
            new AnnotationVisitor(Opcodes.ASM6 + 1, null) {};
            throw new AssertionError("Expected IllegalArgumentException for api > ASM6");
        } catch (IllegalArgumentException expected) {
        }
    }
}
