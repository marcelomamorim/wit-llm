/*
 * @test
 * @summary Witup generated test for MethodVisitor(int api) constructor
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        MethodVisitor supported = new MethodVisitor(Opcodes.ASM4) {};
        if (supported == null) {
            throw new RuntimeException("MethodVisitor(ASM4) returned null");
        }
        supported.visitCode();
        supported.visitEnd();

        MethodVisitor supported6 = new MethodVisitor(Opcodes.ASM6) {};
        if (supported6 == null) {
            throw new RuntimeException("MethodVisitor(ASM6) returned null");
        }
        supported6.visitEnd();

        boolean thrown = false;
        try {
            MethodVisitor invalid = new MethodVisitor(0) {};
            if (invalid == null) {
                throw new RuntimeException("unexpected null");
            }
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("MethodVisitor(0) did not throw IllegalArgumentException");
        }
    }
}
