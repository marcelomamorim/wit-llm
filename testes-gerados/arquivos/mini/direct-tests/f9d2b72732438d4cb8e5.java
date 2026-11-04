/*
 * @test
 * @summary Test MethodVisitor constructor with valid and invalid api versions
 * @run main MethodVisitorWitupTest
 */

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiVersions();
        testInvalidApiVersions();
    }

    private static void testValidApiVersions() {
        // Opcodes.ASM4 = 4, Opcodes.ASM6 = 6
        // Test with api = 4 (lower bound)
        jdk.internal.org.objectweb.asm.MethodVisitor mv1 = new jdk.internal.org.objectweb.asm.MethodVisitor(4, null);
        if (mv1 == null) {
            throw new AssertionError("MethodVisitor instance is null for api=4");
        }

        // Test with api = 5 (middle value)
        jdk.internal.org.objectweb.asm.MethodVisitor mv2 = new jdk.internal.org.objectweb.asm.MethodVisitor(5, null);
        if (mv2 == null) {
            throw new AssertionError("MethodVisitor instance is null for api=5");
        }

        // Test with api = 6 (upper bound)
        jdk.internal.org.objectweb.asm.MethodVisitor mv3 = new jdk.internal.org.objectweb.asm.MethodVisitor(6, null);
        if (mv3 == null) {
            throw new AssertionError("MethodVisitor instance is null for api=6");
        }

        // Test with valid api and non-null MethodVisitor
        jdk.internal.org.objectweb.asm.MethodVisitor delegate = new jdk.internal.org.objectweb.asm.MethodVisitor(5, null);
        jdk.internal.org.objectweb.asm.MethodVisitor mv4 = new jdk.internal.org.objectweb.asm.MethodVisitor(5, delegate);
        if (mv4 == null) {
            throw new AssertionError("MethodVisitor instance is null for api=5 with delegate");
        }
    }

    private static void testInvalidApiVersions() {
        // Test with api less than ASM4 (3)
        try {
            new jdk.internal.org.objectweb.asm.MethodVisitor(3, null);
            throw new AssertionError("Expected IllegalArgumentException for api=3");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with api greater than ASM6 (7)
        try {
            new jdk.internal.org.objectweb.asm.MethodVisitor(7, null);
            throw new AssertionError("Expected IllegalArgumentException for api=7");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
