/*
 * @test
 * @summary Test accept method of ParameterNode to ensure it calls visitParameter with correct arguments
 * @run main ParameterNodeWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.ParameterNode;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class ParameterNodeWitupTest {

    public static void main(String[] args) {
        testAcceptCallsVisitParameter();
        testAcceptWithDifferentParameters();
    }

    private static void testAcceptCallsVisitParameter() {
        ParameterNode node = new ParameterNode("paramName", 0x01);
        final boolean[] visited = {false};
        MethodVisitor visitor = new MethodVisitor() {
            @Override
            public void visitParameter(String name, int access) {
                visited[0] = true;
                assert "paramName".equals(name) : "Expected name to be 'paramName'";
                assert access == 0x01 : "Expected access to be 0x01";
            }
        };
        node.accept(visitor);
        assert visited[0] : "visitParameter was not called";
    }

    private static void testAcceptWithDifferentParameters() {
        ParameterNode node = new ParameterNode("anotherParam", 0x02);
        final String[] capturedName = {null};
        final int[] capturedAccess = {0};
        MethodVisitor visitor = new MethodVisitor() {
            @Override
            public void visitParameter(String name, int access) {
                capturedName[0] = name;
                capturedAccess[0] = access;
            }
        };
        node.accept(visitor);
        assert "anotherParam".equals(capturedName[0]) : "Name mismatch";
        assert capturedAccess[0] == 0x02 : "Access flags mismatch";
    }
}
