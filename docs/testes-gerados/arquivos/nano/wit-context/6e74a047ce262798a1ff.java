/*
 * @test
 * @summary Test visitParameter method for different API levels
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testVisitParameterWithApiLevelBelow();
        testVisitParameterWithApiLevelAtOrAbove();
    }

    private static void testVisitParameterWithApiLevelBelow() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM4, null);
        try {
            mv.visitParameter("param", 0);
            System.out.println("Test passed: visitParameter with API level below ASM5 did not throw.");
        } catch (RuntimeException e) {
            System.out.println("Test failed: Unexpected RuntimeException for API level below ASM5.");
        }
    }

    private static void testVisitParameterWithApiLevelAtOrAbove() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM5, null);
        try {
            mv.visitParameter("param", 0);
            System.out.println("Test passed: visitParameter with API level at ASM5 did not throw.");
        } catch (RuntimeException e) {
            System.out.println("Test failed: Unexpected RuntimeException for API level at ASM5.");
        }
    }
}
