/*
 * @test
 * @summary Test ClassVisitor constructor with valid and invalid api versions
 * @run main ClassVisitorWitupTest
 */

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiVersions();
        testInvalidApiVersions();
    }

    private static void testValidApiVersions() {
        // Opcodes.ASM4 = 4, ASM5 = 5, ASM6 = 6 (from ASM API spec)
        // Test with ASM4
        jdk.internal.org.objectweb.asm.ClassVisitor cv4 = new jdk.internal.org.objectweb.asm.ClassVisitor(4, null) {
        };
        if (cv4.api != 4) {
            throw new AssertionError("Expected api 4 but got " + cv4.api);
        }

        // Test with ASM5
        jdk.internal.org.objectweb.asm.ClassVisitor cv5 = new jdk.internal.org.objectweb.asm.ClassVisitor(5, null) {
        };
        if (cv5.api != 5) {
            throw new AssertionError("Expected api 5 but got " + cv5.api);
        }

        // Test with ASM6
        jdk.internal.org.objectweb.asm.ClassVisitor cv6 = new jdk.internal.org.objectweb.asm.ClassVisitor(6, null) {
        };
        if (cv6.api != 6) {
            throw new AssertionError("Expected api 6 but got " + cv6.api);
        }
    }

    private static void testInvalidApiVersions() {
        // Test with api less than ASM4 (4)
        try {
            new jdk.internal.org.objectweb.asm.ClassVisitor(3, null) {
            };
            throw new AssertionError("Expected IllegalArgumentException for api=3");
        } catch (IllegalArgumentException expected) {
        }

        // Test with api greater than ASM6 (6)
        try {
            new jdk.internal.org.objectweb.asm.ClassVisitor(7, null) {
            };
            throw new AssertionError("Expected IllegalArgumentException for api=7");
        } catch (IllegalArgumentException expected) {
        }
    }
}
