/*
 * @test
 * @summary Test visitParameter method of MethodVisitor for API version check and delegation
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testVisitParameterThrowsForApiLessThanASM5();
        testVisitParameterDelegatesToMv();
        testVisitParameterNoMv();
    }

    private static void testVisitParameterThrowsForApiLessThanASM5() {
        // api < Opcodes.ASM5 (which is 5)
        final int api = Opcodes.ASM4; // 4 < 5
        MethodVisitor mv = new MethodVisitor(api) {};
        boolean thrown = false;
        try {
            mv.visitParameter("param", 0);
        } catch (RuntimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected RuntimeException for api < ASM5");
        }
    }

    private static void testVisitParameterDelegatesToMv() {
        final int api = Opcodes.ASM5; // api >= ASM5
        final boolean[] called = new boolean[1];
        MethodVisitor delegate = new MethodVisitor(api) {
            @Override
            public void visitParameter(String name, int access) {
                if ("param".equals(name) && access == 123) {
                    called[0] = true;
                }
            }
        };
        MethodVisitor mv = new MethodVisitor(api, delegate) {};
        mv.visitParameter("param", 123);
        if (!called[0]) {
            throw new AssertionError("Expected visitParameter to delegate to mv");
        }
    }

    private static void testVisitParameterNoMv() {
        final int api = Opcodes.ASM5; // api >= ASM5
        MethodVisitor mv = new MethodVisitor(api) {};
        // Should not throw and do nothing since mv == null
        mv.visitParameter("param", 0);
    }
}
