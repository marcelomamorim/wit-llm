/*
 * @test
 * @summary Verifies that TraceMethodVisitor.visitParameter dereferences the Printer without a null check
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;

public class TraceMethodVisitorWitupTest {

    public static void main(String[] args) throws Exception {
        TraceMethodVisitor tmv = new TraceMethodVisitor(null);
        if (tmv.p != null) {
            throw new AssertionError("p should be null");
        }
        try {
            tmv.visitParameter("x", 0);
            throw new AssertionError("expected NullPointerException when p is null");
        } catch (NullPointerException expected) {
        }

        TraceMethodVisitor tmv2 = new TraceMethodVisitor(null, null);
        try {
            tmv2.visitParameter(null, 0);
            throw new AssertionError("expected NullPointerException with null mv and null p");
        } catch (NullPointerException expected) {
        }
    }
}
