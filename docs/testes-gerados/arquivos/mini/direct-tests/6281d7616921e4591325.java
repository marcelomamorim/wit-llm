/*
 * @test
 * @summary Test constructor of FieldVisitor with valid and invalid api versions
 * @run main FieldVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiVersions();
        testInvalidApiVersions();
    }

    private static void testValidApiVersions() {
        // Test with valid api versions: ASM4, ASM5, ASM6
        FieldVisitor fv4 = new FieldVisitor(Opcodes.ASM4, null) {
        };
        if (fv4.api != Opcodes.ASM4) {
            throw new AssertionError("Expected api ASM4");
        }

        FieldVisitor fv5 = new FieldVisitor(Opcodes.ASM5, null) {
        };
        if (fv5.api != Opcodes.ASM5) {
            throw new AssertionError("Expected api ASM5");
        }

        FieldVisitor fv6 = new FieldVisitor(Opcodes.ASM6, null) {
        };
        if (fv6.api != Opcodes.ASM6) {
            throw new AssertionError("Expected api ASM6");
        }
    }

    private static void testInvalidApiVersions() {
        // Test with api version less than ASM4
        try {
            new FieldVisitor(Opcodes.ASM4 - 1, null) {
            };
            throw new AssertionError("Expected IllegalArgumentException for api < ASM4");
        } catch (IllegalArgumentException expected) {
        }

        // Test with api version greater than ASM6
        try {
            new FieldVisitor(Opcodes.ASM6 + 1, null) {
            };
            throw new AssertionError("Expected IllegalArgumentException for api > ASM6");
        } catch (IllegalArgumentException expected) {
        }
    }
}
