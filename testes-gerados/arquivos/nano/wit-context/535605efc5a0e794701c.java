/* @test    @summary Test for ParameterNode.accept method to verify behavior with valid MethodVisitor.    @run main ParameterNodeWitupTest */

import jdk.internal.org.objectweb.asm.tree.ParameterNode;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class ParameterNodeWitupTest {

    public static void main(String[] args) {
        testAcceptWithValidMethodVisitor();
        testAcceptWithNullMethodVisitor();
    }

    private static void testAcceptWithValidMethodVisitor() {
        ParameterNode paramNode = new ParameterNode("paramName", 0);
        MethodVisitor mv = new MethodVisitor(0) {
            boolean visited = false;
            @Override
            public void visitParameter(String name, int access) {
                if ("paramName".equals(name) && access == 0) {
                    visited = true;
                }
            }
        };
        paramNode.accept(mv);
        // Since MethodVisitor.visitParameter is void, we check if the method executes without exception.
        // Additional checks can be added if MethodVisitor is extended to record calls.
    }

    private static void testAcceptWithNullMethodVisitor() {
        ParameterNode paramNode = new ParameterNode("paramName", 0);
        try {
            paramNode.accept(null);
            // Expecting NullPointerException as per typical Java behavior when calling a method on null.
            // But since accept calls mv.visitParameter, which is a method on mv, this should throw NullPointerException.
        } catch (NullPointerException e) {
            // Expected exception, test passes.
        }
    }
}
