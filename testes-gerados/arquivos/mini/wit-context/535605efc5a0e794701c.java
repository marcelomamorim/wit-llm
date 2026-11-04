/*
 * @test
 * @summary Test ParameterNode.accept(MethodVisitor) behavior including edge cases
 * @run main ParameterNodeWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.ParameterNode;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class ParameterNodeWitupTest {
    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptWithNullName();
        testAcceptWithZeroAccess();
    }

    private static void testAcceptNormal() {
        ParameterNode pn = new ParameterNode("paramName", 1);
        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);
        if (!"paramName".equals(tmv.name)) {
            throw new AssertionError("Expected name 'paramName' but got '" + tmv.name + "'");
        }
        if (tmv.access != 1) {
            throw new AssertionError("Expected access 1 but got " + tmv.access);
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("Expected visitParameter to be called once but was called " + tmv.visitParameterCalled + " times");
        }
    }

    private static void testAcceptWithNullName() {
        ParameterNode pn = new ParameterNode(null, 2);
        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);
        if (tmv.name != null) {
            throw new AssertionError("Expected name null but got '" + tmv.name + "'");
        }
        if (tmv.access != 2) {
            throw new AssertionError("Expected access 2 but got " + tmv.access);
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("Expected visitParameter to be called once but was called " + tmv.visitParameterCalled + " times");
        }
    }

    private static void testAcceptWithZeroAccess() {
        ParameterNode pn = new ParameterNode("zeroAccess", 0);
        TestMethodVisitor tmv = new TestMethodVisitor();
        pn.accept(tmv);
        if (!"zeroAccess".equals(tmv.name)) {
            throw new AssertionError("Expected name 'zeroAccess' but got '" + tmv.name + "'");
        }
        if (tmv.access != 0) {
            throw new AssertionError("Expected access 0 but got " + tmv.access);
        }
        if (tmv.visitParameterCalled != 1) {
            throw new AssertionError("Expected visitParameter to be called once but was called " + tmv.visitParameterCalled + " times");
        }
    }

    // Minimal MethodVisitor stub to record calls to visitParameter
    private static class TestMethodVisitor extends MethodVisitor {
        String name = "";
        int access = -1;
        int visitParameterCalled = 0;

        TestMethodVisitor() {
            super(/* api = */ 0);
        }

        @Override
        public void visitParameter(String name, int access) {
            this.name = name;
            this.access = access;
            visitParameterCalled++;
        }
    }
}
