/*
 * @test
 * @summary Test visitParameter method of MethodVisitor
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testVisitParameterWithValidInputs();
        testVisitParameterWithNullName();
        testVisitParameterWithEmptyName();
        testVisitParameterWithNegativeAccess();
    }

    static void testVisitParameterWithValidInputs() {
        MethodVisitor mv = new MethodVisitor(1);
        try {
            mv.visitParameter("paramName", 0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testVisitParameterWithNullName() {
        MethodVisitor mv = new MethodVisitor(1);
        try {
            mv.visitParameter(null, 0);
        } catch (NullPointerException e) {
            // Expected if null is not handled
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null name", e);
        }
    }

    static void testVisitParameterWithEmptyName() {
        MethodVisitor mv = new MethodVisitor(1);
        try {
            mv.visitParameter("", 0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty name", e);
        }
    }

    static void testVisitParameterWithNegativeAccess() {
        MethodVisitor mv = new MethodVisitor(1);
        try {
            mv.visitParameter("param", -1);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative access", e);
        }
    }
}
