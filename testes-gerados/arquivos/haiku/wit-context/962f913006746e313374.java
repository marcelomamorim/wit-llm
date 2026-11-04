/*
 * @test
 * @summary Verifies that TraceMethodVisitor.visitParameter dereferences the null Printer supplied through the public single-argument constructor.
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;

public class TraceMethodVisitorWitupTest {

    public static void main(String[] args) {
        TraceMethodVisitor visitor = new TraceMethodVisitor(null);
        boolean thrown = false;
        try {
            visitor.visitParameter("arg", 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException when printer is null");
        }
    }
}
