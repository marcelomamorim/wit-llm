/*
 * @test
 * @summary Test MethodVisitor constructor with single int api parameter
 * @run main MethodVisitorWitupTest
 */

public class MethodVisitorWitupTest {
    public static void main(String[] args) {
        // Test with a typical api value
        jdk.internal.org.objectweb.asm.MethodVisitor mv1 = new jdk.internal.org.objectweb.asm.MethodVisitor(0);
        if (mv1 == null) {
            throw new Error("MethodVisitor constructor returned null");
        }

        // Test with a negative api value
        jdk.internal.org.objectweb.asm.MethodVisitor mv2 = new jdk.internal.org.objectweb.asm.MethodVisitor(-1);
        if (mv2 == null) {
            throw new Error("MethodVisitor constructor returned null for negative api");
        }

        // Test with a large api value
        jdk.internal.org.objectweb.asm.MethodVisitor mv3 = new jdk.internal.org.objectweb.asm.MethodVisitor(Integer.MAX_VALUE);
        if (mv3 == null) {
            throw new Error("MethodVisitor constructor returned null for large api");
        }
    }
}
