/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.tree.ParameterNode.accept method
 * @run main ParameterNodeWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.ParameterNode;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class ParameterNodeWitupTest {
    public static void main(String[] args) {
        testAcceptInvokesVisitParameter();
        testAcceptWithNullName();
        testAcceptWithZeroAccess();
    }

    private static void testAcceptInvokesVisitParameter() {
        final String testName = "paramName";
        final int testAccess = 0x10; // arbitrary access flag
        ParameterNode pn = new ParameterNode(testName, testAccess);

        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);

        if (!testName.equals(tmv.visitedName)) {
            throw new AssertionError("Expected name: " + testName + ", but got: " + tmv.visitedName);
        }
        if (testAccess != tmv.visitedAccess) {
            throw new AssertionError("Expected access: " + testAccess + ", but got: " + tmv.visitedAccess);
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("visitParameter should be called exactly once");
        }
    }

    private static void testAcceptWithNullName() {
        ParameterNode pn = new ParameterNode(null, 0);
        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);
        if (tmv.visitedName != null) {
            throw new AssertionError("Expected null name to be passed to visitParameter");
        }
        if (tmv.visitedAccess != 0) {
            throw new AssertionError("Expected access 0 when constructed with 0");
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("visitParameter should be called exactly once even with null name");
        }
    }

    private static void testAcceptWithZeroAccess() {
        ParameterNode pn = new ParameterNode("test", 0);
        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);
        if (!"test".equals(tmv.visitedName)) {
            throw new AssertionError("Expected name 'test' but got " + tmv.visitedName);
        }
        if (tmv.visitedAccess != 0) {
            throw new AssertionError("Expected access 0 but got " + tmv.visitedAccess);
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("visitParameter should be called exactly once");
        }
    }

    // Minimal MethodVisitor stub to record calls to visitParameter
    private static class TestMethodVisitor extends MethodVisitor {
        String visitedName = "";
        int visitedAccess = -1;
        int visitParameterCalled = 0;

        TestMethodVisitor() {
            super(/* api = */ 0);
        }

        @Override
        public void visitParameter(String name, int access) {
            visitedName = name;
            visitedAccess = access;
            visitParameterCalled++;
        }
    }
}
